package com.example.luisle.googlemapclone.googleMap;

import com.example.luisle.googlemapclone.model.Direction;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by LuisLe on 5/25/2017.
 */

public interface GoogleMapAPI {
    @GET("api/directions/json?key=AIzaSyD9EagKsWiKToCHCpLGsoEUJ1zEPDEp3Fs")
    Call<Direction> getDirection(@Query("origin") String origin, @Query("destination") String destination);
}
