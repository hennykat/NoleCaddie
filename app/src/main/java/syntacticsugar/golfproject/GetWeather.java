package syntacticsugar.golfproject;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Blake on 7/17/2015.
 */
public class GetWeather extends AsyncTask<String, Void, Void> {
    private final String LOG_TAG = GetWeather.class.getSimpleName();


    @Override
    protected Void doInBackground(String... params) {
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
                String skyCondition = (String) jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
                Double windSpeed = jsonObject.getJSONObject("wind").getDouble("speed");
                Integer windDirection = jsonObject.getJSONObject("wind").getInt("deg");
                Log.v("JSONTry", skyCondition);
                Log.v("JSONTry", windSpeed.toString());
                Log.v("JSONTry", windDirection.toString());
                Log.v(LOG_TAG, "Forecast is" + weatherData);
            }catch (JSONException e){
                Log.e("JSON error","couldnt resolve data",e);
            }


        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
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
        return null;

    }

    }

