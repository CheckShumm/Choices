package com.example.nathanshumm.decided.model.api;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.nathanshumm.decided.view.main.MainActivity;
import com.google.android.gms.location.LocationListener;

import com.google.android.gms.location.places.Place;

import java.util.ArrayList;

public class PlaceResponse {

    private String nextPageToken;
    private Context context;
    private StringBuilder sbValue;
    private PlacesTask placesTask;
    private ParserTask parserTask;

    private static String type = "restaurant";

    private static double latitude = 45.5016890;
    private static double longitude = -73.5672560;

    public PlaceResponse(Context context) {
        Log.e("context", "place response task context = " + context);
        this.context = context;
    }

    private StringBuilder sbMethod(boolean nextPage) {

        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location=" + latitude + "," + longitude);
        sb.append("&radius=5000");
        sb.append("&types=" + type);
        sb.append("&sensor=true");
        sb.append("&key=AIzaSyCTU-xhtgLhx05jPIoUdc_5ncsds1jWP2E");
        Log.d("LatLon", "String builder Lat: " + latitude + "  long: " + longitude);
        if(nextPage && nextPageToken.length()>5) {
            Log.e("NPT", "append next page token : " + nextPageToken);
            sb.append("&pagetoken=" + nextPageToken);
        }
        Log.d("Map", "api: " + sb.toString());

        return sb;
    }

    public void execute(){
        Log.d("Map", "executing");
        placesTask = new PlacesTask(context);
        sbValue = new StringBuilder(sbMethod(false));
        placesTask.execute(sbValue.toString());
    }

    public void executeNextResponse(){
        setNextPageToken();
        sbValue = new StringBuilder(sbMethod(true));
        placesTask = new PlacesTask(this.context);
        placesTask.execute(sbValue.toString());
    }

    public ArrayList<com.example.nathanshumm.decided.model.api.Place> getPlace(){
            Log.e("Parse_Size", " "+ ParserTask.getPlaceList().size());
        return ParserTask.getPlaceList();
    }


    public void setNextPageToken(){
        this.nextPageToken = ParserTask.getNextPageToken();
    }

    public void setType(String newType){
        type = newType;
        Log.e("filter", "set type: " + newType);
    }

    public String getType(){
        return this.type;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
        Log.d("LatLon", "Lat resp: " + latitude + "  long: " + longitude);
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
        Log.d("LatLon", "Lat: " + latitude + "  long resp: " + longitude);
    }

}
