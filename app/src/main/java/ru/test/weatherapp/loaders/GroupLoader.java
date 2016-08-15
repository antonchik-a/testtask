package ru.test.weatherapp.loaders;

import android.content.Context;

import java.io.IOException;

import retrofit2.Call;
import ru.test.weatherapp.api.ApiFactory;
import ru.test.weatherapp.api.OpenWeatherMapService;
import ru.test.weatherapp.models.GroupResponse;
import static ru.test.weatherapp.Config.*;
/**
 * Created by Alexey Antonchik on 14.08.16.
 */
public class GroupLoader extends BaseLoader {


    String ids;

    public GroupLoader(Context context, String ids) {
        super(context);
        this.ids = ids;
    }

    @Override
    protected Object apiCall() throws IOException {
        OpenWeatherMapService service = ApiFactory.getService();

        Call<GroupResponse> call = service.getGroupWeather(ids, APPID, UNITS, LANG);

        return call.execute().body();
    }
}
