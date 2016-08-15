package ru.test.weatherapp.ui.fragments.base;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import ru.test.weatherapp.R;
import ru.test.weatherapp.events.BaseEvent;
import ru.test.weatherapp.ui.activities.base.BaseActivity;

/**
 * Created by Alexey Antonchik on 10.08.16.
 */
public class BaseFragment extends Fragment {

    protected ActionBar toolBar;
    public BaseActivity mActivity;

    @Nullable
    @BindView(R.id.swipe_to_refresh_layout)
    protected SwipeRefreshLayout swipeRefreshLayout;

    public void showToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
        setToolBar();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (swipeRefreshLayout != null) {
            @ColorInt int color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
            swipeRefreshLayout.setColorSchemeColors(color);
            swipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener)this);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onBaseEvent(BaseEvent baseEvent){

    }

    public void setToolBar() {
        toolBar = mActivity.getSupportActionBar();
        toolBar.setDisplayShowTitleEnabled(true);
    }
}
