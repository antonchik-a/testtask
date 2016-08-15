package ru.test.weatherapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import ru.test.weatherapp.models.City;
import ru.test.weatherapp.models.base.BaseModel;
import static ru.test.weatherapp.Config.*;
/**
 * Created by Alexey Antonchik on 14.08.16.
 */
public class CacheHelper {


    public static void cacheDataForKey(Context context, String string, String key){
        SharedPreferences sPref = context.getSharedPreferences(WEATHER_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(key, string);
        ed.apply();
        ed.commit();

        Log.d(LOG_TAG, "cache " + key + " " + string);
    }


    public static void cacheDataForKey(Context context, BaseModel data, String key){
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String string = gson.toJson(data);
        cacheDataForKey(context, string, key);
    }



}
