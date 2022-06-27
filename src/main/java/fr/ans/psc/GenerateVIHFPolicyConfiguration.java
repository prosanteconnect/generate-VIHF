/*
 * Copyright A.N.S 2022
 */
package fr.ans.psc;

import io.gravitee.policy.api.PolicyConfiguration;
import io.gravitee.common.http.HttpMethod;

@SuppressWarnings("unused")
public class GenerateVIHFPolicyConfiguration implements PolicyConfiguration {

    /**
     * A String parameter
     */
    private String stringParam = "defaultValue";

    /**
     * A integer parameter
     */
    private int integerParam;

    /**
     * A integer parameter
     */
    private boolean booleanParam;

    /**
     * An enum parameter
     */
    private HttpMethod httpMethod;

    /**
     * Get the String parameter
     *
     * @return the String parameter
     */
    public String getStringParam() {
        return stringParam;
    }

    /**
     * Get the integer parameter
     *
     * @return the integer parameter
     */
    public int getIntegerParam() {
        return integerParam;
    }

    /**
     * Get the boolean parameter
     *
     * @return the boolean parameter
     */
    public boolean getBooleanParam() {
        return booleanParam;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }
}
