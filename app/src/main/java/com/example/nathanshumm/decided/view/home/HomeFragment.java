package com.example.nathanshumm.decided.view.home;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nathanshumm.decided.R;
import com.example.nathanshumm.decided.model.api.Place;
import com.example.nathanshumm.decided.model.api.PlaceResponse;
import com.example.nathanshumm.decided.viewmodel.home.HomeViewModel;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{

    private HomeViewModel homeViewModel;
    private PlaceResponse placeResponse;
    private Button testButton;
    private TextView placeText;
    private ImageView placeImage;
    private ArrayList<Place> newPlaceList = new ArrayList<Place>();

    // clients
    private GeoDataClient geoDataClient;
    private GoogleApiClient googleApiClient;
    int counter = 0;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View homeView = inflater.inflate(R.layout.fragment_home, container, false);
        testButton = (Button)homeView.findViewById(R.id.testButton);
        placeText = (TextView)homeView.findViewById(R.id.tv_places);
        placeImage = (ImageView)homeView.findViewById(R.id.iv_places);

        placeResponse = new PlaceResponse();
        testButton.setOnClickListener(this);
        geoDataClient = Places.getGeoDataClient(this.getActivity(),null);

        return homeView;
    }

    private void retrievePlace(){
        newPlaceList =  placeResponse.getPlace();
        Log.e("placesAPI", "" + newPlaceList.size());
        placeText.setText(newPlaceList.get(counter).getName());

        //get photo
        final Task<PlacePhotoMetadataResponse> photoMetadataResponse = geoDataClient.
                getPlacePhotos(newPlaceList.get(counter).getPlaceId());

        photoMetadataResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
                // Get the list of photos.
                PlacePhotoMetadataResponse photos = task.getResult();
                // Get the PlacePhotoMetadataBuffer (metadata for all of the photos).
                PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                // Get the first photo in the list.
                PlacePhotoMetadata photoMetadata = photoMetadataBuffer.get(0);
                // Get the attribution text.
                CharSequence attribution = photoMetadata.getAttributions();
                // Get a full-size bitmap for the photo.
                Task<PlacePhotoResponse> photoResponse = geoDataClient.getPhoto(photoMetadata);
                photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                        PlacePhotoResponse photo = task.getResult();
                        Bitmap bitmap = photo.getBitmap();
                        placeImage.setImageBitmap(bitmap);
                    }
                });
            }
        });


        counter++;
    }

    @Override
    public void onClick(View v) {
        retrievePlace();
    }
}
