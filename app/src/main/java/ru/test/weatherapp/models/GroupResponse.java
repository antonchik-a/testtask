package ru.test.weatherapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.test.weatherapp.models.base.BaseModel;

/**
 * Created by Alexey Antonchik on 14.08.16.
 */
public class GroupResponse extends BaseModel {

    @SerializedName("list")
    List<WeatherResponse> weatherResponseList;


    public List<WeatherResponse> getWeatherResponseList() {
        return weatherResponseList;
    }

    public void setWeatherResponseList(List<WeatherResponse> weatherResponseList) {
        this.weatherResponseList = weatherResponseList;
    }
}
