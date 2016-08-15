package ru.test.weatherapp.ui.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.greenrobot.eventbus.Subscribe;

import ru.test.weatherapp.R;
import ru.test.weatherapp.events.LoadGroupData;
import ru.test.weatherapp.events.ShowDetailEvent;
import ru.test.weatherapp.ui.activities.base.BaseActivity;
import ru.test.weatherapp.ui.fragments.DetailWeatherFragment;

import static ru.test.weatherapp.Config.*;

public class MainActivity extends BaseActivity {

    public DetailWeatherFragment mDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActionBar();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Subscribe
    public void onShowDetailEvent(ShowDetailEvent showDetailEvent){

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            Intent detailIntent = new Intent(this, DetailCityWeatherActivity.class);
            detailIntent.putExtra(CITY_ID, showDetailEvent.getCity().getId());
            startActivity(detailIntent);
        }else {
            DetailWeatherFragment detailWeatherFragment = new DetailWeatherFragment();

            Bundle bundle = new Bundle();
            bundle.putInt(CITY_ID, showDetailEvent.getCity().getId());

            detailWeatherFragment.setArguments(bundle);

            if (mDetailFragment == null || (mDetailFragment.getArguments().getInt(CITY_ID) != showDetailEvent.getCity().getId() )) {
                getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.content_frame, detailWeatherFragment).commit();
                mDetailFragment = detailWeatherFragment;
            }

        }
    }

    public DetailWeatherFragment getDetailFragment() {
        return mDetailFragment;
    }
}
