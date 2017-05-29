package com.example.luisle.googlemapclone.googleMap;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
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
        view.showProgressDialog("Searching ...");
        try {
            Geocoder geocoder = new Geocoder(context);
            List<Address> list = geocoder.getFromLocationName(locationName, 1);
            Address address = list.get(0);
            final String locality = address.getLocality();
            final double lat = address.getLatitude();
            final double lng = address.getLongitude();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.hideProgressDialog();
                    view.addMarker(lat, lng, locality);
                }
            }, 2000);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void findRoutes(String startPoint, String endPoint) {
        view.showProgressDialog("Finding ...");
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
                    final LatLng origin = new LatLng(
                            direction.getRoutes().get(0).getLegs().get(0).getStart_location().getLat(),
                            direction.getRoutes().get(0).getLegs().get(0).getStart_location().getLng());
                    final LatLng destination = new LatLng(
                            direction.getRoutes().get(0).getLegs().get(0).getEnd_location().getLat(),
                            direction.getRoutes().get(0).getLegs().get(0).getEnd_location().getLng());
                    final String originAddress = direction.getRoutes().get(0).getLegs().get(0).getStart_address();
                    final String destinationAddress = direction.getRoutes().get(0).getLegs().get(0).getEnd_address();
                    final String polylinePoints = direction.getRoutes().get(0).getOverview_polyline().getPoints();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            view.hideProgressDialog();
                            view.drawRoutes(origin, destination, originAddress, destinationAddress, polylinePoints);
                        }
                    }, 1000);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Direction> call, @NonNull Throwable t) {

            }
        });
    }

}
