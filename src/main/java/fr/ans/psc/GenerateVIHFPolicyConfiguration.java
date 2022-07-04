/*
 * Copyright A.N.S 2022
 */
package fr.ans.psc;

import io.gravitee.policy.api.PolicyConfiguration;

@SuppressWarnings("unused")
public class GenerateVIHFPolicyConfiguration implements PolicyConfiguration {

    private String certificateDN;

    private String lpsName;

    private String lpsVersion;

    private String lpsHomologationNumber;

    public String getCertificateDN() {
        return certificateDN;
    }

    public void setCertificateDN(String certificateDN) {
        this.certificateDN = certificateDN;
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
