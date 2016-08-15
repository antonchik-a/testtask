package ru.test.weatherapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.test.weatherapp.R;
import ru.test.weatherapp.events.AfterRemoveCityEvent;
import ru.test.weatherapp.events.LoadGroupData;
import ru.test.weatherapp.events.RemoveCityEvent;
import ru.test.weatherapp.loaders.GroupLoader;
import ru.test.weatherapp.loaders.IdLoader;
import ru.test.weatherapp.models.City;
import ru.test.weatherapp.models.GroupResponse;
import ru.test.weatherapp.models.WeatherResponse;
import ru.test.weatherapp.models.base.BaseModel;
import ru.test.weatherapp.ui.fragments.base.BaseFragment;
import ru.test.weatherapp.utils.CacheHelper;

import static ru.test.weatherapp.Config.*;
/**
 * Created by Alexey Antonchik on 14.08.16.
 */
public class DetailWeatherFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<BaseModel> {

    private WeatherResponse mWeatherResponse;
    private int mCityId;

    @BindView(R.id.status) TextView mStatusTextView;
    @BindView(R.id.pressure) TextView mPressureTextView;
    @BindView(R.id.wind) TextView mWindTextView;
    @BindView(R.id.temp) TextView mTempTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_detail, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mCityId =  getArguments().getInt(CITY_ID, 0);

        if(mCityId > 0){
            mWeatherResponse = WeatherResponse.getCacheForId(getActivity(), mCityId);

            if(mWeatherResponse == null){
                onRefresh();
            }else {
                setData();
            }
        }else {
            showToast(getResources().getString(R.string.error));
        }
    }

    public void setData(){
        if(mWeatherResponse != null) {
            String statusString = String.format(getString(R.string.status), mWeatherResponse.getName(), mWeatherResponse.getWeatherStatus());
            mStatusTextView.setText(statusString);

            String temp = String.format(getString(R.string.temp), mWeatherResponse.getMainWeather().getStringTemp());
            mTempTextView.setText(temp);

            String pressure = String.format(getString(R.string.pressure), mWeatherResponse.getMainWeather().getStringPressure());
            mPressureTextView.setText(pressure);

            String wind = String.format(getString(R.string.wind), mWeatherResponse.getWind().getStringSpeed());
            mWindTextView.setText(wind);
        }else {
            mStatusTextView.setText("");
            mTempTextView.setText("");
            mPressureTextView.setText("");
            mWindTextView.setText("");
        }
    }

    @Override
    public void onRefresh() {
        getLoaderManager().initLoader(R.id.id_loader, Bundle.EMPTY, this);
    }

    @Override
    public Loader<BaseModel> onCreateLoader(int id, Bundle args) {
        swipeRefreshLayout.setRefreshing(true);
        if (id == R.id.id_loader) {
            return new IdLoader(getContext(), String.valueOf(mCityId));
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<BaseModel> loader, BaseModel data) {

        swipeRefreshLayout.setRefreshing(false);

        if (loader.getId() == R.id.id_loader) {
            if (data != null) {
                WeatherResponse groupResponse = (WeatherResponse) data;

                if(groupResponse != null) {
                    CacheHelper.cacheDataForKey(getActivity(), groupResponse, String.valueOf(mCityId));

                    mWeatherResponse = groupResponse;

                    setData();
                }


            } else {
                showToast(getResources().getString(R.string.network_error));
            }
        }

        getLoaderManager().destroyLoader(loader.getId());
    }

    @Override
    public void onLoaderReset(Loader<BaseModel> loader) {

    }

    @Subscribe
    public void onAfterRemoveCityEvent(AfterRemoveCityEvent afterRemoveCityEvent){
        if(mCityId == afterRemoveCityEvent.getCity().getId()){
            mWeatherResponse = null;
            setData();
        }
    }

}
