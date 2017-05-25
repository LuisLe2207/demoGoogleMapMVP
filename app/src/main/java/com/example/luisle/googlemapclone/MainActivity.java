package com.example.luisle.googlemapclone;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.luisle.googlemapclone.googleMap.MapContract;
import com.example.luisle.googlemapclone.googleMap.MapPresenter;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MapContract.View, OnMapReadyCallback {

    private MapFragment mapFragment;
    private MapPresenter mapPresenter;
    private GoogleMap googleMap;
    private Marker searchMarker;

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;

    private FloatingActionButton fabFindRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabFindRoute = (FloatingActionButton) findViewById(R.id.fabFindRoute);

        mapPresenter = new MapPresenter(MainActivity.this, this);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);

        fabFindRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String origin = "Go Vap, Ho Chi Minh City";
                String destination = "Binh Thanh, Ho Chi Minh City";
                mapPresenter.findRoutes(origin, destination);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
//        MenuItem item = menu.findItem(R.id.menuSearch);
//        SearchView searchView = (SearchView) item.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                mapPresenter.searchPlace(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSearch:
                try {
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(this);
                    startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE && resultCode == RESULT_OK && data != null) {
            Place place = PlaceAutocomplete.getPlace(this, data);
            mapPresenter.searchPlace(String.valueOf(place.getName()));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        this.googleMap.setMyLocationEnabled(true);
    }

    @Override
    public void setPresenter(MapContract.Presenter presenter) {
        if (presenter != null) {

        }
    }


    @Override
    public void addMarker(double lat, double lng, String locality) {
        float zoom = 15;
        LatLng newLatLng = new LatLng(lat, lng);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(newLatLng, zoom);

        // Check and remove marker
        if (searchMarker != null) {
            searchMarker.remove();
        }

        MarkerOptions markerOptions = new MarkerOptions()
                                            .position(newLatLng)
                                            .title(locality);
        searchMarker = googleMap.addMarker(markerOptions);

        googleMap.moveCamera(cameraUpdate);
    }

    @Override
    public void drawRoutes(LatLng origin, LatLng destination, String originAddress, String destinationAddress, String polylinePoints) {
        List<LatLng> decodedPath = PolyUtil.decode(polylinePoints);
        PolylineOptions polylineOptions = new PolylineOptions()
                                                .addAll(decodedPath)
                                                .clickable(true);

        googleMap.addPolyline(polylineOptions);

        googleMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
                Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
            }
        });


        googleMap.addMarker(new MarkerOptions().position(origin).title(originAddress));
        googleMap.addMarker(new MarkerOptions().position(destination).title(destinationAddress));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 15));
    }

}
