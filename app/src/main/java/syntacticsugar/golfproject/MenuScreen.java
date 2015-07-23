package syntacticsugar.golfproject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Dalton on 7/6/2015.
 * Edited by Sam on 7/16/2015.
 * Edited by Blake 7/21/2015
 */
public class MenuScreen extends Activity {

    RelativeLayout mRelativeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        mRelativeLayout = (RelativeLayout) findViewById(R.id.mRelativeLayout);

        GetWeather myGetWeather = new GetWeather();
        myGetWeather.execute();

    }//end onCreate


    public void startGame(View view) {
       /*
        Intent intent = new Intent(this,MainTab.class);
        startActivity(intent);
        */

        Intent mainIntent = new Intent(MenuScreen.this, MainTab.class);
        MenuScreen.this.startActivity(mainIntent);
        MenuScreen.this.finish();

    }
    public class GetWeather extends AsyncTask<Void, Void, Integer> {
        private final String LOG_TAG = GetWeather.class.getSimpleName();
        private int skyCondition;

        @Override
        protected Integer doInBackground(Void... params) {
            //API call setup
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String weatherData = null;

            try{
                //url conforms to OpenWeather api call for zipcode 32405, zip for FSU-PC
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?zip=32405,us");

                //Open request to OpenWeather
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                //Receive JSON data
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null){
                    //Didn't receive anything
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But  makes debugging easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                weatherData = buffer.toString();

                try {
                    JSONObject jsonObject = new JSONObject(weatherData);
                    skyCondition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
                    //Double windSpeed = jsonObject.getJSONObject("wind").getDouble("speed");
                    // Integer windDirection = jsonObject.getJSONObject("wind").getInt("deg");
                    Log.v("JSONTry", "Sky Condition: " + skyCondition);
                    // Log.v("JSONTry", windSpeed.toString());
                    // Log.v("JSONTry", windDirection.toString());
                    // Log.v(LOG_TAG, "Forecast is" + weatherData);
                }catch (JSONException e){
                    Log.e("JSON error","couldnt resolve data",e);
                }


            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);

            }finally{

                //close connection
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            return skyCondition;

        }

        // set weather type screen on main menu
        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if (integer != null){

            Integer skyType = new Integer("2");

            if (integer == 800) skyType = 0; //sunny clear skys
            else if ( integer <= 531) skyType = 1; // rainy
                Log.v("SkyType", skyType + "");
            switch(skyType){
                case 0:  // sunny
                    mRelativeLayout.setBackgroundResource(R.drawable.screensunny);
                    break;
                case 1:  // rainy
                    mRelativeLayout.setBackgroundResource(R.drawable.screenrain);
                    break;
                case 2:default:  // partly cloudy
                    mRelativeLayout.setBackgroundResource(R.drawable.screenpartly);
                    break;
            }
            }
        }


    }


}//end class
