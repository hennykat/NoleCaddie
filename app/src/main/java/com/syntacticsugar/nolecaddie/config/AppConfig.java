package com.syntacticsugar.nolecaddie.config;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by sam on 7/17/15.
 * Updated by henny 2018
 */
public class AppConfig {

    public static final String FIRST_COLUMN = "First";
    public static final String SECOND_COLUMN = "Second";
    public static final String THIRD_COLUMN = "Third";

    public static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?zip=32405,us&APPID=";

    public static int[] HOLE_PARS = {2, 3, 3, 3, 3, 3, 3, 2, 4, 3, 3, 2, 3, 3, 2, 2, 2, 2};

    public static final LatLng[] HOLE_LOCATIONS = new LatLng[]{
            new LatLng(30.190003, -85.724264), //hole 1
            new LatLng(30.189567, -85.724789), //hole 2
            new LatLng(30.189336, -85.724953), //hole 3
            new LatLng(30.188992, -85.725439), //hole 4
            new LatLng(30.189200, -85.725322), //hole 5
            new LatLng(30.189086, -85.725586), //hole 6
            new LatLng(30.189483, -85.725283), //hole 7
            new LatLng(30.189558, -85.724944), //hole 8
            new LatLng(30.190283, -85.724247), //hole 9
            new LatLng(30.190758, -85.723369), //hole 10
            new LatLng(30.190142, -85.722667), //hole 11
            new LatLng(30.190375, -85.721986), //hole 12
            new LatLng(30.191125, -85.721831), //hole 13
            new LatLng(30.191375, -85.722125), //hole 14
            new LatLng(30.191053, -85.722194), //hole 15
            new LatLng(30.191025, -85.722619), //hole 16
            new LatLng(30.190789, -85.723086), //hole 17
            new LatLng(30.190328, -85.723506)  //hole 18
    };
}