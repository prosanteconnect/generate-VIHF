/*
 * Copyright A.N.S 2022
 */
package fr.ans.psc;

import fr.ans.psc.model.UserInfos;
import fr.ans.psc.model.VihfBuilder;
import io.gravitee.common.http.HttpStatusCode;
import io.gravitee.gateway.api.ExecutionContext;
import io.gravitee.gateway.api.Request;
import io.gravitee.gateway.api.Response;
import io.gravitee.policy.api.PolicyChain;
import io.gravitee.policy.api.PolicyResult;
import io.gravitee.policy.api.annotations.OnRequest;
import io.gravitee.policy.api.annotations.OnResponse;

@SuppressWarnings("unused")
public class GenerateVIHFPolicy {

    /**
     * The associated configuration to this GenerateVIHF Policy
     */
    private GenerateVIHFPolicyConfiguration configuration;

    /**
     * Create a new GenerateVIHF Policy instance based on its associated configuration
     *
     * @param configuration the associated configuration to the new GenerateVIHF Policy instance
     */
    public GenerateVIHFPolicy(GenerateVIHFPolicyConfiguration configuration) {
        this.configuration = configuration;
    }

    @OnRequest
    public void onRequest(Request request, Response response, ExecutionContext executionContext, PolicyChain policyChain) {

        // TODO récupérer les éléments nécessaires dans les headers & le contexte Gravitee
        String payload = (String) executionContext.getAttribute("openid.userinfo.payload");
        String structureId = request.headers().getFirst("X-structureIdHeader");
        String workSituId = request.headers().getFirst("X-workSituationHeader");
        String ins = request.headers().getFirst("X-insHeader");

        // TODO transformer le payload en un objet UserInfos qu'on puisse parser

        // TODO générer la grappe d'objets custom
        UserInfos userInfos = new UserInfos();

        // TODO convertir la grappe en XML
        VihfBuilder vihfBuilder = new VihfBuilder(userInfos, structureId, workSituId, ins, configuration);
        String vihf = vihfBuilder.generateVIHF();

        // ajouter le jeton VIHF généré au contexte gravitee
        executionContext.setAttribute("VIHF", vihf);
        // sortir de l'exécution de la policy
        policyChain.doNext(request, response);
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
