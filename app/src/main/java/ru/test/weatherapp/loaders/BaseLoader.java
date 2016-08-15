package ru.test.weatherapp.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Alexey Antonchik on 14.08.16.
 */
public abstract class BaseLoader<T> extends AsyncTaskLoader<T> {

    private final static String TAG = "BaseLoader";

    public BaseLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public T loadInBackground() {
        try {
            T response = apiCall();

            if (response == null) {
                onError();
                return null;
            }

            onSuccess();

            return response;
        } catch (IOException e) {
            onError();
            if(e.getMessage() != null) {
                Log.d(TAG, e.getMessage());
                e.printStackTrace();
            }

            return null;
        }
    }

    protected void onSuccess() {
    }

    protected void onError() {
    }

    protected abstract T apiCall() throws IOException;
}
