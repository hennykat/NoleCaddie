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
 * Edited by Blake 7/21/2015.
 * Updated by Henny 2018
 */
public class MenuScreen extends Activity {

    RelativeLayout mRelativeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        mRelativeLayout = findViewById(R.id.mRelativeLayout);

        GetWeather myGetWeather = new GetWeather();
        myGetWeather.execute();

    }//end onCreate


    public void startGame(View view) {
        Intent mainIntent = new Intent(MenuScreen.this, MainTab.class);
        startActivity(mainIntent);
        finish();

    }

    public class GetWeather extends AsyncTask<Void, Void, Integer> {

        private final String TAG = GetWeather.class.getSimpleName();
        private int skyCondition;

        @Override
        protected Integer doInBackground(Void... params) {
            //API call setup
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String weatherData = null;

            try {
                //url conforms to OpenWeather api call for zipcode 32405, zip for FSU-PC
                // TODO: abstract app id
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?zip=32405,us&APPID=5a448d218d027a8293c6f74cc606c4eb");

                //Open request to OpenWeather
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                //Receive JSON data
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder builder = new StringBuilder();
                if (inputStream == null) {
                    //Didn't receive anything
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                    builder.append("\n");
                }
                if (builder.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                weatherData = builder.toString();

                try {
                    JSONObject jsonObject = new JSONObject(weatherData);
                    skyCondition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
                    Log.v("JSONTry", "Sky Condition: " + skyCondition);
                } catch (JSONException e) {
                    Log.e("JSON error", "couldnt resolve data", e);
                }

            } catch (IOException e) {
                Log.e(TAG, "Error ", e);

            } finally {

                //close connection
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(TAG, "Error closing stream", e);
                    }
                }
            }
            return skyCondition;
        }

        // set weather type screen on main menu
        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            if (integer != null) {
                switch (integer) {
                    case 800:  // sunny
                        mRelativeLayout.setBackgroundResource(R.drawable.screensunny);
                        break;
                    case 531:  // rainy
                        mRelativeLayout.setBackgroundResource(R.drawable.screenrain);
                        break;
                    default:  // partly cloudy
                        mRelativeLayout.setBackgroundResource(R.drawable.screenpartly);
                        break;
                }
            }
        }
    }

}
