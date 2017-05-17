package com.example.luisle.googlemapclone.googleMap;

import com.example.luisle.googlemapclone.common.BasePresenter;
import com.example.luisle.googlemapclone.common.BaseView;

/**
 * Created by LuisLe on 5/16/2017.
 */

public interface MapContract {
    interface View extends BaseView<Presenter> {
        void addMarker(double lat, double lng, String locality);
    }

    interface Presenter extends BasePresenter {
        void searchPlace(String locationName);
        void findRoutes(String startPoint, String endPoint);
    }
}
