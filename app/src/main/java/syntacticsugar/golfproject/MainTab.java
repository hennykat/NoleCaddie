package syntacticsugar.golfproject;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DecimalFormat;

/**
 * Created by Dalton on 7/6/2015.
 * Edited by Sam on 7/10/2015, Blake
 */
public class MainTab extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

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

    public static final String TAG = MainTab.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    LatLng currentLocation;
    GoogleMap googleMap;
    public static int currentStroke = 1;
    public static int currentHole = 1;
    public static String currentPar;
    SupportMapFragment mapFragment;
    LocationManager locationManager;
    Location mCurrentLocation;
    Location hCurrentLocation;
    Criteria criteria;
    String bestProvider;
    LatLng currentHoleLatLng;
    double myCurrentLat;
    double myCurrentLng;
    double distanceToHole;
    TextView strokeText;

/*  // map overlay stuff
    LatLng mapSWCorner = new LatLng(30.190333,-85.724764);
    LatLng mapNECorner = new LatLng(30.190003,-85.724264);
    LatLng golfMapCenter = new LatLng(30.1901,-85.72383);
    BitmapDescriptor mapOveryLayImage;
    LatLngBounds mapBounds; */

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        TextView distanceText = (TextView) findViewById(R.id.distanceText);
        TextView holeText = (TextView) findViewById(R.id.holeNumber);
        strokeText = (TextView) findViewById(R.id.strokecount);
        holeText.setText(String.valueOf(currentHole));
        strokeText.setText(String.valueOf(currentStroke));

         mapFragment = (SupportMapFragment) getSupportFragmentManager()
                 .findFragmentById(R.id.map);

        if (!isGooglePlayServicesAvailable()) {
            finish();
        }
        googleMap = mapFragment.getMap();
        googleMap.setMyLocationEnabled(true);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        criteria = new Criteria();
        bestProvider = locationManager.getBestProvider(criteria, true);
        mCurrentLocation = locationManager.getLastKnownLocation(bestProvider);
        if (mCurrentLocation != null) {
            onLocationChanged(mCurrentLocation);
        }
        //locationManager.requestLocationUpdates(20000,0,null,null,null);
        currentHoleLatLng = holeLocations[currentHole- 1];

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

        distanceText.setText(String.valueOf(distanceToHole));

    }//end onCreate

    @Override
    protected void onResume() {
        super.onResume();

        currentPar = checkPar(currentHole);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void markStroke(View view) {

        //display in short period of time
        Toast.makeText(getApplicationContext(), "Throw Marked.",
                Toast.LENGTH_SHORT).show();
        ++currentStroke;
        strokeText.setText(String.valueOf(currentStroke));

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
        LatLng Hole = holeLocations[currentHole- 1];
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Location services connected.");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
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

    @Override
    public void onLocationChanged(Location location) {
        //handleNewLocation(location);

        //TextView locationTv = (TextView) findViewById(R.id.latlongLocation);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(latLng));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        //locationTv.setText("Latitude:" + latitude + ", Longitude:" + longitude);
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

}//end class
