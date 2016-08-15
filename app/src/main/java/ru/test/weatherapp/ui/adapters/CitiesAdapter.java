package ru.test.weatherapp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import ru.test.weatherapp.R;
import ru.test.weatherapp.events.RemoveCityEvent;
import ru.test.weatherapp.events.ShowDetailEvent;
import ru.test.weatherapp.models.City;
import ru.test.weatherapp.ui.items.CityItem;

/**
 * Created by Alexey Antonchik on 13.08.16.
 */
public class CitiesAdapter extends RecyclerView.Adapter<CityItem> {
    private Context mContext;
    private List<City> mCities;

    public CitiesAdapter(Context context) {
        super();
        mContext = context;
        mCities = City.getCache(context);
    }

    public void updateData(){
        mCities = City.getCache(mContext);
        notifyDataSetChanged();
    }

    @Override
    public CityItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_city, parent, false);

        CityItem menuViewHolder = new CityItem(view, mContext);

        return menuViewHolder;
    }

    @Override
    public void onBindViewHolder(CityItem holder, final int position) {
        holder.setData(mCities.get(position));
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                City selectedCity = mCities.get(position);
                EventBus.getDefault().post(new ShowDetailEvent(selectedCity));
            }
        });

        holder.getView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                City selectedCity = mCities.get(position);
                EventBus.getDefault().post(new RemoveCityEvent(selectedCity));
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }

    public List<City> getCities() {
        return mCities;
    }
}
