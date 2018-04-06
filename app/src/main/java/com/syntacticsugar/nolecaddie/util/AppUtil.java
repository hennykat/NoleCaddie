package com.syntacticsugar.nolecaddie.util;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;

public class AppUtil {

    public static final String TAG = AppUtil.class.getSimpleName();

    // TODO: rewrite this & add unit tests
    // http://stackoverflow.com/questions/14394366/find-distance-between-two-points-on-map-using-google-map-api-v2
    public static double calculateDistance(LatLng start, LatLng end) {

        int earthRadiusKm = 6371;

        double startLat = start.latitude;
        double startLng = start.longitude;
        double endLat = end.latitude;
        double endLng = end.longitude;

        double deltaLat = Math.toRadians(endLat - startLat);
        double deltaLng = Math.toRadians(endLng - startLng);

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(Math.toRadians(startLat))
                * Math.cos(Math.toRadians(endLat)) * Math.sin(deltaLng / 2)
                * Math.sin(deltaLng / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = earthRadiusKm * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("#");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i(TAG, "radius value: " + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Math.floor(((earthRadiusKm * c) * 3280.84) * 100) / 100;
    }
}
