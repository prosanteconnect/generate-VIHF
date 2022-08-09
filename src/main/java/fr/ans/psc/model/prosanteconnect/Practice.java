package fr.ans.psc.model.prosanteconnect;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Practice {
    @JsonProperty("codeProfession")
    private String professionCode = null;

    @JsonProperty("codeCategorieProfessionnelle")
    private String professionalCategoryCode = null;

    @JsonProperty("codeTypeSavoirFaire")
    private String expertiseTypeCode = null;

    @JsonProperty("codeSavoirFaire")
    private String expertiseCode = null;

    @JsonProperty("codeCiviliteDexercice")
    private String professionalCivilityTitle = null;

    @JsonProperty("nomDexercice")
    private String professionalLastName = null;

    @JsonProperty("prenomDexercice")
    private String professionalFirstName = null;

    @JsonProperty("activities")
    private List<Activity> activities = null;

    public Practice professionCode(String professionCode) {
        this.professionCode = professionCode;
        return this;
    }

    /**
     * Get professionCode
     *
     * @return professionCode
     **/
    public String getProfessionCode() {
        return professionCode;
    }

    public void setProfessionCode(String professionCode) {
        this.professionCode = professionCode;
    }

    public Practice professionalCategoryCode(String professionalCategoryCode) {
        this.professionalCategoryCode = professionalCategoryCode;
        return this;
    }

    /**
     * Get professionalCategoryCode
     *
     * @return professionalCategoryCode
     **/
    public String getProfessionalCategoryCode() {
        return professionalCategoryCode;
    }

    public void setProfessionalCategoryCode(String professionalCategoryCode) {
        this.professionalCategoryCode = professionalCategoryCode;
    }

    public Practice expertiseTypeCode(String expertiseTypeCode) {
        this.expertiseTypeCode = expertiseTypeCode;
        return this;
    }

    /**
     * Get expertiseTypeCode
     *
     * @return expertiseTypeCode
     **/
    public String getExpertiseTypeCode() {
        return expertiseTypeCode;
    }

    public void setExpertiseTypeCode(String expertiseTypeCode) {
        this.expertiseTypeCode = expertiseTypeCode;
    }

    public Practice expertiseCode(String expertiseCode) {
        this.expertiseCode = expertiseCode;
        return this;
    }

    /**
     * Get expertiseCode
     *
     * @return expertiseCode
     **/
    public String getExpertiseCode() {
        return expertiseCode;
    }

    public void setExpertiseCode(String expertiseCode) {
        this.expertiseCode = expertiseCode;
    }

    public Practice professionalCivilityTitle(String professionalCivilityTitle) {
        this.professionalCivilityTitle = professionalCivilityTitle;
        return this;
    }

    /**
     * Get professionalCivilityTitle
     *
     * @return professionalCivilityTitle
     **/
    public String getProfessionalCivilityTitle() {
        return professionalCivilityTitle;
    }

    public void setProfessionalCivilityTitle(String professionalCivilityTitle) {
        this.professionalCivilityTitle = professionalCivilityTitle;
    }

    public Practice professionalLastName(String professionalLastName) {
        this.professionalLastName = professionalLastName;
        return this;
    }

    /**
     * Get professionalLastName
     *
     * @return professionalLastName
     **/
    public String getProfessionalLastName() {
        return professionalLastName;
    }

    public void setProfessionalLastName(String professionalLastName) {
        this.professionalLastName = professionalLastName;
    }

    public Practice professionalFirstName(String professionalFirstName) {
        this.professionalFirstName = professionalFirstName;
        return this;
    }

    /**
     * Get professionalFirstName
     *
     * @return professionalFirstName
     **/
    public String getProfessionalFirstName() {
        return professionalFirstName;
    }

    public void setProfessionalFirstName(String professionalFirstName) {
        this.professionalFirstName = professionalFirstName;
    }

    public Practice activities(List<Activity> activities) {
        this.activities = activities;
        return this;
    }

    public Practice addActivitiesItem(Activity activitiesItem) {
        if (this.activities == null) {
            this.activities = new ArrayList<Activity>();
        }
        this.activities.add(activitiesItem);
        return this;
    }

    /**
     * Get activities
     *
     * @return activities
     **/
    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Practice practice = (Practice) o;
        return Objects.equals(this.professionCode, practice.professionCode) &&
                Objects.equals(this.professionalCategoryCode, practice.professionalCategoryCode) &&
                Objects.equals(this.expertiseTypeCode, practice.expertiseTypeCode) &&
                Objects.equals(this.expertiseCode, practice.expertiseCode) &&
                Objects.equals(this.professionalCivilityTitle, practice.professionalCivilityTitle) &&
                Objects.equals(this.professionalLastName, practice.professionalLastName) &&
                Objects.equals(this.professionalFirstName, practice.professionalFirstName) &&
                Objects.equals(this.activities, practice.activities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(professionCode, professionalCategoryCode, expertiseTypeCode, expertiseCode, professionalCivilityTitle, professionalLastName, professionalFirstName, activities);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Practice {\n");

        sb.append("    professionCode: ").append(toIndentedString(professionCode)).append("\n");
        sb.append("    professionalCategoryCode: ").append(toIndentedString(professionalCategoryCode)).append("\n");
        sb.append("    expertiseTypeCode: ").append(toIndentedString(expertiseTypeCode)).append("\n");
        sb.append("    expertiseCode: ").append(toIndentedString(expertiseCode)).append("\n");
        sb.append("    professionalCivilityTitle: ").append(toIndentedString(professionalCivilityTitle)).append("\n");
        sb.append("    professionalLastName: ").append(toIndentedString(professionalLastName)).append("\n");
        sb.append("    professionalFirstName: ").append(toIndentedString(professionalFirstName)).append("\n");
        sb.append("    activities: ").append(toIndentedString(activities)).append("\n");
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
