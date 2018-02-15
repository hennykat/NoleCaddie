package com.syntacticsugar.nolecaddie.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.syntacticsugar.nolecaddie.R;
import com.syntacticsugar.nolecaddie.model.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dalton on 7/6/2015.
 * Edited by Sam on 7/16/2015.
 * Edited by Blake 7/21/2015.
 * Updated by henny 2018
 */
public class MenuActivity extends Activity {

    public static final String TAG = MenuActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getWeather();
    }

    public void startGame(View view) {
        Intent mainIntent = new Intent(MenuActivity.this, MainTab.class);
        startActivity(mainIntent);
        finish();
    }

    private void getWeather() {

        // setup queue
        RequestQueue queue = Volley.newRequestQueue(this);
        final String appId = getResources().getString(R.string.weather_id);
        final String url = AppConfig.WEATHER_URL + appId;

        // request json
        JsonObjectRequest weatherRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Response: " + response.toString());

                        int weatherId = getWeatherId(response);
                        updateBackground(weatherId);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "failed to obtain weather: " + error.toString());
                    }
                });
        // add to queue
        queue.add(weatherRequest);
    }

    // TODO: use GSON for this?
    private int getWeatherId(JSONObject jsonObject) {

        if (jsonObject == null) {
            return 0;
        }

        JSONArray weather = null;
        try {
            weather = jsonObject.getJSONArray("weather");
        } catch (JSONException e) {
            Log.e(TAG, "failed to get weather arr", e);
        }

        if (weather == null || weather.length() < 1) {
            return 0;
        }

        JSONObject weatherObj = null;
        try {
            weatherObj = (JSONObject) weather.get(0);
        } catch (JSONException e) {
            Log.e(TAG, "failed to get weather obj", e);
        }

        if (weatherObj == null) {
            return 0;
        }

        int weatherId = 0;
        try {
            weatherId = weatherObj.getInt("id");
        } catch (JSONException e) {
            Log.e(TAG, "failed to get weather id", e);
        }

        return weatherId;
    }

    private void updateBackground(int weatherId) {

        final RelativeLayout menuLayout = findViewById(R.id.menu_layout);

        if (weatherId >= 200 & weatherId <= 531) {
            // groups 2xx, 3xx and 5xx all rain
            menuLayout.setBackgroundResource(R.drawable.screenrain);
        } else if (weatherId == 800) {
            // sunny code
            menuLayout.setBackgroundResource(R.drawable.screensunny);
        } else {
            // default to cloudy
            menuLayout.setBackgroundResource(R.drawable.screenpartly);
        }
    }

}
