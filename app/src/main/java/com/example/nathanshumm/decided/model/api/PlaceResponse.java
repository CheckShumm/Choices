package com.example.nathanshumm.decided.model.api;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.location.places.Place;

import java.util.ArrayList;

public class PlaceResponse {

    private static String type = "restaurant";
    private String nextPageToken;
    private Context context;
    private StringBuilder sbValue = new StringBuilder(sbMethod(false));
    private PlacesTask placesTask;
    private ParserTask parserTask;

    public PlaceResponse(Context context) {
        Log.e("context", "place response task context = " + context);
        this.context = context;
        placesTask = new PlacesTask(context);
    }

    private StringBuilder sbMethod(boolean nextPage) {

        //use your current location here
        double mLatitude = 45.5016890;
        double mLongitude = -73.5672560;

        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location=" + mLatitude + "," + mLongitude);
        sb.append("&radius=5000");
        sb.append("&types=" + type);
        sb.append("&sensor=true");
        sb.append("&key=AIzaSyCTU-xhtgLhx05jPIoUdc_5ncsds1jWP2E");

        if(nextPage && nextPageToken.length()>5) {
            Log.e("NPT", "append next page token : " + nextPageToken);
            sb.append("&pagetoken=" + nextPageToken);
        }
        Log.d("Map", "api: " + sb.toString());

        return sb;
    }

    public void execute(){
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

}
