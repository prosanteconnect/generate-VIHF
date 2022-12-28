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
import org.opensaml.DefaultBootstrap;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.ws.wssecurity.WSSecurityConstants;
import org.opensaml.xml.ConfigurationException;
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
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.function.Consumer;

import static fr.ans.psc.utils.Constants.*;
import static io.gravitee.common.util.VertxProxyOptionsUtils.setSystemProxy;

@SuppressWarnings("unused")
public class GenerateVIHFPolicy {
    private final Logger log = LoggerFactory.getLogger(GenerateVIHFPolicy.class);
    private final GenerateVIHFPolicyConfiguration configuration;
    private final ObjectMapper mapper;
    private Vertx vertx;
    private HttpClientOptions httpClientOptions;

    /**
     * Create a new GenerateVIHF Policy instance based on its associated configuration
     *
     * @param configuration the associated configuration to the new GenerateVIHF Policy instance
     */
    public GenerateVIHFPolicy(GenerateVIHFPolicyConfiguration configuration){
        this.configuration = configuration;
        this.mapper = new ObjectMapper();
        try {
            DefaultBootstrap.bootstrap();
        } catch (ConfigurationException e) {
            log.error("Unable to bootstrap opensaml", e);
            throw new RuntimeException(e);
        }

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
                initVertxClient(executionContext);
                generateOpenSamlVihfAndSign(
                        executionContext,
                        signedBody -> {
                            if (signedBody.length() > 0) {
                                HttpHeaders headers = executionContext.request().headers();
                                headers.remove(CONTENT_LENGTH_HEADER);
                                headers.set(TRANSFER_ENCODING_HEADER, CHUNKED);
                                log.error(signedBody);
                                Buffer buf = Buffer.buffer();
                                buf.appendString(signedBody);
                                super.write(buf);
                            }
                            super.end();
                        },
                        policyChain::streamFailWith);
            }
        };
    }

    private void generateOpenSamlVihfAndSign(ExecutionContext context, Consumer<String> onSuccess,
                                             Consumer<PolicyResult> onError) {

        EvaluableRequest request = (EvaluableRequest) context.getTemplateEngine().getTemplateContext()
                .lookupVariable(REQUEST_TEMPLATE_VARIABLE);

        String bodyWithToken = request.getContent();

        try {
            UserInfos userInfos = getUserInfos(context);
            String workSituationId = request.getHeaders().get(WORK_SITUATION_HEADER);
            String insHeader = request.getHeaders().get(PATIENT_INS_HEADER);
            OpenSamlVihfBuilder vihfBuilder = new OpenSamlVihfBuilder(userInfos, workSituationId, insHeader, configuration);

            Assertion vihfToken = vihfBuilder.fetchAssertion();

            bodyWithToken = insertVIHFinMessageAndSignIt(vihfToken, bodyWithToken);
        } catch (GenericVihfException e) {
            onError.accept(PolicyResult.failure(GENERATE_VIHF_ERROR));
        }


        Future<HttpResponse<io.vertx.core.buffer.Buffer>> futureResponse = signRequestContent(bodyWithToken);
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

    private String insertVIHFinMessageAndSignIt(Assertion vihfToken, String requestContent) throws GenericVihfException {
        MarshallerFactory marshallerFactory = org.opensaml.Configuration
                .getMarshallerFactory();
        Marshaller marshaller = marshallerFactory.getMarshaller(vihfToken);

        try {
            // import body
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder builder = null;
            builder = dbFactory.newDocumentBuilder();
            Document body = builder.parse(new InputSource(new StringReader(requestContent)));
            Node wsseSec = body.createElementNS(WSSecurityConstants.WSSE_NS, "wsse:Security");

            Element vihfFragment = marshaller.marshall(vihfToken);

            Node vihfNode = body.importNode(vihfFragment, true);
            wsseSec.appendChild(vihfNode);

            Node soapHeader = body.getElementsByTagNameNS("*", "Header").item(0);
            soapHeader.appendChild(wsseSec);

            return getXMLStringFromDocument(body);
        } catch (ParserConfigurationException | IOException | SAXException | MarshallingException e) {
            log.error("error", e);
            throw new GenericVihfException(VIHF_SIGNING_ERROR, e);
        }
    }
}
