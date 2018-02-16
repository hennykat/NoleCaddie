package com.syntacticsugar.nolecaddie.model;

import java.util.List;

/**
 * Created by henny on 2/16/18.
 */
public class Weather {

    public List<WeatherCondition> weather;

    public List<WeatherCondition> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherCondition> weather) {
        this.weather = weather;
    }
}
