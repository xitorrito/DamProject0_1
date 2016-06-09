package com.example.xito.damproject01.Models;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Xito on 04/06/2016.
 */
public class Networks implements Serializable{

    private String networkName;
    private String macAddress;
    private double latitude;
    private double longitude;

    public Networks(String networkName, String macAddress, double latitude, double longitude) {
        this.networkName = networkName;
        this.macAddress = macAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Networks() {

    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public static ArrayList<Networks> getNetworksFromDB (SQLiteDatabase db) {
        ArrayList<Networks> networksArrayList = new ArrayList<>();
        Cursor c;
        c = db.query("networks", new String[]{"networkName", "macAddress", "latitude", "longitude"}, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                Networks network = new Networks();
                network.setNetworkName(c.getString(0));
                network.setMacAddress(c.getString(1));
                network.setLatitude(c.getDouble(2));
                network.setLongitude(c.getDouble(3));

                networksArrayList.add(network);

            } while (c.moveToNext());

        }
        return networksArrayList;
    }
}
