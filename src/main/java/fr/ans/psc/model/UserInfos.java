package fr.ans.psc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserInfos {

    @JsonProperty("Secteur_Activite")
    private String activitySector;

    @JsonProperty("sub")
    private String sub;

    @JsonProperty("email_verified")
    private boolean emailVerified;

    @JsonProperty("SubjectOrganization")
    private String subjectOrganization;

    @JsonProperty("Mode_Acces_Raison")
    private String modeAccesRaison;

    @JsonProperty("preferred_username")
    private String preferredUsername;

    @JsonProperty("given_name")
    private String givenName;

    @JsonProperty("Acces_Regulation_Medicale")
    private String accesregulationMedicale;

    @JsonProperty("UITVersion")
    private String uitVersion;

    @JsonProperty("Palier_Authentification")
    private String palierAuthentification;

    @JsonProperty("SubjectRefPro")
    private SubjectRefPro subjectRefPro;

    @JsonProperty("SubjectOrganizationID")
    private String subjectOrganizationID;

    @JsonProperty("SubjectRole")
    private List<String> subjectRoles;

    @JsonProperty("PSI_Locale")
    private String psiLocale;

    @JsonProperty("otherIds")
    private List<AlternativeIdentifier> otherIds;

    @JsonProperty("SubjectNameID")
    private String subjectNameID;

    @JsonProperty("family_name")
    private String familyName;

    public String getActivitySector() {
        return activitySector;
    }

    public void setActivitySector(String activitySector) {
        this.activitySector = activitySector;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getSubjectOrganization() {
        return subjectOrganization;
    }

    public void setSubjectOrganization(String subjectOrganization) {
        this.subjectOrganization = subjectOrganization;
    }

    public String getModeAccesRaison() {
        return modeAccesRaison;
    }

    public void setModeAccesRaison(String modeAccesRaison) {
        this.modeAccesRaison = modeAccesRaison;
    }

    public String getPreferredUsername() {
        return preferredUsername;
    }

    public void setPreferredUsername(String preferredUsername) {
        this.preferredUsername = preferredUsername;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getAccesregulationMedicale() {
        return accesregulationMedicale;
    }

    public void setAccesregulationMedicale(String accesregulationMedicale) {
        this.accesregulationMedicale = accesregulationMedicale;
    }

    public String getUitVersion() {
        return uitVersion;
    }

    public void setUitVersion(String uitVersion) {
        this.uitVersion = uitVersion;
    }

    public String getPalierAuthentification() {
        return palierAuthentification;
    }

    public void setPalierAuthentification(String palierAuthentification) {
        this.palierAuthentification = palierAuthentification;
    }

    public SubjectRefPro getSubjectRefPro() {
        return subjectRefPro;
    }

    public void setSubjectRefPro(SubjectRefPro subjectRefPro) {
        this.subjectRefPro = subjectRefPro;
    }

    public String getSubjectOrganizationID() {
        return subjectOrganizationID;
    }

    public void setSubjectOrganizationID(String subjectOrganizationID) {
        this.subjectOrganizationID = subjectOrganizationID;
    }

    public List<String> getSubjectRoles() {
        return subjectRoles;
    }

    public void setSubjectRoles(List<String> subjectRoles) {
        this.subjectRoles = subjectRoles;
    }

    public String getPsiLocale() {
        return psiLocale;
    }

    public void setPsiLocale(String psiLocale) {
        this.psiLocale = psiLocale;
    }

    public List<AlternativeIdentifier> getOtherIds() {
        return otherIds;
    }

    public void setOtherIds(List<AlternativeIdentifier> otherIds) {
        this.otherIds = otherIds;
    }

    public String getSubjectNameID() {
        return subjectNameID;
    }

    public void setSubjectNameID(String subjectNameID) {
        this.subjectNameID = subjectNameID;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getSubjectId() {
        return familyName + " " + givenName + " " + subjectOrganization;
    }
}
