package com.syntacticsugar.nolecaddie.ui;

public class ScorecardItem {

    private String hole;
    private String par;
    private String score;

    public ScorecardItem(String hole, String par, String score) {
        this.hole = hole;
        this.par = par;
        this.score = score;
    }

    public String getHole() {
        return hole;
    }

    public void setHole(String hole) {
        this.hole = hole;
    }

    public String getPar() {
        return par;
    }

    public void setPar(String par) {
        this.par = par;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
