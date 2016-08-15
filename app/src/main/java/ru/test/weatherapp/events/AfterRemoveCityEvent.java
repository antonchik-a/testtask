package ru.test.weatherapp.events;

import ru.test.weatherapp.models.City;

/**
 * Created by Alexey Antonchik on 14.08.16.
 */
public class AfterRemoveCityEvent extends BaseEvent {

    private City mCity;

    public AfterRemoveCityEvent(City mCity) {
        this.mCity = mCity;
    }

    public City getCity() {
        return mCity;
    }
}
