/**
 * Copyright (C) 2022 ANS (https://esante.gouv.fr)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
