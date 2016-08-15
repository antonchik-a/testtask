package ru.test.weatherapp.models;

import com.google.gson.annotations.SerializedName;

import ru.test.weatherapp.models.base.BaseModel;

/**
 * Created by Alexey Antonchik on 03.08.16.
 */
public class WeatherDesc extends BaseModel {

    @SerializedName("id")
    int id;

    @SerializedName("main")
    String main;

    @SerializedName("description")
    String description;

    @SerializedName("icon")
    String icon;

    public int getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
