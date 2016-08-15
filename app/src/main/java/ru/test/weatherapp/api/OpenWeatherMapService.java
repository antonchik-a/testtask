package ru.test.weatherapp.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.test.weatherapp.models.GroupResponse;
import ru.test.weatherapp.models.WeatherResponse;

/**
 * Created by Alexey Antonchik on 03.08.16.
 */
public interface OpenWeatherMapService {


    @GET("group")
    Call<GroupResponse> getGroupWeather(
            @Query("id")      String id,
            @Query("appid")    String appid,
            @Query("units")     String units,
            @Query("lang")     String lang);

    @GET("weather")
    Call<WeatherResponse> getWeatherById(
            @Query("id")      String id,
            @Query("appid")    String appid,
            @Query("units")     String units,
            @Query("lang")     String lang);

    @GET("weather")
    Call<WeatherResponse> getWeatherByQuery(
            @Query("q")      String q,
            @Query("appid")    String appid,
            @Query("units")     String units,
            @Query("lang")     String lang);
}
