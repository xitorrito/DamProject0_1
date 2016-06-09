package com.example.xito.damproject01;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.xito.damproject01.Models.Networks;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, LocationListener{
    private GoogleMap mGoogleMap;
    private LatLng mLatLng;
    private GoogleApiClient mGoogleApiClient;
    private boolean firstTime=true;
    private SQLiteDatabase db;
    private DBManager dbManager;
    private Networks network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this).addApi(LocationServices.API)
                .build();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        dbManager = DBManager.getInstance(this);
        db = dbManager.getWritableDatabase();
        mGoogleApiClient.connect();


    }
    @Override
    public void onMapReady(GoogleMap map) {
        mGoogleMap = map;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                mGoogleMap.setMyLocationEnabled(true);
            }
        }else{
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        LocationRequest mLocationRequest = createLocationRequest();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                LocationServices.FusedLocationApi.requestLocationUpdates(
                        mGoogleApiClient, mLocationRequest, this);
            }
        }else{
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        if(getIntent().hasExtra("network")) {
            if (firstTime) {
                ScanResult networks = getIntent().getExtras().getParcelable("network");
                network = new Networks(networks.SSID, networks.BSSID, location.getLatitude(), location.getLongitude());
                ContentValues contentValues = new ContentValues();
                contentValues.put("networkName", network.getNetworkName());
                contentValues.put("macAddress", network.getMacAddress());
                contentValues.put("latitude", network.getLatitude());
                contentValues.put("longitude", network.getLongitude());
                db.insert("networks", null, contentValues);

            }
        }

        mGoogleMap.clear();
        setMarkers();
        if(firstTime)
            updateCamera();
        firstTime = false;


    }

    private LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    public void stopSharingLocation() {
        stopLocationUpdates();
        mGoogleApiClient.disconnect();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    private void setMarkers() {

        mGoogleMap.clear();
      /*  if(getIntent().hasExtra("network")) {
            LatLng coordenadesNetwork = new LatLng(network.getLatitude(), network.getLongitude());

            mGoogleMap.addMarker(new MarkerOptions()
                    .position(coordenadesNetwork)
                    .title(network.getNetworkName()));
        }*/


        ArrayList<Networks> networks = Networks.getNetworksFromDB(db);

        for (int i = 0; i < networks.size(); i++) {

            LatLng coordenades = new LatLng(networks.get(i).getLatitude(), networks.get(i).getLongitude());

            mGoogleMap.addMarker(new MarkerOptions()
                    .position(coordenades)
                    .title(networks.get(i).getNetworkName()));
        }

    }

    private void updateCamera() {
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 18));
    }

    public void onPause(){
        super.onPause();
        // stopSharingLocation();
    }

    @Override
    protected void onResume() {
        //startSharingLocation();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopSharingLocation();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(!getIntent().hasExtra("backMenu")){
            Intent intent = new Intent(this,HackDevices.class);
            startActivity(intent);
            finish();
        }
    }
}
