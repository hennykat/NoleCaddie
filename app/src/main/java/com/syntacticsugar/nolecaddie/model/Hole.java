package com.syntacticsugar.nolecaddie.model;

import com.google.android.gms.maps.model.LatLng;

public class Hole {

    private int index;
    private int par;
    private LatLng latLng;
    // user
    private Integer score;

    public Hole(int index, int par, LatLng latLng, Integer score) {
        this.index = index;
        this.par = par;
        this.latLng = latLng;
        this.score = score;
    }

    public Hole(int index, int par, LatLng latLng) {
        this(index, par, latLng, null);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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
