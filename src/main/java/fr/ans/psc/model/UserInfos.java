package fr.ans.psc.model;

import java.util.List;

public class UserInfos {

    private String activitySector;
    private String sub;
    private boolean emailVerified;
    private String subjectOrganization;
    private String modeAccesRaison;
    private String preferredUsername;
    private String givenName;
    private String accesregulationMedicale;
    private String uitVersion;
    private String palierAuthentification;
    private SubjectRefPro subjectRefPro;
    private String subjectOrganizationID;
    private List<String> subjectRoles;
    private String psiLocale;
    private List<AlternativeIdentifier> otherIds;
    private String subjectNameID;
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
}
