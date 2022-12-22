/*
 * Copyright A.N.S 2022
 */
package fr.ans.psc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import fr.ans.esignsante.model.ESignSanteSignatureReport;
import fr.ans.psc.exception.GenericVihfException;
import fr.ans.psc.model.prosanteconnect.UserInfos;
import io.gravitee.common.http.HttpStatusCode;
import io.gravitee.common.http.MediaType;
import io.gravitee.gateway.api.ExecutionContext;
import io.gravitee.gateway.api.Request;
import io.gravitee.gateway.api.Response;
import io.gravitee.gateway.api.buffer.Buffer;
import io.gravitee.gateway.api.el.EvaluableRequest;
import io.gravitee.gateway.api.http.HttpHeaders;
import io.gravitee.gateway.api.stream.BufferedReadWriteStream;
import io.gravitee.gateway.api.stream.ReadWriteStream;
import io.gravitee.gateway.api.stream.SimpleReadWriteStream;
import io.gravitee.node.api.configuration.Configuration;
import io.gravitee.policy.api.PolicyChain;
import io.gravitee.policy.api.PolicyResult;
import io.gravitee.policy.api.annotations.OnRequestContent;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.*;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.multipart.MultipartForm;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallerFactory;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.x509.BasicX509Credential;
import org.opensaml.xml.security.x509.X509KeyInfoGeneratorFactory;
import org.opensaml.xml.signature.*;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.SignatureException;
import org.opensaml.xml.signature.Signer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.function.Consumer;

import static fr.ans.psc.utils.Constants.*;
import static io.gravitee.common.util.VertxProxyOptionsUtils.setSystemProxy;

@SuppressWarnings("unused")
public class GenerateVIHFPolicy {
    private final Logger log = LoggerFactory.getLogger(GenerateVIHFPolicy.class);
    /**
     * The associated configuration to this GenerateVIHF Policy
     */
    private final GenerateVIHFPolicyConfiguration configuration;
    private final ObjectMapper mapper;
    private Vertx vertx;
    private HttpClientOptions httpClientOptions;
    private Credential signingCredential;

    /**
     * Create a new GenerateVIHF Policy instance based on its associated configuration
     *
     * @param configuration the associated configuration to the new GenerateVIHF Policy instance
     */
    public GenerateVIHFPolicy(GenerateVIHFPolicyConfiguration configuration) {
        this.configuration = configuration;
        this.mapper = new ObjectMapper();
        this.signingCredential = initSigningCredential(configuration);
    }

    private void initVertxClient(ExecutionContext executionContext) {
        vertx = executionContext.getComponent(Vertx.class);
        String url = configuration.getDigitalSigningEndpoint();
        URI target = URI.create(url);
        httpClientOptions = new HttpClientOptions();

        httpClientOptions
                .setDefaultHost(target.getHost())
                .setDefaultPort(configuration.isUseSSL() ? HTTPS_PORT : HTTP_PORT)
                .setIdleTimeout(60)
                .setConnectTimeout(1000);

        if (configuration.isUseSSL()) {
            httpClientOptions.setSsl(true).setTrustAll(true).setVerifyHost(false);
        }
        if (configuration.isUseSystemProxy()) {
            Configuration config = executionContext.getComponent(Configuration.class);
            try {
                setSystemProxy(httpClientOptions, config);
            } catch (IllegalStateException e) {
                log.warn("Digital Signing requires a system proxy to be defined but some configurations are " +
                        "missing or not well defined: {}. Ignoring proxy.", e.getMessage(), e.getCause());
            }
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @OnRequestContent
    public ReadWriteStream onRequestContent(Request request, Response response, ExecutionContext executionContext, PolicyChain policyChain) {
        return new BufferedReadWriteStream() {

            io.gravitee.gateway.api.buffer.Buffer buffer = io.gravitee.gateway.api.buffer.Buffer.buffer();

            @Override
            public SimpleReadWriteStream<io.gravitee.gateway.api.buffer.Buffer> write(io.gravitee.gateway.api.buffer.Buffer content) {
                buffer.appendBuffer(content);
                return this;
            }

            @Override
            public void end() {
                initRequestResponseProperties(executionContext, buffer.toString());
//                initVertxClient(executionContext);
//                generateVihfAndSign(
//                        executionContext,
//                        result -> {
//                            if (result.length() > 0) {
//                                // REWRITE BUFFER WITH TRANSFORMED RESULT
//                                HttpHeaders headers = executionContext.request().headers();
//                                headers.remove(CONTENT_LENGTH_HEADER);
//                                headers.set(TRANSFER_ENCODING_HEADER, CHUNKED);
//                                log.error(result);
//                                Buffer buf = Buffer.buffer();
//                                buf.appendString(result);
//                                super.write(buf);
//                            }
//                            super.end();
//                        },
//                        policyChain::streamFailWith);
                try {
                    String body = generateOpenSamlVihfAndSign(executionContext);
                    if (body.length() > 0) {
                        HttpHeaders headers = executionContext.request().headers();
                        headers.remove(CONTENT_LENGTH_HEADER);
                        headers.set(TRANSFER_ENCODING_HEADER, CHUNKED);
                        log.error(body);
                        Buffer buf = Buffer.buffer();
                        buf.appendString(body);
                        super.write(buf);
                    }
                    super.end();
                } catch (GenericVihfException e) {
                    policyChain.streamFailWith(PolicyResult.failure(GENERATE_VIHF_ERROR));
                }
            }
        };
    }

    private void generateVihfAndSign(ExecutionContext executionContext, Consumer<String> onSuccess,
                                     Consumer<PolicyResult> onError) {

        // generate VIHF token
        String vihfToken = null;
        EvaluableRequest request = (EvaluableRequest) executionContext.getTemplateEngine().getTemplateContext()
                .lookupVariable(REQUEST_TEMPLATE_VARIABLE);

        try {
            UserInfos userInfos = getUserInfos(executionContext);
            String workSituationId = request.getHeaders().get(WORK_SITUATION_HEADER);
            String insHeader = request.getHeaders().get(PATIENT_INS_HEADER);

            VihfBuilder vihfBuilder = new VihfBuilder(userInfos, workSituationId, insHeader, configuration);
            vihfToken = vihfBuilder.generateVIHF();

        } catch (GenericVihfException e) {
            onError.accept(PolicyResult.failure(GENERATE_VIHF_ERROR));
        }

        // -> insert VIHF in body
        String content = request.getContent();
        try {
            content = injectVihfToRequestContent(request.getContent(), vihfToken);

        } catch (GenericVihfException e) {
            onError.accept(PolicyResult.failure(GENERATE_VIHF_ERROR));
        }

        // -> sign body
        Future<HttpResponse<io.vertx.core.buffer.Buffer>> futureResponse = signRequestContent(content);
        futureResponse.onFailure(failure -> {
            log.error("Could not send document do signature server", failure);
            onError.accept(PolicyResult.failure(VIHF_SIGNING_ERROR));
        });
        futureResponse.onSuccess(response -> {
            if (response.statusCode() == HttpStatusCode.OK_200) {
                log.error("VIHF successfully signed");
                // TODO CONVERT REPORT
                Gson gson = new Gson();
                ESignSanteSignatureReport report = gson.fromJson(response.bodyAsString(), ESignSanteSignatureReport.class);
                String signedBody = new String(Base64.getDecoder().decode(report.getDocSigne()));
                onSuccess.accept(signedBody);
            } else {
                log.error("Signing request rejected by Signature server");
                onError.accept(PolicyResult.failure(VIHF_SIGNING_ERROR));
            }
        });
    }

    private String generateOpenSamlVihfAndSign(ExecutionContext context) throws GenericVihfException {

        EvaluableRequest request = (EvaluableRequest) context.getTemplateEngine().getTemplateContext()
                .lookupVariable(REQUEST_TEMPLATE_VARIABLE);

        UserInfos userInfos = getUserInfos(context);
        String workSituationId = request.getHeaders().get(WORK_SITUATION_HEADER);
        String insHeader = request.getHeaders().get(PATIENT_INS_HEADER);
        OpenSamlVihfBuilder vihfBuilder = new OpenSamlVihfBuilder(userInfos, workSituationId, insHeader, configuration);

        Assertion vihfToken = vihfBuilder.fetchAssertion();
        vihfToken.setSignature(prepareSignature());

        return insertVIHFinMessageAndSignIt(vihfToken, request.getContent());
    }

    private void initRequestResponseProperties(ExecutionContext context, String requestContent) {
        context
                .getTemplateEngine()
                .getTemplateContext()
                .setVariable(REQUEST_TEMPLATE_VARIABLE, new EvaluableRequest(context.request(), requestContent));
    }

    private Future<HttpResponse<io.vertx.core.buffer.Buffer>> signRequestContent(String content) {
        HttpClient httpClient = vertx.createHttpClient(httpClientOptions);
        WebClient webClient = WebClient.wrap(httpClient);
        io.vertx.core.buffer.Buffer buffer = io.vertx.core.buffer.Buffer.buffer(content);
        MultipartForm form = MultipartForm.create().attribute(ID_SIGN_CONF_KEY, configuration.getSigningConfigId())
                .attribute(SIGN_SECRET_KEY, configuration.getClientSecret())
                .binaryFileUpload("file", "file", buffer, MediaType.MEDIA_APPLICATION_OCTET_STREAM.toMediaString());

        return webClient
                .post(configuration.getDigitalSigningEndpoint())
                .putHeader(CONTENT_TYPE_HEADER, MULTIPART_FORM_HEADER)
                .putHeader(ACCEPT_HEADER, JSON_HEADER)
                .sendMultipartForm(form);
    }

    private String injectVihfToRequestContent(String requestContent, String vihf)
            throws GenericVihfException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder builder = dbFactory.newDocumentBuilder();

            Document body = builder.parse(new InputSource(new StringReader(requestContent)));
            Node vihfFragment = builder.parse(new InputSource(new StringReader(vihf))).getDocumentElement();

            Node soapHeader = body.getElementsByTagNameNS("*", "Header").item(0);
            Node vihfNode = body.importNode(vihfFragment, true);
            soapHeader.appendChild(vihfNode);

            return getXMLStringFromDocument(body);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            log.error("XML parsing error", e);
            throw new GenericVihfException("XML parsing error", e.getCause());
        }

    }

    private String getXMLStringFromDocument(Document doc) throws GenericVihfException {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            return writer.toString();
        } catch (TransformerException e) {
            log.error("Could not convert XML tree to String", e);
            throw new GenericVihfException(e.getMessage(), e.getCause());
        }

    }

    private UserInfos getUserInfos(ExecutionContext context) throws GenericVihfException {
        String payload = (String) context.getAttribute(USER_INFOS_PAYLOAD_KEY);
        try {
            return mapper.readValue(payload, UserInfos.class);
        } catch (Exception e) {
            log.error("Could not read User Infos", e);
            throw new GenericVihfException("Could not read User Infos", e.getCause());
        }
    }

    private static boolean isASuccessfulResponse(Response response) {
        switch (response.status() / 100) {
            case 1:
            case 2:
            case 3:
                return true;
            default:
                return false;
        }
    }

    private Credential initSigningCredential(GenerateVIHFPolicyConfiguration configuration) {
        try {
            BasicX509Credential credential = new BasicX509Credential();

            CertificateFactory certFac = CertificateFactory.getInstance("x509");
            InputStream signingCertIS = new ByteArrayInputStream(configuration.getSigningCertificate().getBytes(StandardCharsets.UTF_8));
            X509Certificate signingCertificate = (X509Certificate) certFac.generateCertificate(signingCertIS);
            signingCertIS.close();

//            List<X509Certificate> certChain = new ArrayList<>();
//            String acCertsString = configuration.getAcCerts();
//            acCertsString.replaceAll("-----END CERTIFICATE-----", "-----END CERTIFICATE-----;");
//            String[] acCerts =  acCertsString.split(";");
//
//            for (String cert : acCerts) {
//                log.error("AC cert : " + cert);
//                InputStream is = new ByteArrayInputStream(cert.getBytes(StandardCharsets.UTF_8));
//                X509Certificate ac = (X509Certificate) certFac.generateCertificate(is);
//                certChain.add(ac);
//            }

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(
                    Base64.getDecoder().decode(configuration.getSigningPrivateKey()));
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            credential.setPrivateKey(privateKey);
            credential.setEntityCertificate(signingCertificate);
//            credential.setEntityCertificateChain(certChain);
            credential.setPublicKey(signingCertificate.getPublicKey());

            return credential;
        } catch (Exception e) {
            log.error("Could not generate credential from certificate", e);
            throw new RuntimeException("Could not generate credential from certificate", e);
        }

    }

    private KeyInfo getKeyInfo(Credential credential) throws SecurityException {
        KeyInfo keyinfo = null;
        X509KeyInfoGeneratorFactory factory;
        factory = new X509KeyInfoGeneratorFactory();
        factory.setEmitEntityCertificate(true);
        // Creating a KeyInfo object and attaching to the Signature object is
        // mandatory for the user certificate to appear at the signature.
        keyinfo = factory.newInstance().generate(credential);
        return keyinfo;
    }

    private Signature prepareSignature() throws GenericVihfException {
        try {
            Signature signature = (Signature) org.opensaml.Configuration
                    .getBuilderFactory().getBuilder(
                            Signature.DEFAULT_ELEMENT_NAME).buildObject(
                            Signature.DEFAULT_ELEMENT_NAME);

            log.info("Set signingCredential to : " + signingCredential);
            signature.setSigningCredential(signingCredential);
            signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA1);
            signature.setCanonicalizationAlgorithm(SignatureConstants.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
            signature.setKeyInfo(getKeyInfo(signingCredential));

            return signature;
        } catch (SecurityException e) {
            log.error("Could not generate saml signatrure element", e);
            throw new GenericVihfException(VIHF_SIGNING_ERROR, e);
        }
    }

    private String insertVIHFinMessageAndSignIt(Assertion vihfToken, String requestContent) throws GenericVihfException {
        MarshallerFactory marshallerFactory = org.opensaml.Configuration
                .getMarshallerFactory();
        Marshaller marshaller = marshallerFactory.getMarshaller(vihfToken);

        try {
            Element vihfFragment = marshaller.marshall(vihfToken);
            Signer.signObject(vihfToken.getSignature());

            // import body
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder builder = null;
            builder = dbFactory.newDocumentBuilder();
            Document body = builder.parse(new InputSource(new StringReader(requestContent)));

            Node soapHeader = body.getElementsByTagNameNS("*", "Header").item(0);
            Node vihfNode = body.importNode(vihfFragment, true);
            soapHeader.appendChild(vihfNode);

            return getXMLStringFromDocument(body);
        } catch (SignatureException | ParserConfigurationException | IOException | SAXException | MarshallingException e) {
            log.error("error", e);
            throw new GenericVihfException(VIHF_SIGNING_ERROR, e);
        }
    }
}
