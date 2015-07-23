package syntacticsugar.golfproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Dalton on 7/6/2015.
 * Edited by Sam on 7/10/2015
 */
public class MainTab extends FragmentActivity implements OnMapReadyCallback {

    int[] parArray = {2,3,3,3,3,3,3,2,4,3,3,2,3,3,2,2,2,2};

    protected static final LatLng[] holeLocations = new LatLng[]{
        new LatLng(30.190003,-85.724264), //hole 1
        new LatLng(30.189567,-85.724789), //hole 2
        new LatLng(30.189336,-85.724953), //hole 3
        new LatLng(30.188992,-85.725439), //hole 4
        new LatLng(30.189200,-85.725322), //hole 5
        new LatLng(30.189086,-85.725586), //hole 6
        new LatLng(30.189483,-85.725283), //hole 7
        new LatLng(30.189558,-85.724944), //hole 8
        new LatLng(30.190283,-85.724247), //hole 9
        new LatLng(30.190758,-85.723369), //hole 10
        new LatLng(30.190142,-85.722667), //hole 11
        new LatLng(30.190375,-85.721986), //hole 12
        new LatLng(30.191125,-85.721831), //hole 13
        new LatLng(30.191375,-85.722125), //hole 14
        new LatLng(30.191053,-85.722194), //hole 15
        new LatLng(30.191025,-85.722619), //hole 16
        new LatLng(30.190789,-85.723086), //hole 17
        new LatLng(30.190328,-85.723506)  //hole 18
    };



    public static int currentStroke = 1;
    public static int currentHole = 1;
    public static String currentPar;

    SupportMapFragment mapFragment;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

         mapFragment = (SupportMapFragment) getSupportFragmentManager()
                 .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }//end onCreate

    @Override
    protected void onResume() {
        super.onResume();

        currentPar = checkPar(currentHole);
    }

    public void markStroke(View view) {

        //display in short period of time
        Toast.makeText(getApplicationContext(), "Throw Marked.",
                Toast.LENGTH_SHORT).show();
        ++currentStroke;
    }

    public String checkPar(int hole) {
        String par = Integer.toString(parArray[(hole-1)]);
        return par;
    }

    public void gotoMenu(View view) {
        Intent intent = new Intent(this,MenuScreen.class);
        startActivity(intent);
    }

    public void finishHole(View view) {

        Intent intent = new Intent(this,ScoreTab.class);
        startActivity(intent);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        // original code snippets from google maps api reference
        //https://developers.google.com/maps/documentation/android/views#target_location
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        Log.v("MapReady Hole is: ", " " + currentHole);
        LatLng Hole = holeLocations[currentHole-1];

        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Hole, 20));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(Hole)      // Sets the center of the map to Mountain View
                .zoom(19)                   // Sets the zoom
                .bearing(180)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        googleMap.addMarker(new MarkerOptions()
                .position(Hole)
                .title("Hole "+currentHole));
        googleMap.setMyLocationEnabled(true);
    }
}//end class
