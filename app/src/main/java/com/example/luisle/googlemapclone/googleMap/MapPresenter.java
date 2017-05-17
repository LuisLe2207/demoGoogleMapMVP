package com.example.luisle.googlemapclone.googleMap;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;

/**
 * Created by LuisLe on 5/16/2017.
 */

public class MapPresenter implements MapContract.Presenter {

    private MapContract.View view;
    private Context context;

    public MapPresenter(Context context, MapContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void searchPlace(String locationName) {
        try {
            Geocoder geocoder = new Geocoder(context);
            List<Address> list = geocoder.getFromLocationName(locationName, 1);
            Address address = list.get(0);
            String locality = address.getLocality();
            double lat = address.getLatitude();
            double lng = address.getLongitude();
            view.addMarker(lat, lng, locality);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void findRoutes(String startPoint, String endPoint) {

    }

}
