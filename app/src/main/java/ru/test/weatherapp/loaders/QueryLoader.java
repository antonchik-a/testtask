package ru.test.weatherapp.loaders;

import android.content.Context;

import java.io.IOException;

import retrofit2.Call;
import ru.test.weatherapp.api.ApiFactory;
import ru.test.weatherapp.api.OpenWeatherMapService;
import ru.test.weatherapp.models.WeatherResponse;

import static ru.test.weatherapp.Config.APPID;
import static ru.test.weatherapp.Config.LANG;
import static ru.test.weatherapp.Config.UNITS;

/**
 * Created by Alexey Antonchik on 14.08.16.
 */
public class QueryLoader extends BaseLoader {
    String query;

    public QueryLoader(Context context, String query) {
        super(context);
        this.query = query;
    }

    @Override
    protected Object apiCall() throws IOException {
        OpenWeatherMapService service = ApiFactory.getService();

        Call<WeatherResponse> call = service.getWeatherByQuery(query, APPID, UNITS, LANG);

        return call.execute().body();
    }
}
