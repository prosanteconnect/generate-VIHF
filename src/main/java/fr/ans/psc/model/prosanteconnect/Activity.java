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
package fr.ans.psc.model.prosanteconnect;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Activity {
    @JsonProperty("codeModeExercice")
    private String professionalModeCode = null;

    @JsonProperty("codeSecteurDactivite")
    private String activitySectorCode = null;

    @JsonProperty("codeSectionPharmacien")
    private String pharmacistTableSectionCode = null;

    @JsonProperty("codeRole")
    private String roleCode = null;

    @JsonProperty("numeroSiretSite")
    private String siretSiteNumber = null;

    @JsonProperty("numeroSirenSite")
    private String sirenSiteNumber = null;

    @JsonProperty("numeroFinessSite")
    private String finessSiteNumber = null;

    @JsonProperty("numeroFinessetablissementJuridique")
    private String finessLegalCompanyNumber = null;

    @JsonProperty("identifiantTechniqueDeLaStructure")
    private String companyTechnicalIdentifier = null;

    @JsonProperty("raisonSocialeSite")
    private String companyName = null;

    @JsonProperty("enseigneCommercialeSite")
    private String companyCommercialSign = null;

    @JsonProperty("complementDestinataire")
    private String companyAdditionalAddress = null;

    @JsonProperty("complementPointGeographique")
    private String companyGeographicalPointComplement = null;

    @JsonProperty("numeroVoie")
    private String companyWayNumber = null;

    @JsonProperty("indiceRepetitionVoie")
    private String companyRepeatIndex = null;

    @JsonProperty("codeTypeDeVoie")
    private String companyWayType = null;

    @JsonProperty("libelleVoie")
    private String companyWayLabel = null;

    @JsonProperty("mentionDistribution")
    private String companyDistributionMention = null;

    @JsonProperty("bureauCedex")
    private String companyCedexOffice = null;

    @JsonProperty("codePostal")
    private String companyPostalCode = null;

    @JsonProperty("codeCommune")
    private String companyTownCode = null;

    @JsonProperty("codePays")
    private String companyCountryCode = null;

    @JsonProperty("telephone")
    private String companyPhone1 = null;

    @JsonProperty("telephone2")
    private String companyPhone2 = null;

    @JsonProperty("telecopie")
    private String companyFax = null;

    @JsonProperty("adresseEMail")
    private String companyEmail = null;

    @JsonProperty("codeDepartement")
    private String companyCountyCode = null;

    @JsonProperty("ancienIdentifiantDeLaStructure")
    private String companyOldIdentifier = null;

    @JsonProperty("autoriteDenregistrement")
    private String companyRegistrationAuthority = null;

    @JsonProperty("codeGenreActivite")
    private String activityKindCode = null;

    public String getActivityKindCode() {
        return activityKindCode;
    }

    public void setActivityKindCode(String activityKindCode) {
        this.activityKindCode = activityKindCode;
    }

    public Activity professionalModeCode(String professionalModeCode) {
        this.professionalModeCode = professionalModeCode;
        return this;
    }

    /**
     * Get professionalModeCode
     * @return professionalModeCode
     **/
    public String getProfessionalModeCode() {
        return professionalModeCode;
    }

    public void setProfessionalModeCode(String professionalModeCode) {
        this.professionalModeCode = professionalModeCode;
    }

    public Activity activitySectorCode(String activitySectorCode) {
        this.activitySectorCode = activitySectorCode;
        return this;
    }

    /**
     * Get activitySectorCode
     * @return activitySectorCode
     **/
    public String getActivitySectorCode() {
        return activitySectorCode;
    }

    public void setActivitySectorCode(String activitySectorCode) {
        this.activitySectorCode = activitySectorCode;
    }

    public Activity pharmacistTableSectionCode(String pharmacistTableSectionCode) {
        this.pharmacistTableSectionCode = pharmacistTableSectionCode;
        return this;
    }

    /**
     * Get pharmacistTableSectionCode
     * @return pharmacistTableSectionCode
     **/
    public String getPharmacistTableSectionCode() {
        return pharmacistTableSectionCode;
    }

    public void setPharmacistTableSectionCode(String pharmacistTableSectionCode) {
        this.pharmacistTableSectionCode = pharmacistTableSectionCode;
    }

    public Activity roleCode(String roleCode) {
        this.roleCode = roleCode;
        return this;
    }

    /**
     * Get roleCode
     * @return roleCode
     **/
    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public Activity siretSiteNumber(String siretSiteNumber) {
        this.siretSiteNumber = siretSiteNumber;
        return this;
    }

    /**
     * Get siretSiteNumber
     * @return siretSiteNumber
     **/
    public String getSiretSiteNumber() {
        return siretSiteNumber;
    }

    public void setSiretSiteNumber(String siretSiteNumber) {
        this.siretSiteNumber = siretSiteNumber;
    }

    public Activity sirenSiteNumber(String sirenSiteNumber) {
        this.sirenSiteNumber = sirenSiteNumber;
        return this;
    }

    /**
     * Get sirenSiteNumber
     * @return sirenSiteNumber
     **/
    public String getSirenSiteNumber() {
        return sirenSiteNumber;
    }

    public void setSirenSiteNumber(String sirenSiteNumber) {
        this.sirenSiteNumber = sirenSiteNumber;
    }

    public Activity finessSiteNumber(String finessSiteNumber) {
        this.finessSiteNumber = finessSiteNumber;
        return this;
    }

    /**
     * Get finessSiteNumber
     * @return finessSiteNumber
     **/
    public String getFinessSiteNumber() {
        return finessSiteNumber;
    }

    public void setFinessSiteNumber(String finessSiteNumber) {
        this.finessSiteNumber = finessSiteNumber;
    }

    public Activity finessLegalCompanyNumber(String finessLegalCompanyNumber) {
        this.finessLegalCompanyNumber = finessLegalCompanyNumber;
        return this;
    }

    /**
     * Get finessLegalCompanyNumber
     * @return finessLegalCompanyNumber
     **/
    public String getFinessLegalCompanyNumber() {
        return finessLegalCompanyNumber;
    }

    public void setFinessLegalCompanyNumber(String finessLegalCompanyNumber) {
        this.finessLegalCompanyNumber = finessLegalCompanyNumber;
    }

    public Activity companyTechnicalIdentifier(String companyTechnicalIdentifier) {
        this.companyTechnicalIdentifier = companyTechnicalIdentifier;
        return this;
    }

    /**
     * Get companyTechnicalIdentifier
     * @return companyTechnicalIdentifier
     **/
    public String getCompanyTechnicalIdentifier() {
        return companyTechnicalIdentifier;
    }

    public void setCompanyTechnicalIdentifier(String companyTechnicalIdentifier) {
        this.companyTechnicalIdentifier = companyTechnicalIdentifier;
    }

    public Activity companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    /**
     * Get companyName
     * @return companyName
     **/
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Activity companyCommercialSign(String companyCommercialSign) {
        this.companyCommercialSign = companyCommercialSign;
        return this;
    }

    /**
     * Get companyCommercialSign
     * @return companyCommercialSign
     **/
    public String getCompanyCommercialSign() {
        return companyCommercialSign;
    }

    public void setCompanyCommercialSign(String companyCommercialSign) {
        this.companyCommercialSign = companyCommercialSign;
    }

    public Activity companyAdditionalAddress(String companyAdditionalAddress) {
        this.companyAdditionalAddress = companyAdditionalAddress;
        return this;
    }

    /**
     * Get companyAdditionalAddress
     * @return companyAdditionalAddress
     **/
    public String getCompanyAdditionalAddress() {
        return companyAdditionalAddress;
    }

    public void setCompanyAdditionalAddress(String companyAdditionalAddress) {
        this.companyAdditionalAddress = companyAdditionalAddress;
    }

    public Activity companyGeographicalPointComplement(String companyGeographicalPointComplement) {
        this.companyGeographicalPointComplement = companyGeographicalPointComplement;
        return this;
    }

    /**
     * Get companyGeographicalPointComplement
     * @return companyGeographicalPointComplement
     **/
    public String getCompanyGeographicalPointComplement() {
        return companyGeographicalPointComplement;
    }

    public void setCompanyGeographicalPointComplement(String companyGeographicalPointComplement) {
        this.companyGeographicalPointComplement = companyGeographicalPointComplement;
    }

    public Activity companyWayNumber(String companyWayNumber) {
        this.companyWayNumber = companyWayNumber;
        return this;
    }

    /**
     * Get companyWayNumber
     * @return companyWayNumber
     **/
    public String getCompanyWayNumber() {
        return companyWayNumber;
    }

    public void setCompanyWayNumber(String companyWayNumber) {
        this.companyWayNumber = companyWayNumber;
    }

    public Activity companyRepeatIndex(String companyRepeatIndex) {
        this.companyRepeatIndex = companyRepeatIndex;
        return this;
    }

    /**
     * Get companyRepeatIndex
     * @return companyRepeatIndex
     **/
    public String getCompanyRepeatIndex() {
        return companyRepeatIndex;
    }

    public void setCompanyRepeatIndex(String companyRepeatIndex) {
        this.companyRepeatIndex = companyRepeatIndex;
    }

    public Activity companyWayType(String companyWayType) {
        this.companyWayType = companyWayType;
        return this;
    }

    /**
     * Get companyWayType
     * @return companyWayType
     **/
    public String getCompanyWayType() {
        return companyWayType;
    }

    public void setCompanyWayType(String companyWayType) {
        this.companyWayType = companyWayType;
    }

    public Activity companyWayLabel(String companyWayLabel) {
        this.companyWayLabel = companyWayLabel;
        return this;
    }

    /**
     * Get companyWayLabel
     * @return companyWayLabel
     **/
    public String getCompanyWayLabel() {
        return companyWayLabel;
    }

    public void setCompanyWayLabel(String companyWayLabel) {
        this.companyWayLabel = companyWayLabel;
    }

    public Activity companyDistributionMention(String companyDistributionMention) {
        this.companyDistributionMention = companyDistributionMention;
        return this;
    }

    /**
     * Get companyDistributionMention
     * @return companyDistributionMention
     **/
    public String getCompanyDistributionMention() {
        return companyDistributionMention;
    }

    public void setCompanyDistributionMention(String companyDistributionMention) {
        this.companyDistributionMention = companyDistributionMention;
    }

    public Activity companyCedexOffice(String companyCedexOffice) {
        this.companyCedexOffice = companyCedexOffice;
        return this;
    }

    /**
     * Get companyCedexOffice
     * @return companyCedexOffice
     **/
    public String getCompanyCedexOffice() {
        return companyCedexOffice;
    }

    public void setCompanyCedexOffice(String companyCedexOffice) {
        this.companyCedexOffice = companyCedexOffice;
    }

    public Activity companyPostalCode(String companyPostalCode) {
        this.companyPostalCode = companyPostalCode;
        return this;
    }

    /**
     * Get companyPostalCode
     * @return companyPostalCode
     **/
    public String getCompanyPostalCode() {
        return companyPostalCode;
    }

    public void setCompanyPostalCode(String companyPostalCode) {
        this.companyPostalCode = companyPostalCode;
    }

    public Activity companyTownCode(String companyTownCode) {
        this.companyTownCode = companyTownCode;
        return this;
    }

    /**
     * Get companyTownCode
     * @return companyTownCode
     **/
    public String getCompanyTownCode() {
        return companyTownCode;
    }

    public void setCompanyTownCode(String companyTownCode) {
        this.companyTownCode = companyTownCode;
    }

    public Activity companyCountryCode(String companyCountryCode) {
        this.companyCountryCode = companyCountryCode;
        return this;
    }

    /**
     * Get companyCountryCode
     * @return companyCountryCode
     **/
    public String getCompanyCountryCode() {
        return companyCountryCode;
    }

    public void setCompanyCountryCode(String companyCountryCode) {
        this.companyCountryCode = companyCountryCode;
    }

    public Activity companyPhone1(String companyPhone1) {
        this.companyPhone1 = companyPhone1;
        return this;
    }

    /**
     * Get companyPhone1
     * @return companyPhone1
     **/
    public String getCompanyPhone1() {
        return companyPhone1;
    }

    public void setCompanyPhone1(String companyPhone1) {
        this.companyPhone1 = companyPhone1;
    }

    public Activity companyPhone2(String companyPhone2) {
        this.companyPhone2 = companyPhone2;
        return this;
    }

    /**
     * Get companyPhone2
     * @return companyPhone2
     **/
    public String getCompanyPhone2() {
        return companyPhone2;
    }

    public void setCompanyPhone2(String companyPhone2) {
        this.companyPhone2 = companyPhone2;
    }

    public Activity companyFax(String companyFax) {
        this.companyFax = companyFax;
        return this;
    }

    /**
     * Get companyFax
     * @return companyFax
     **/
    public String getCompanyFax() {
        return companyFax;
    }

    public void setCompanyFax(String companyFax) {
        this.companyFax = companyFax;
    }

    public Activity companyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
        return this;
    }

    /**
     * Get companyEmail
     * @return companyEmail
     **/
    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public Activity companyCountyCode(String companyCountyCode) {
        this.companyCountyCode = companyCountyCode;
        return this;
    }

    /**
     * Get companyCountyCode
     * @return companyCountyCode
     **/
    public String getCompanyCountyCode() {
        return companyCountyCode;
    }

    public void setCompanyCountyCode(String companyCountyCode) {
        this.companyCountyCode = companyCountyCode;
    }

    public Activity companyOldIdentifier(String companyOldIdentifier) {
        this.companyOldIdentifier = companyOldIdentifier;
        return this;
    }

    /**
     * Get companyOldIdentifier
     * @return companyOldIdentifier
     **/
    public String getCompanyOldIdentifier() {
        return companyOldIdentifier;
    }

    public void setCompanyOldIdentifier(String companyOldIdentifier) {
        this.companyOldIdentifier = companyOldIdentifier;
    }

    public Activity companyRegistrationAuthority(String companyRegistrationAuthority) {
        this.companyRegistrationAuthority = companyRegistrationAuthority;
        return this;
    }

    /**
     * Get companyRegistrationAuthority
     * @return companyRegistrationAuthority
     **/
    public String getCompanyRegistrationAuthority() {
        return companyRegistrationAuthority;
    }

    public void setCompanyRegistrationAuthority(String companyRegistrationAuthority) {
        this.companyRegistrationAuthority = companyRegistrationAuthority;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Activity activity = (Activity) o;
        return Objects.equals(this.professionalModeCode, activity.professionalModeCode) &&
                Objects.equals(this.activitySectorCode, activity.activitySectorCode) &&
                Objects.equals(this.pharmacistTableSectionCode, activity.pharmacistTableSectionCode) &&
                Objects.equals(this.roleCode, activity.roleCode) &&
                Objects.equals(this.siretSiteNumber, activity.siretSiteNumber) &&
                Objects.equals(this.sirenSiteNumber, activity.sirenSiteNumber) &&
                Objects.equals(this.finessSiteNumber, activity.finessSiteNumber) &&
                Objects.equals(this.finessLegalCompanyNumber, activity.finessLegalCompanyNumber) &&
                Objects.equals(this.companyTechnicalIdentifier, activity.companyTechnicalIdentifier) &&
                Objects.equals(this.companyName, activity.companyName) &&
                Objects.equals(this.companyCommercialSign, activity.companyCommercialSign) &&
                Objects.equals(this.companyAdditionalAddress, activity.companyAdditionalAddress) &&
                Objects.equals(this.companyGeographicalPointComplement, activity.companyGeographicalPointComplement) &&
                Objects.equals(this.companyWayNumber, activity.companyWayNumber) &&
                Objects.equals(this.companyRepeatIndex, activity.companyRepeatIndex) &&
                Objects.equals(this.companyWayType, activity.companyWayType) &&
                Objects.equals(this.companyWayLabel, activity.companyWayLabel) &&
                Objects.equals(this.companyDistributionMention, activity.companyDistributionMention) &&
                Objects.equals(this.companyCedexOffice, activity.companyCedexOffice) &&
                Objects.equals(this.companyPostalCode, activity.companyPostalCode) &&
                Objects.equals(this.companyTownCode, activity.companyTownCode) &&
                Objects.equals(this.companyCountryCode, activity.companyCountryCode) &&
                Objects.equals(this.companyPhone1, activity.companyPhone1) &&
                Objects.equals(this.companyPhone2, activity.companyPhone2) &&
                Objects.equals(this.companyFax, activity.companyFax) &&
                Objects.equals(this.companyEmail, activity.companyEmail) &&
                Objects.equals(this.companyCountyCode, activity.companyCountyCode) &&
                Objects.equals(this.companyOldIdentifier, activity.companyOldIdentifier) &&
                Objects.equals(this.companyRegistrationAuthority, activity.companyRegistrationAuthority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(professionalModeCode, activitySectorCode, pharmacistTableSectionCode, roleCode, siretSiteNumber, sirenSiteNumber, finessSiteNumber, finessLegalCompanyNumber, companyTechnicalIdentifier, companyName, companyCommercialSign, companyAdditionalAddress, companyGeographicalPointComplement, companyWayNumber, companyRepeatIndex, companyWayType, companyWayLabel, companyDistributionMention, companyCedexOffice, companyPostalCode, companyTownCode, companyCountryCode, companyPhone1, companyPhone2, companyFax, companyEmail, companyCountyCode, companyOldIdentifier, companyRegistrationAuthority);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Activity {\n");

        sb.append("    professionalModeCode: ").append(toIndentedString(professionalModeCode)).append("\n");
        sb.append("    activitySectorCode: ").append(toIndentedString(activitySectorCode)).append("\n");
        sb.append("    pharmacistTableSectionCode: ").append(toIndentedString(pharmacistTableSectionCode)).append("\n");
        sb.append("    roleCode: ").append(toIndentedString(roleCode)).append("\n");
        sb.append("    siretSiteNumber: ").append(toIndentedString(siretSiteNumber)).append("\n");
        sb.append("    sirenSiteNumber: ").append(toIndentedString(sirenSiteNumber)).append("\n");
        sb.append("    finessSiteNumber: ").append(toIndentedString(finessSiteNumber)).append("\n");
        sb.append("    finessLegalCompanyNumber: ").append(toIndentedString(finessLegalCompanyNumber)).append("\n");
        sb.append("    companyTechnicalIdentifier: ").append(toIndentedString(companyTechnicalIdentifier)).append("\n");
        sb.append("    companyName: ").append(toIndentedString(companyName)).append("\n");
        sb.append("    companyCommercialSign: ").append(toIndentedString(companyCommercialSign)).append("\n");
        sb.append("    companyAdditionalAddress: ").append(toIndentedString(companyAdditionalAddress)).append("\n");
        sb.append("    companyGeographicalPointComplement: ").append(toIndentedString(companyGeographicalPointComplement)).append("\n");
        sb.append("    companyWayNumber: ").append(toIndentedString(companyWayNumber)).append("\n");
        sb.append("    companyRepeatIndex: ").append(toIndentedString(companyRepeatIndex)).append("\n");
        sb.append("    companyWayType: ").append(toIndentedString(companyWayType)).append("\n");
        sb.append("    companyWayLabel: ").append(toIndentedString(companyWayLabel)).append("\n");
        sb.append("    companyDistributionMention: ").append(toIndentedString(companyDistributionMention)).append("\n");
        sb.append("    companyCedexOffice: ").append(toIndentedString(companyCedexOffice)).append("\n");
        sb.append("    companyPostalCode: ").append(toIndentedString(companyPostalCode)).append("\n");
        sb.append("    companyTownCode: ").append(toIndentedString(companyTownCode)).append("\n");
        sb.append("    companyCountryCode: ").append(toIndentedString(companyCountryCode)).append("\n");
        sb.append("    companyPhone1: ").append(toIndentedString(companyPhone1)).append("\n");
        sb.append("    companyPhone2: ").append(toIndentedString(companyPhone2)).append("\n");
        sb.append("    companyFax: ").append(toIndentedString(companyFax)).append("\n");
        sb.append("    companyEmail: ").append(toIndentedString(companyEmail)).append("\n");
        sb.append("    companyCountyCode: ").append(toIndentedString(companyCountyCode)).append("\n");
        sb.append("    companyOldIdentifier: ").append(toIndentedString(companyOldIdentifier)).append("\n");
        sb.append("    companyRegistrationAuthority: ").append(toIndentedString(companyRegistrationAuthority)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
