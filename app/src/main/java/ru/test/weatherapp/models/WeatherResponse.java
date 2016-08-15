package ru.test.weatherapp.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.test.weatherapp.models.base.BaseModel;

import static ru.test.weatherapp.Config.WEATHER_PREFERENCES;

/**
 * Created by Alexey Antonchik on 03.08.16.
 */
public class WeatherResponse extends BaseModel {


    @SerializedName("weather")
    List<WeatherDesc> weatherList;

    @SerializedName("base")
    String base;

    @SerializedName("main")
    Weather mainWeather;

    @SerializedName("wind")
    Wind wind;

    @SerializedName("id")
    int id;

    @SerializedName("cod")
    int cod;

    @SerializedName("name")
    String name;

    public List<WeatherDesc> getWeatherList() {
        return weatherList;
    }

    public String getBase() {
        return base;
    }

    public Weather getMainWeather() {
        return mainWeather;
    }

    public Wind getWind() {
        return wind;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCod() {
        return cod;
    }


    public static WeatherResponse getCacheForId(Context context, int id){

        SharedPreferences sPref = context.getSharedPreferences(WEATHER_PREFERENCES, Context.MODE_PRIVATE);
        String json =  sPref.getString(String.valueOf(id), "");

        try {
            WeatherResponse weatherResponse = new Gson().fromJson(json, WeatherResponse.class);

            return weatherResponse;
        }catch (JsonSyntaxException ex){
            return null;
        }
    }

    public String getWeatherStatus(){
        String result = "";

        if(weatherList != null && weatherList.size() > 0){
            WeatherDesc  weather = weatherList.get(0);
            result = weather.getMain()  + "(" + weather.getDescription() + ")";
        }


        return result;
    }
}
