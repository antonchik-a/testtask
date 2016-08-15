package ru.test.weatherapp.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ru.test.weatherapp.Config;
import ru.test.weatherapp.models.base.BaseModel;
import ru.test.weatherapp.utils.CacheHelper;

import static ru.test.weatherapp.Config.*;
/**
 * Created by Alexey Antonchik on 03.08.16.
 */
public class City extends BaseModel {

    public final static String CACHE_KEY = "CITIES";


    @SerializedName("name")
    String name;

    @SerializedName("id")
    int id;

    public City() {
    }

    public City(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public City(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {

        if(name == null){
            return "";
        }

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<City> getCache(Context context){

        SharedPreferences sPref = context.getSharedPreferences(WEATHER_PREFERENCES, Context.MODE_PRIVATE);
        String json =  sPref.getString(CACHE_KEY, "");

        try {

            Type type = new TypeToken<List<City>>() {}.getType();

            List<City> cache = new Gson().fromJson(json, type);

            if(cache == null){
                cache = getStaticCities();
                CacheHelper.cacheDataForKey(context, getString(cache), CACHE_KEY);
            }

            return cache;
        }catch (JsonSyntaxException ex){
            Log.d(LOG_TAG, ex.getMessage());
        }
        return new ArrayList<>();


    }

    public static List<City> getStaticCities(){

        ArrayList<City> staticCities = new ArrayList<>();

        City moscow = new City("Moscow", 524901);
        staticCities.add(moscow);

        City kiev = new City("Kiev", 703448);
        staticCities.add(kiev);

        City london = new City("London", 2643743);
        staticCities.add(london);

        City tyumen = new City("Tyumen", 1488754);
        staticCities.add(tyumen);

        City nizhnevartovsk = new City("Nizhnevartovsk", 1497543);
        staticCities.add(nizhnevartovsk);

        return staticCities;
    }

    public static void remove(Context context, City city){

        List<City> cityArrayList = getCache(context);

        List<City> newArray = new ArrayList<>();

        for(City current : cityArrayList){
            if(current.getId() != city.getId()){
                newArray.add(current);
            }
        }

        CacheHelper.cacheDataForKey(context, getString(newArray), CACHE_KEY);
    }

    public static void add(Context context, City city){

        List<City> cityArrayList = getCache(context);

        List<City> newArray = new ArrayList<>(cityArrayList);

        newArray.add(city);

        CacheHelper.cacheDataForKey(context, getString(newArray), CACHE_KEY);
    }


    public static String getString(List<City> cities){
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String string = gson.toJson(cities);
        return string;
    }

}
