package ru.test.weatherapp.ui.fragments;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.test.weatherapp.R;
import ru.test.weatherapp.events.AfterRemoveCityEvent;
import ru.test.weatherapp.events.LoadGroupData;
import ru.test.weatherapp.events.RemoveCityEvent;
import ru.test.weatherapp.events.ShowDetailEvent;
import ru.test.weatherapp.loaders.GroupLoader;
import ru.test.weatherapp.loaders.QueryLoader;
import ru.test.weatherapp.models.City;
import ru.test.weatherapp.models.GroupResponse;
import ru.test.weatherapp.models.WeatherResponse;
import ru.test.weatherapp.models.base.BaseModel;
import ru.test.weatherapp.ui.activities.MainActivity;
import ru.test.weatherapp.ui.adapters.CitiesAdapter;
import ru.test.weatherapp.ui.fragments.base.BaseFragment;
import ru.test.weatherapp.utils.CacheHelper;
import ru.test.weatherapp.utils.ViewHelper;

import static ru.test.weatherapp.Config.*;
/**
 * Created by Alexey Antonchik on 10.08.16.
 */
public class CitiesFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<BaseModel>  {

    @BindView(R.id.citiesRecyclerView)
    RecyclerView mRecyclerView;


    @OnClick(R.id.add)
    public void addCity(View view) {
        showAddDialog(mQuery);
    }

    private CitiesAdapter mCitiesAdapter;
    private String mQuery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_cities, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mQuery = "";

        mRecyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));
        mCitiesAdapter = new CitiesAdapter(getActivity());
        mRecyclerView.setAdapter(mCitiesAdapter);

        if(mCitiesAdapter.getCities().size() > 0 && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && ((MainActivity)getActivity()).getDetailFragment() == null){
            Log.d(LOG_TAG, "showFirst");
            ((MainActivity)getActivity()).onShowDetailEvent( new ShowDetailEvent(mCitiesAdapter.getCities().get(0)));
        }

    }

    @Override
    public void onRefresh() {
        getLoaderManager().initLoader(R.id.group_loader, Bundle.EMPTY, this);
    }

    @Override
    public Loader<BaseModel> onCreateLoader(int id, Bundle args) {
        swipeRefreshLayout.setRefreshing(true);
        if (id == R.id.group_loader) {
            List<City> cities = City.getCache(getActivity());
            StringBuilder sb = new StringBuilder();

            for(City city : cities){
                if(sb.length() > 0){
                    sb.append(",");
                }
                sb.append(String.valueOf(city.getId()));
            }

            return new GroupLoader(getContext(), sb.toString());
        }else if(id == R.id.query_loader){
            return new QueryLoader(getActivity(), mQuery);
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<BaseModel> loader, BaseModel data) {

        swipeRefreshLayout.setRefreshing(false);

        if (loader.getId() == R.id.group_loader) {
            if (data != null) {
                GroupResponse groupResponse = (GroupResponse) data;

                List<WeatherResponse> citiesWeather = groupResponse.getWeatherResponseList();

                for(WeatherResponse weatherResponse : citiesWeather){
                    CacheHelper.cacheDataForKey(getActivity(), weatherResponse, String.valueOf(weatherResponse.getId()));
                }

                mCitiesAdapter.updateData();


            } else {
                showToast(getResources().getString(R.string.network_error));
            }
        }else if(loader.getId() == R.id.query_loader) {
            if (data != null) {
                WeatherResponse weatherResponse = (WeatherResponse) data;

                if(weatherResponse.getCod() == 200){

                    City newCity = new City(weatherResponse.getName(), weatherResponse.getId());

                    City.add(getActivity(), newCity);

                    mCitiesAdapter.updateData();

                    showToast(getResources().getString(R.string.add_success));

                    mQuery = "";
                }else {
                    showToast(getResources().getString(R.string.error_input));
                    showAddDialog(mQuery);
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
    public void onLoadGroupData(LoadGroupData loadGroupData){
        onRefresh();
    }

    @Subscribe
    public void onRemoveCityEvent(RemoveCityEvent removeCityEvent){
        showRemoveDialog(removeCityEvent.getCity());
    }

    private void showRemoveDialog(final City city){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.remove));
        String msg = String.format(getString(R.string.remove_city), city.getName());
        builder.setMessage(msg);

        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                City.remove(getActivity(), city);
                mCitiesAdapter.updateData();
                EventBus.getDefault().post(new AfterRemoveCityEvent(city));
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.create().show();

    }

    private void showAddDialog(String name){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.add_title));

        FrameLayout frameLayout = new FrameLayout(getActivity());
        int px = (int)ViewHelper.dpToPx(getActivity(), 16);
        frameLayout.setPadding(px, px, px, px);
        final EditText input = new EditText(getActivity());
        input.getLayoutParams();
        input.setText(name);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        frameLayout.addView(input);


        builder.setView(frameLayout);

        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mQuery = input.getText().toString().trim().toLowerCase();
                getLoaderManager().initLoader(R.id.query_loader, Bundle.EMPTY, CitiesFragment.this);
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

}
