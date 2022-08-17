/*
 * Copyright A.N.S 2022
 */
package fr.ans.psc;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ans.psc.exception.WrongWorkSituationKeyException;
import fr.ans.psc.model.prosanteconnect.UserInfos;
import io.gravitee.common.http.HttpStatusCode;
import io.gravitee.gateway.api.ExecutionContext;
import io.gravitee.gateway.api.Request;
import io.gravitee.gateway.api.Response;
import io.gravitee.policy.api.PolicyChain;
import io.gravitee.policy.api.PolicyResult;
import io.gravitee.policy.api.annotations.OnRequest;
import io.gravitee.policy.api.annotations.OnResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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

    @OnRequest
    public void onRequest(Request request, Response response, ExecutionContext executionContext, PolicyChain policyChain) throws IOException {

        String payload = (String) executionContext.getAttribute("openid.userinfo.payload");
        String workSituId = request.headers().get("X-Worksituation");
        String ins = request.headers().get("X-Ins");

        UserInfos userInfos = objectMapper.readValue(payload, UserInfos.class);

        VihfBuilder vihfBuilder = new VihfBuilder(userInfos, workSituId, ins, configuration);

        try {
            String vihf = vihfBuilder.generateVIHF();

            // ajouter le jeton VIHF généré au contexte gravitee
            executionContext.setAttribute("vihf.token.payload", vihf);
            // sortir de l'exécution de la policy
            policyChain.doNext(request, response);
        } catch (WrongWorkSituationKeyException e) {
            policyChain.failWith(PolicyResult.failure(HttpStatusCode.BAD_REQUEST_400, e.getMessage()));
        } catch (Exception e) {
            log.error("Something went wrong when generating VIHF token", e);
            policyChain.failWith(PolicyResult.failure("Something went wrong when generating VIHF token"));
        }

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
