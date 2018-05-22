package com.example.nathanshumm.decided.model.api;

import android.util.Log;

import com.google.android.gms.location.places.Place;

import java.util.ArrayList;

public class PlaceResponse {

    private String type;
    private String nextPageToken;
    private StringBuilder sbValue = new StringBuilder(sbMethod(false));
    private PlacesTask placesTask = new PlacesTask();
    private ParserTask parserTask = new ParserTask();

    public PlaceResponse() {
        placesTask.execute(sbValue.toString());
    }

    private StringBuilder sbMethod(boolean nextPage) {

        //use your current location here
        double mLatitude = 45.5016890;
        double mLongitude = -73.5672560;

        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location=" + mLatitude + "," + mLongitude);
        sb.append("&radius=5000");
        sb.append("&types=" + "restaurant");
        sb.append("&sensor=true");
        sb.append("&key=AIzaSyCTU-xhtgLhx05jPIoUdc_5ncsds1jWP2E");

        if(nextPage) {
            Log.e("NPT", "append next page token : " + nextPageToken);
            sb.append("&pagetoken=" + nextPageToken);
        }
        Log.d("Map", "api: " + sb.toString());

        return sb;
    }

    public void executeNextResponse(){
        setNextPageToken();
        sbValue = new StringBuilder(sbMethod(true));
        placesTask = new PlacesTask();
        placesTask.execute(sbValue.toString());
    }

    public ArrayList<com.example.nathanshumm.decided.model.api.Place> getPlace(){
            Log.e("Parse_Size", " "+ parserTask.getPlaceList().size());
        return parserTask.getPlaceList();
    }


    public void setNextPageToken(){
        this.nextPageToken = parserTask.getNextPageToken();
    }

}
