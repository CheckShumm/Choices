package com.example.nathanshumm.decided.model.api;

import android.util.Log;

import com.google.android.gms.location.places.Place;

import java.util.ArrayList;

public class PlaceResponse {

    private String type;
    private StringBuilder sbValue = new StringBuilder(sbMethod());
    private PlacesTask placesTask = new PlacesTask();

    private StringBuilder sbMethod() {

        //use your current location here
        double mLatitude = 45.5016890;
        double mLongitude = -73.5672560;

        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location=" + mLatitude + "," + mLongitude);
        sb.append("&radius=5000");
        sb.append("&types=" + "restaurant");
        sb.append("&sensor=true");
        sb.append("&key=AIzaSyCTU-xhtgLhx05jPIoUdc_5ncsds1jWP2E");

        Log.d("Map", "api: " + sb.toString());

        return sb;
    }

    public ArrayList<com.example.nathanshumm.decided.model.api.Place> getPlace(int index){
        placesTask.execute(sbValue.toString());
        return placesTask.getPlaceList();
    }

}
