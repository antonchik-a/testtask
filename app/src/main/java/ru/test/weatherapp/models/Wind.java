package ru.test.weatherapp.models;

import com.google.gson.annotations.SerializedName;

import ru.test.weatherapp.models.base.BaseModel;

/**
 * Created by Alexey Antonchik on 03.08.16.
 */
public class Wind extends BaseModel {

    @SerializedName("deg")
    double deg;

    @SerializedName("speed")
    double speed;

    public double getDeg() {
        return deg;
    }

    public double getSpeed() {
        return speed;
    }

    public String getStringSpeed(){
        return String.valueOf(Math.round(speed) + "  м/с");
    }
}
