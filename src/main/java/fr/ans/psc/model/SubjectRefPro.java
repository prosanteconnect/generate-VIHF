package fr.ans.psc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

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
