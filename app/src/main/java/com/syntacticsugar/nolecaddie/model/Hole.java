package com.syntacticsugar.nolecaddie.model;

import com.google.android.gms.maps.model.LatLng;

public class Hole {

    private int par;
    private LatLng latLng;
    // user
    private Integer score;

    public Hole(int par, LatLng latLng, Integer score) {
        this.par = par;
        this.latLng = latLng;
        this.score = score;
    }

    public Hole(int par, LatLng latLng) {
        this(par, latLng, null);
    }

    public int getPar() {
        return par;
    }

    public void setPar(int par) {
        this.par = par;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
