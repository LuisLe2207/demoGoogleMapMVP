package com.example.luisle.googlemapclone.googleMap;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;

import com.example.luisle.googlemapclone.model.Direction;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        String baseUrl = "https://maps.googleapis.com/maps/";
        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(baseUrl)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

        GoogleMapAPI googleMapAPI = retrofit.create(GoogleMapAPI.class);
        Call<Direction> call = googleMapAPI.getDirection(startPoint, endPoint);
        call.enqueue(new Callback<Direction>() {
            @Override
            public void onResponse(@NonNull Call<Direction> call, @NonNull Response<Direction> response) {
                Direction direction = response.body();
                if (direction != null) {
                    LatLng origin = new LatLng(
                            direction.getRoutes().get(0).getLegs().get(0).getStart_location().getLat(),
                            direction.getRoutes().get(0).getLegs().get(0).getStart_location().getLng());
                    LatLng destination = new LatLng(
                            direction.getRoutes().get(0).getLegs().get(0).getEnd_location().getLat(),
                            direction.getRoutes().get(0).getLegs().get(0).getEnd_location().getLng());
                    String originAddress = direction.getRoutes().get(0).getLegs().get(0).getStart_address();
                    String destinationAddress = direction.getRoutes().get(0).getLegs().get(0).getEnd_address();
                    String polylinePoints = direction.getRoutes().get(0).getOverview_polyline().getPoints();
                    view.drawRoutes(origin, destination, originAddress, destinationAddress, polylinePoints);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Direction> call, @NonNull Throwable t) {

            }
        });
    }

}
