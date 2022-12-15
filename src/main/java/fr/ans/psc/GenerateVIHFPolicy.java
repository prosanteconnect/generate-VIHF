/*
 * Copyright A.N.S 2022
 */
package fr.ans.psc;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ans.psc.exception.GenericVihfException;
import fr.ans.psc.model.prosanteconnect.UserInfos;
import io.gravitee.common.http.HttpStatusCode;
import io.gravitee.gateway.api.ExecutionContext;
import io.gravitee.gateway.api.Request;
import io.gravitee.gateway.api.Response;
import io.gravitee.gateway.api.buffer.Buffer;
import io.gravitee.gateway.api.el.EvaluableRequest;
import io.gravitee.gateway.api.stream.BufferedReadWriteStream;
import io.gravitee.gateway.api.stream.ReadWriteStream;
import io.gravitee.gateway.api.stream.SimpleReadWriteStream;
import io.gravitee.policy.api.PolicyChain;
import io.gravitee.policy.api.PolicyResult;
import io.gravitee.policy.api.annotations.OnRequestContent;
import io.gravitee.policy.api.annotations.OnResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
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
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class GenerateVIHFPolicy {

    private final Logger log = LoggerFactory.getLogger(GenerateVIHFPolicy.class);
    /**
     * The associated configuration to this GenerateVIHF Policy
     */
    private final GenerateVIHFPolicyConfiguration configuration;

    private static final String REQUEST_TEMPLATE_VARIABLE = "request";
    private static final String RESPONSE_TEMPLATE_VARIABLE = "response";
    private static final String GENERATE_VIHF_ERROR = "GENERATE_VIHF_ERROR";
    private static final String USER_INFOS_PAYLOAD_KEY = "openid.userinfo.payload";

    private final ObjectMapper mapper;

    /**
     * Create a new GenerateVIHF Policy instance based on its associated configuration
     *
     * @param configuration the associated configuration to the new GenerateVIHF Policy instance
     */
    public GenerateVIHFPolicy(GenerateVIHFPolicyConfiguration configuration) {
        this.configuration = configuration;
        this.mapper = new ObjectMapper();
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

                generateVihfAndSign(
                        executionContext,
                        result -> {
                            if (result.length() > 0) {
                                // REWRITE BUFFER WITH TRANSFORMED RESULT
                                super.write(Buffer.buffer(result));
                                super.end();
                            }

                        },
                        policyChain::streamFailWith);
            }
        };
    }

    private void generateVihfAndSign(ExecutionContext executionContext, Consumer<String> onSuccess, Consumer<PolicyResult> onError) {
        final Consumer<Void> onSuccessCallback;
        final Consumer<PolicyResult> onErrorCallback;

        // BUSINESS METHOD
        String vihfToken = null;
        EvaluableRequest request = (EvaluableRequest) executionContext.getTemplateEngine().getTemplateContext()
                .lookupVariable(REQUEST_TEMPLATE_VARIABLE);

        try {
            UserInfos userInfos = getUserInfos(executionContext);
            String workSituationId = request.getHeaders().get("X-Worksituation");
            String insHeader = request.getHeaders().get("X-insHeader");

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

        // -> write buffer and end
        onSuccess.accept(content);
    }

    @OnResponse
    public void onResponse(Request request, Response response, PolicyChain policyChain) {
        if (isASuccessfulResponse(response)) {
            policyChain.doNext(request, response);
        } else {
            policyChain.failWith(
                    PolicyResult.failure(HttpStatusCode.INTERNAL_SERVER_ERROR_500, "Not a successful response :-("));
        }
    }

    private void initRequestResponseProperties(ExecutionContext context, String requestContent) {
        context
                .getTemplateEngine()
                .getTemplateContext()
                .setVariable(REQUEST_TEMPLATE_VARIABLE, new EvaluableRequest(context.request(), requestContent));
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
            log.error("XML parsing error");
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
            log.error("Could not convert XML tree to String");
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

}
