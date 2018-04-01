package com.syntacticsugar.nolecaddie.ui;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.syntacticsugar.nolecaddie.R;
import com.syntacticsugar.nolecaddie.config.AppConfig;

import java.text.DecimalFormat;

/**
 * Created by Dalton on 7/6/2015.
 * Edited by Sam on 7/10/2015, Blake
 * Updated by henny 2018
 */
public class MainActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private LatLng currentLocation;
    private GoogleMap googleMap;
    public static int currentStroke = 1;
    public static int currentHole = 1;
    public static String currentPar;
    private SupportMapFragment mapFragment;
    private LocationManager locationManager;
    private Location mCurrentLocation;
    private Location hCurrentLocation;
    private Criteria criteria;
    private String bestProvider;
    private LatLng currentHoleLatLng;
    double myCurrentLat;
    double myCurrentLng;
    double distanceToHole;
    private TextView strokeTextView, distanceTextView, holeTextView;

/*  // map overlay stuff
    LatLng mapSWCorner = new LatLng(30.190333,-85.724764);
    LatLng mapNECorner = new LatLng(30.190003,-85.724264);
    LatLng golfMapCenter = new LatLng(30.1901,-85.72383);
    BitmapDescriptor mapOveryLayImage;
    LatLngBounds mapBounds; */

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        distanceTextView = findViewById(R.id.main_distance_textview);
        holeTextView = findViewById(R.id.main_hole_textview);
        strokeTextView = findViewById(R.id.main_stroke_textview);

        holeTextView.setText(String.valueOf(currentHole));
        strokeTextView.setText(String.valueOf(currentStroke));

        final Button throwButton = findViewById(R.id.main_throw_button);
        throwButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markStroke();
            }
        });
        final Button finishButton = findViewById(R.id.main_finish_button);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishHole();
            }
        });

//        mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);

        if (!isGooglePlayServicesAvailable()) {
            finish();
        }
//        googleMap = mapFragment.getMap();
//        googleMap.setMyLocationEnabled(true);
//        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        criteria = new Criteria();
//        bestProvider = locationManager.getBestProvider(criteria, true);
//
//        mCurrentLocation = locationManager.getLastKnownLocation(bestProvider);


        if (mCurrentLocation != null) {
            myLocationListener.onLocationChanged(mCurrentLocation);
        } else {
            Toast.makeText(getApplicationContext(), "No Location Available. Probably turned off.",
                    Toast.LENGTH_SHORT).show();
            //Intent intent =  new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            //startActivity(intent);
        }

        //locationManager.requestLocationUpdates(bestProvider,20000,1,myLocationListener);

    }//end onCreate

    private final LocationListener myLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            updateCurrentLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void updateCurrentLocation(Location location) {

        if (location != null) {
            currentHoleLatLng = AppConfig.HOLE_LOCATIONS[currentHole - 1];
            myCurrentLat = mCurrentLocation.getLatitude();
            myCurrentLng = mCurrentLocation.getLongitude();
            currentLocation = new LatLng(myCurrentLat, myCurrentLng);


    /*   // **** works; no time to make a good graphic so taking out for now
        // Uncomment to see working with FSU graphic
        mapOveryLayImage = BitmapDescriptorFactory.fromResource(R.drawable.gmap); // get an image.
        mapBounds = new LatLngBounds(mapNECorner,mapSWCorner); // get a bounds
        // Adds a ground overlay with 50% transparency.
        GroundOverlay groundOverlay = googleMap.addGroundOverlay(new GroundOverlayOptions()
                .image(mapOveryLayImage)
                .position(golfMapCenter, 396.0f, 324.0f)
                .transparency(0.8f)); */


            hCurrentLocation = new Location("");
            hCurrentLocation.setLatitude(currentHoleLatLng.latitude);
            hCurrentLocation.setLongitude(currentHoleLatLng.longitude);

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(currentHoleLatLng)      // Sets the center of the map to Mountain View
                    .zoom(18)                   // Sets the zoom
                    .bearing(mCurrentLocation.bearingTo(hCurrentLocation))                // Sets the orientation of the camera to east
                    .tilt(65.0f)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder

            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            googleMap.addMarker(new MarkerOptions()
                    .position(currentHoleLatLng)
                    .title("Hole " + currentHole)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.basketmarker)));

            googleMap
                    .addPolyline((new PolylineOptions())
                            .add(currentLocation, currentHoleLatLng).width(5).color(0xFF7A2339)
                            .geodesic(true));

            distanceToHole = CalculationByDistance(currentLocation, currentHoleLatLng);

            distanceTextView.setText(String.valueOf(distanceToHole));
        } else {
            Toast.makeText(getBaseContext(), "No Location Found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        currentPar = checkPar(currentHole);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void markStroke() {

        //display in short period of time
        Toast.makeText(getApplicationContext(), "Throw Marked.",
                Toast.LENGTH_SHORT).show();
        ++currentStroke;
        strokeTextView.setText(String.valueOf(currentStroke));
//        locationManager.requestSingleUpdate(criteria,myLocationListener,null);

    }

    public String checkPar(int hole) {
        return Integer.toString(AppConfig.HOLE_PARS[(hole - 1)]);
    }

    public void finishHole() {
        Intent intent = new Intent(this, EditScore.class);
        startActivity(intent);
    }

    //***** taken from http://stackoverflow.com/questions/14394366/find-distance-between-two-points-on-map-using-google-map-api-v2 and modified
    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("#");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Math.floor(((Radius * c) * 3280.84) * 100) / 100;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // original code snippets from google maps api reference
        //https://developers.google.com/maps/documentation/android/views#target_location
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        Log.v("MapReady Hole is: ", " " + currentHole);
        LatLng Hole = AppConfig.HOLE_LOCATIONS[currentHole - 1];
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Location services connected.");
        //locationManager.requestLocationUpdates(bestProvider,20000,1,null);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

}
