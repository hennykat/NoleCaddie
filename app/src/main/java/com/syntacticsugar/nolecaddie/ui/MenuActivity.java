package com.syntacticsugar.nolecaddie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.syntacticsugar.nolecaddie.R;
import com.syntacticsugar.nolecaddie.config.AppConfig;
import com.syntacticsugar.nolecaddie.model.Weather;
import com.syntacticsugar.nolecaddie.model.WeatherCondition;

import java.util.List;

/**
 * Created by Dalton on 7/6/2015.
 * Edited by Sam on 7/16/2015.
 * Edited by Blake 7/21/2015.
 * Updated by henny 2018
 */
public class MenuActivity extends AppCompatActivity {

    public static final String TAG = MenuActivity.class.getSimpleName();

    private RequestQueue volleyQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // setup request queue
        this.volleyQueue = Volley.newRequestQueue(this);

        final Button startButton = findViewById(R.id.menu_start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        getWeather();
    }

    @Override
    public void onPause() {
        super.onPause();

        this.volleyQueue.cancelAll(TAG);
    }

    private void startGame() {
        Intent mainIntent = new Intent(MenuActivity.this, MainActivity.class);
        startActivity(mainIntent);
    }

    private void getWeather() {

        if (volleyQueue == null) {
            Log.w(TAG, "failed to get weather, invalid request queue");
            return;
        }

        final String appId = getResources().getString(R.string.weather_id);
        final String url = AppConfig.WEATHER_URL + appId;

        // request json
        StringRequest weatherRequest = new StringRequest
                (Request.Method.GET, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response: " + response);

                        Integer weatherId = getWeatherId(response);
                        updateBackground(weatherId);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "failed to obtain weather: " + error.toString());
                    }
                });
        // add to queue
        weatherRequest.setTag(TAG);
        volleyQueue.add(weatherRequest);
    }

    private Integer getWeatherId(String jsonString) {

        if (jsonString == null) {
            return 0;
        }

        Gson gson = new Gson();
        Weather weather = gson.fromJson(jsonString, Weather.class);

        if (weather == null) {
            return 0;
        }

        List<WeatherCondition> conditionList = weather.getWeather();
        if (conditionList == null || conditionList.isEmpty()) {
            return 0;
        }

        return conditionList.get(0).getId();
    }

    private void updateBackground(Integer weatherId) {

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
