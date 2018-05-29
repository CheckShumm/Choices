package com.example.nathanshumm.decided.model.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {

    JSONObject jObject;
    private boolean loaded;
    private Context context;
    private GeoDataClient geoDataClient;
    public static String nextPageToken;
    public static ArrayList<Place> placeList = new ArrayList<>();

    public ParserTask(Context context) {
        this.context = context;
        Log.e("context", "parser task context = " + context);
        geoDataClient = Places.getGeoDataClient(context,null);
    }

    // Invoked by execute() method of this object
    @Override
    protected List<HashMap<String, String>> doInBackground(String... jsonData) {
        Log.e("NPT", "parserTask in background");
        List<HashMap<String, String>> places = null;
        Place_JSON placeJson = new Place_JSON();

        try {
            jObject = new JSONObject(jsonData[0]);

            places = placeJson.parse(jObject);
            this.nextPageToken = placeJson.nextPageToken;
            Log.e("nextPageToken", this.nextPageToken);

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }
        return places;
    }

    // Executed after the complete execution of doInBackground() method
    @Override
    protected void onPostExecute(List<HashMap<String, String>> list) {
        Log.e("NPT", "parserTask on post executes");
        loaded = false;
        Log.d("Map", "list size: " + list.size());

        // Clears all the existing markers;
        if(placeList.size() > 19)
            placeList.clear();

        for (int i = 0; i < list.size(); i++) {

            // Getting a place from the places list
            HashMap<String, String> hmPlace = list.get(i);

            // Getting latitude of the place
            double lat = Double.parseDouble(hmPlace.get("lat"));

            // Getting longitude of the place
            double lng = Double.parseDouble(hmPlace.get("lng"));

            // Getting name
            String name = hmPlace.get("place_name");

            Log.e("NPT", "place: " + name);

            // Getting vicinity
            String vicinity = hmPlace.get("vicinity");

            LatLng latLng = new LatLng(lat, lng);

            String placeId = hmPlace.get("id");

            double rating = Double.parseDouble(hmPlace.get("rating"));

            Place newPlace = new Place(lat, lng, name, vicinity, latLng, placeId, rating);
            setPhoto(newPlace);
            placeList.add(newPlace);
            loaded = true;
        }

    }

    public static String getNextPageToken(){
        return nextPageToken;
    }
    public static ArrayList<Place> getPlaceList(){
        return placeList;
    }

    public void setPhoto(final Place place) {
        // Get Photo
        final Task<PlacePhotoMetadataResponse> photoMetadataResponse = geoDataClient.
                getPlacePhotos(place.getPlaceId());
        Log.e("photoRef", "set photo 1 : " + place.getName());
        photoMetadataResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
                // Get the list of photos.
                PlacePhotoMetadataResponse photos = task.getResult();
                // Get the PlacePhotoMetadataBuffer (metadata for all of the photos).
                final PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                // Get the first photo in the list.
                if (photoMetadataBuffer.getCount() > 0) {
                    final PlacePhotoMetadata photoMetadata = photoMetadataBuffer.get(0);

                    // Get the attribution text.
                    CharSequence attribution = photoMetadata.getAttributions();
                    // Get a full-size bitmap for the photo.

                    final Task<PlacePhotoResponse> photoResponse = geoDataClient.getScaledPhoto(photoMetadata,316,316);
                    photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                        @Override
                        public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                            PlacePhotoResponse photo = task.getResult();
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 8;
                            Bitmap bitmap = photo.getBitmap();
                            Log.e("photoRef", "set photo 2 : " + place.getName());
                            place.setPhoto(bitmap);
                            photoMetadataBuffer.release();
                        }
                    });
                }
            }
        });
    }
}
