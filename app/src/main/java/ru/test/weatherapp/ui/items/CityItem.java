package ru.test.weatherapp.ui.items;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.test.weatherapp.R;
import ru.test.weatherapp.events.LoadGroupData;
import ru.test.weatherapp.models.City;
import ru.test.weatherapp.models.WeatherResponse;
import ru.test.weatherapp.ui.items.base.BaseViewHolder;
import static ru.test.weatherapp.Config.*;
/**
 * Created by Alexey Antonchik on 03.08.16.
 */
public class CityItem extends BaseViewHolder {

    private View mView;
    private Context mContext;

    @BindView(R.id.cityName)
    TextView mCityName;

    @BindView(R.id.currentTemp)
    TextView mCurrentTemp;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;


    public CityItem(View itemView, Context mContext) {
        super(itemView);
        this.mView = itemView;
        this.mContext = mContext;
        ButterKnife.bind(this, mView);
    }


    public void setData(City city){
        if(city == null){
            return;
        }

        mCityName.setText(city.getName());

        WeatherResponse weatherResponseForCity = WeatherResponse.getCacheForId(mContext, city.getId());

        if(weatherResponseForCity != null){
            mCurrentTemp.setText(weatherResponseForCity.getMainWeather().getStringTemp());
            mProgressBar.setVisibility(View.INVISIBLE);
        }else {
            mCurrentTemp.setText("");
            mProgressBar.setVisibility(View.VISIBLE);
            EventBus.getDefault().post(new LoadGroupData());
        }
    }

    public View getView() {
        return mView;
    }
}
