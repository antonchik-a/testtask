package ru.test.weatherapp.models;

import com.google.gson.annotations.SerializedName;

import ru.test.weatherapp.models.base.BaseModel;

import static ru.test.weatherapp.Config.DEGREE;

/**
 * Created by Alexey Antonchik on 03.08.16.
 */
public class Weather extends BaseModel {

    @SerializedName("temp")
    double temp;

    @SerializedName("pressure")
    double pressure;

    @SerializedName("humidity")
    int humidity;

    @SerializedName("temp_min")
    double temp_min;

    @SerializedName("temp_max")
    double temp_max;


    public double getTemp() {
        return temp;
    }

    public double getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }


    public String getStringTemp(){
       return String.valueOf(Math.round(getTemp())) + DEGREE;
    }

    public String getStringPressure(){
        return String.valueOf(Math.round(getPressure())) ;
    }

    public String getStringHumidity(){
        return String.valueOf(getHumidity()) ;
    }
}
