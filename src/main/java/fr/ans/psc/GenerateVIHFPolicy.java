/*
 * Copyright A.N.S 2022
 */
package fr.ans.psc;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ans.psc.model.prosanteconnect.UserInfos;
import io.gravitee.common.http.HttpStatusCode;
import io.gravitee.common.http.MediaType;
import io.gravitee.gateway.api.ExecutionContext;
import io.gravitee.gateway.api.Request;
import io.gravitee.gateway.api.Response;
import io.gravitee.policy.api.PolicyChain;
import io.gravitee.policy.api.PolicyResult;
import io.gravitee.policy.api.annotations.OnRequest;
import io.gravitee.policy.api.annotations.OnResponse;

import java.io.IOException;
import java.util.Arrays;

@SuppressWarnings("unused")
public class GenerateVIHFPolicy {

    /**
     * The associated configuration to this GenerateVIHF Policy
     */
    private GenerateVIHFPolicyConfiguration configuration;

    private ObjectMapper objectMapper;

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
        } catch (Exception e) {
            policyChain.failWith(PolicyResult.failure(HttpStatusCode.INTERNAL_SERVER_ERROR_500, Arrays.toString(e.getStackTrace()), MediaType.APPLICATION_JSON));
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
