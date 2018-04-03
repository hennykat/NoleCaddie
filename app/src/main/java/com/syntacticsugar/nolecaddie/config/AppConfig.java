package com.syntacticsugar.nolecaddie.config;

import com.google.android.gms.maps.model.LatLng;
import com.syntacticsugar.nolecaddie.model.Hole;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sam on 7/17/15.
 * Updated by henny 2018
 */
public class AppConfig {

    public static final String FIRST_COLUMN = "First";
    public static final String SECOND_COLUMN = "Second";
    public static final String THIRD_COLUMN = "Third";

    public static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?zip=32405,us&APPID=";

    public static List<Hole> getHoleList() {
        final List<Hole> holeList = new ArrayList<>(18);
        holeList.add(new Hole(1, 2, new LatLng(30.190003, -85.724264))); //hole 1
        holeList.add(new Hole(2, 3, new LatLng(30.189567, -85.724789))); //hole 2
        holeList.add(new Hole(3, 3, new LatLng(30.189336, -85.724953))); //hole 3
        holeList.add(new Hole(4, 3, new LatLng(30.188992, -85.725439))); //hole 4
        holeList.add(new Hole(5, 3, new LatLng(30.189200, -85.725322))); //hole 5
        holeList.add(new Hole(6, 3, new LatLng(30.189086, -85.725586))); //hole 6
        holeList.add(new Hole(7, 3, new LatLng(30.189483, -85.725283))); //hole 7
        holeList.add(new Hole(8, 2, new LatLng(30.189558, -85.724944))); //hole 8
        holeList.add(new Hole(9, 4, new LatLng(30.190283, -85.724247))); //hole 9
        holeList.add(new Hole(10, 3, new LatLng(30.190758, -85.723369))); //hole 10
        holeList.add(new Hole(11, 3, new LatLng(30.190142, -85.722667))); //hole 11
        holeList.add(new Hole(12, 2, new LatLng(30.190375, -85.721986))); //hole 12
        holeList.add(new Hole(13, 3, new LatLng(30.191125, -85.721831))); //hole 13
        holeList.add(new Hole(14, 3, new LatLng(30.191375, -85.722125))); //hole 14
        holeList.add(new Hole(15, 2, new LatLng(30.191053, -85.722194))); //hole 15
        holeList.add(new Hole(16, 2, new LatLng(30.191025, -85.722619))); //hole 16
        holeList.add(new Hole(17, 2, new LatLng(30.190789, -85.723086))); //hole 17
        holeList.add(new Hole(18, 2, new LatLng(30.190328, -85.723506)));  //hole 18
        return holeList;
    }
}