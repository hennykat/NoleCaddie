package com.syntacticsugar.nolecaddie.model;

/**
 * Created by henny on 2/16/18.
 */
public class WeatherCondition {

    public Integer id;
    public String main;
    public String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
