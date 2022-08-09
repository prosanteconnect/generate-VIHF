package fr.ans.psc.model.prosanteconnect;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SubjectRefPro {

    @JsonProperty("codeCivilite")
    private String civilCode;

    @JsonProperty("exercices")
    private List<Practice> exercices;

    public String getCivilCode() {
        return civilCode;
    }

    public void setCivilCode(String civilCode) {
        this.civilCode = civilCode;
    }

    public List<Practice> getExercices() {
        return exercices;
    }

    public void setExercices(List<Practice> exercices) {
        this.exercices = exercices;
    }
}
