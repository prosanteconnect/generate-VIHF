package fr.ans.psc.model;

import java.util.List;

public class SubjectRefPro {

    private String civilCode;
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
