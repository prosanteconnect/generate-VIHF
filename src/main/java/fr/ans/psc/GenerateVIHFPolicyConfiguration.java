/*
 * Copyright A.N.S 2022
 */
package fr.ans.psc;

import io.gravitee.policy.api.PolicyConfiguration;

@SuppressWarnings("unused")
public class GenerateVIHFPolicyConfiguration implements PolicyConfiguration {

    private String certificateDN;

    private String structureId;

    private String lpsName;

    private String lpsVersion;

    private String lpsHomologationNumber;

    private String digitalSigningEndpoint;

    private String signingConfigId;

    private String clientSecret;

    private boolean useSystemProxy;

    private boolean useSSL;

    public String getDigitalSigningEndpoint() {
        return digitalSigningEndpoint;
    }

    public void setDigitalSigningEndpoint(String digitalSigningEndpoint) {
        this.digitalSigningEndpoint = digitalSigningEndpoint;
    }

    public String getSigningConfigId() {
        return signingConfigId;
    }

    public void setSigningConfigId(String signingConfigId) {
        this.signingConfigId = signingConfigId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public boolean isUseSystemProxy() {
        return useSystemProxy;
    }

    public void setUseSystemProxy(boolean useSystemProxy) {
        this.useSystemProxy = useSystemProxy;
    }

    public boolean isUseSSL() {
        return useSSL;
    }

    public void setUseSSL(boolean useSSL) {
        this.useSSL = useSSL;
    }

    public String getCertificateDN() {
        return certificateDN;
    }

    public void setCertificateDN(String certificateDN) {
        this.certificateDN = certificateDN;
    }

    public String getStructureId() {
        return structureId;
    }

    public void setStructureId(String structureId) {
        this.structureId = structureId;
    }

    public String getLpsName() {
        return lpsName;
    }

    public void setLpsName(String lpsName) {
        this.lpsName = lpsName;
    }

    public String getLpsVersion() {
        return lpsVersion;
    }

    public void setLpsVersion(String lpsVersion) {
        this.lpsVersion = lpsVersion;
    }

    public String getLpsHomologationNumber() {
        return lpsHomologationNumber;
    }

    public void setLpsHomologationNumber(String lpsHomologationNumber) {
        this.lpsHomologationNumber = lpsHomologationNumber;
    }
}
