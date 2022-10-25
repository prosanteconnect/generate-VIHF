/*
 * Copyright A.N.S 2022
 */
package fr.ans.psc;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.function.Function;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.ans.psc.exception.WrongWorkSituationKeyException;
import fr.ans.psc.model.prosanteconnect.UserInfos;
import io.gravitee.common.http.HttpStatusCode;
import io.gravitee.common.http.MediaType;
import io.gravitee.gateway.api.ExecutionContext;
import io.gravitee.gateway.api.Request;
import io.gravitee.gateway.api.Response;
import io.gravitee.gateway.api.buffer.Buffer;
import io.gravitee.gateway.api.http.stream.TransformableRequestStreamBuilder;
import io.gravitee.gateway.api.stream.ReadWriteStream;
import io.gravitee.policy.api.PolicyChain;
import io.gravitee.policy.api.PolicyResult;
import io.gravitee.policy.api.annotations.OnRequestContent;
import io.gravitee.policy.api.annotations.OnResponse;

@SuppressWarnings("unused")
public class GenerateVIHFPolicy {

    private final Logger log = LoggerFactory.getLogger(GenerateVIHFPolicy.class);
    /**
     * The associated configuration to this GenerateVIHF Policy
     */
    private final GenerateVIHFPolicyConfiguration configuration;

    private final ObjectMapper objectMapper;

    /**
     * Create a new GenerateVIHF Policy instance based on its associated configuration
     *
     * @param configuration the associated configuration to the new GenerateVIHF Policy instance
     */
    public GenerateVIHFPolicy(GenerateVIHFPolicyConfiguration configuration) {
        this.configuration = configuration;
        this.objectMapper = new ObjectMapper();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@OnRequestContent
    public ReadWriteStream onRequestContent(Request request, Response response, ExecutionContext executionContext, PolicyChain policyChain) {
    	return TransformableRequestStreamBuilder
                .on(request)
                .chain(policyChain)
                .contentType(MediaType.APPLICATION_XML)
                .transform(generate(executionContext, request, configuration, policyChain))
                .build();
    }
    
    private Function<Buffer,Buffer> generate(ExecutionContext executionContext, Request request, GenerateVIHFPolicyConfiguration configuration, PolicyChain policyChain){
    	return input -> {
        String payload = (String) executionContext.getAttribute("openid.userinfo.payload");
        String workSituId = request.headers().get("X-Worksituation");
        String ins = request.headers().get("X-insHeader");

        UserInfos userInfos = null;
		try {
			userInfos = this.objectMapper.readValue(payload, UserInfos.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        VihfBuilder vihfBuilder = new VihfBuilder(userInfos, workSituId, ins, configuration);
        StringWriter writer = new StringWriter();

        try {
            log.info("generating VIHF token...");
            String vihf = vihfBuilder.generateVIHF();
         
            // ajouter le jeton VIHF généré au body
            Document body = DocumentBuilderFactory.newInstance().newDocumentBuilder().
            		parse(new InputSource(new StringReader(new String(input.getBytes()))));
            Node assertion = DocumentBuilderFactory.newInstance().newDocumentBuilder().
            		parse(new InputSource(new StringReader(vihf))).getDocumentElement();
            assertion = body.importNode(assertion, true);
            NodeList headers = body.getElementsByTagName("soap:Header");
            Element header = (Element)headers.item(0);
            header.appendChild(assertion);
            DOMSource domSource = new DOMSource(body);
            
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);

        } catch (WrongWorkSituationKeyException e) {
            policyChain.failWith(PolicyResult.failure(HttpStatusCode.BAD_REQUEST_400, e.getMessage()));
        } catch (Exception e) {
            log.error("Something went wrong when generating VIHF token", e);
            policyChain.failWith(PolicyResult.failure("Something went wrong when generating VIHF token"));
        }
        return Buffer.buffer(writer.toString());
    	};

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
