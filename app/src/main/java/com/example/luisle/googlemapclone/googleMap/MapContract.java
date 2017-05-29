package com.example.luisle.googlemapclone.googleMap;

import com.example.luisle.googlemapclone.common.BasePresenter;
import com.example.luisle.googlemapclone.common.BaseView;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by LuisLe on 5/16/2017.
 */

public interface MapContract {
    interface View extends BaseView<Presenter> {
        void showProgressDialog(String message);
        void hideProgressDialog();
        void addMarker(double lat, double lng, String locality);
        void drawRoutes(LatLng origin, LatLng destination, String originAddress, String destinationAddress,String polylinePoints);
    }

    interface Presenter extends BasePresenter {
        void searchPlace(String locationName);
        void findRoutes(String startPoint, String endPoint);
    }
}
