package com.example.nathanshumm.decided.model.api;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {

    JSONObject jObject;
    public static ArrayList<Place> placeList = new ArrayList<>();

    // Invoked by execute() method of this object
    @Override
    protected List<HashMap<String, String>> doInBackground(String... jsonData) {

        List<HashMap<String, String>> places = null;
        Place_JSON placeJson = new Place_JSON();

        try {
            jObject = new JSONObject(jsonData[0]);

            places = placeJson.parse(jObject);

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }
        return places;
    }

    // Executed after the complete execution of doInBackground() method
    @Override
    protected void onPostExecute(List<HashMap<String, String>> list) {

        Log.d("Map", "list size: " + list.size());

        // Clears all the existing markers;

        for (int i = 0; i < list.size(); i++) {

            // Getting a place from the places list
            HashMap<String, String> hmPlace = list.get(i);

            // Getting latitude of the place
            double lat = Double.parseDouble(hmPlace.get("lat"));

            // Getting longitude of the place
            double lng = Double.parseDouble(hmPlace.get("lng"));

            // Getting name
            String name = hmPlace.get("place_name");

            Log.e("Maperr", "place: " + name + "\nphoto " + hmPlace.get("place_name"));

            // Getting vicinity
            String vicinity = hmPlace.get("vicinity");

            LatLng latLng = new LatLng(lat, lng);

            Place newPlace = new Place(lat, lng, name, vicinity, latLng);
            placeList.add(newPlace);

        }

    }
    public ArrayList<Place> getPlaceList(){
        return this.placeList;
    }
}
