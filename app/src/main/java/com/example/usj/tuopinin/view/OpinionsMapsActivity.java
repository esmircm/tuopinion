package com.example.usj.tuopinin.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.usj.tuopinin.Constants;
import com.example.usj.tuopinin.R;
import com.example.usj.tuopinin.model.CachePlaces;
import com.example.usj.tuopinin.model.entities.Place;
import com.example.usj.tuopinin.presenter.MapsPresenter;
import com.example.usj.tuopinin.view.interfaces.MapsView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.List;

import static com.example.usj.tuopinin.Constants.PLACES_LIST;

public class OpinionsMapsActivity extends FragmentActivity implements OnMapReadyCallback, MapsView {

    GoogleMap maps;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    FusedLocationProviderClient mFusedLocationClient;
    private MapsPresenter mapsPresenter;
    private BottomRegisterFragment bottomRegisterFragment;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private List<Place> placesList;
    private Bundle savedState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinions_maps);
        savedState = savedInstanceState;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mapsPresenter = new MapsPresenter(this, CachePlaces.getInstance());
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (maps != null) {
            maps.setOnMarkerClickListener(null);
            maps.setOnMapClickListener(null);
        }
        mapFrag.getMapAsync(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        maps = googleMap;
        maps.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(120000);
        mLocationRequest.setFastestInterval(120000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                maps.setMyLocationEnabled(true);

            } else {

                checkLocationPermission();

            }

        } else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            maps.setMyLocationEnabled(true);
        }

        maps.setOnMarkerClickListener(marker -> {
            mapsPresenter.openDetailsFragment(marker.getPosition().latitude, marker.getPosition().longitude);
            return true;
        });

        maps.setOnMapClickListener(point -> {
            maps.addMarker(new MarkerOptions().position(point));
            maps.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 16.0f));
        });

        if (savedState != null) {
            placesList = (List<Place>) savedState.getSerializable(PLACES_LIST);
            displayPlaces(placesList);
        } else {
            mapsPresenter.displayPlaces();
        }

    }


    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {

            List<Location> locationList = locationResult.getLocations();

            if (locationList.size() > 0) {

                Location location = locationList.get(locationList.size() - 1);

                mLastLocation = location;

                if (mCurrLocationMarker != null) {

                    mCurrLocationMarker.remove();

                }

                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(getString(R.string.position));
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                mCurrLocationMarker = maps.addMarker(markerOptions);

                maps.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
            }
        }
    };

    private void checkLocationPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.permission_title))
                    .setMessage(getString(R.string.permission_msg))
                    .setPositiveButton("OK", (dialogInterface, i) -> ActivityCompat.requestPermissions(OpinionsMapsActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION))
                    .create()
                    .show();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {

            case MY_PERMISSIONS_REQUEST_LOCATION: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        maps.setMyLocationEnabled(true);
                    }

                } else {

                    Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void displayPlaces(List<Place> places) {
        placesList = places;
        for (Place place : places) {
            LatLng latLng = new LatLng(place.getLatitude(), place.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            String rating = place.getRating() == null ? " " : " rating: " + String.valueOf(place.getRating());
            markerOptions.title(place.getName() + rating);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
            Marker marker = maps.addMarker(markerOptions);
            marker.showInfoWindow();
        }
    }

    @Override
    public void openDetailsFragment(double latitude, double longitude) {
        BottomCommentFragment bottomCommentFragment =
                BottomCommentFragment.newInstance(latitude, longitude);
        bottomCommentFragment.show(getSupportFragmentManager(),
                "bottomDetailsFragment");
    }

    @Override
    public void openRegisterFragment(double latitude, double longitude) {
        bottomRegisterFragment = BottomRegisterFragment.newInstance(latitude, longitude);
        bottomRegisterFragment.show(getSupportFragmentManager(),
                "bottomRegisterFragment");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constants.SAVE_PLACE && resultCode == RESULT_OK) {
            mapsPresenter.displayPlaces();
        }

        if (requestCode == Constants.ADD_COMMENT && resultCode == RESULT_OK) {
            mapsPresenter.displayPlaces();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(PLACES_LIST, (Serializable) placesList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapsPresenter.onDestroy();
    }
}
