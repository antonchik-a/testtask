package ru.test.weatherapp.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import ru.test.weatherapp.R;
import ru.test.weatherapp.ui.activities.base.BaseActivity;
import ru.test.weatherapp.ui.fragments.DetailWeatherFragment;

import static ru.test.weatherapp.Config.CITY_ID;

/**
 * Created by Alexey Antonchik on 11.08.16.
 */
public class DetailCityWeatherActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setActionBar();

        if(getIntent().hasExtra(CITY_ID)) {
            DetailWeatherFragment detailWeatherFragment = new DetailWeatherFragment();

            Bundle bundle = new Bundle();
            bundle.putInt(CITY_ID, getIntent().getIntExtra(CITY_ID, 0));

            detailWeatherFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, detailWeatherFragment).commit();

        }else {
            showToast(getResources().getString(R.string.error));
        }
    }
}
