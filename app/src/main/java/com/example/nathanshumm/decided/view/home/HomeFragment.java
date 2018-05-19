package com.example.nathanshumm.decided.view.home;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.example.nathanshumm.decided.viewmodel.home.CardAdapter;
import com.example.nathanshumm.decided.viewmodel.home.HomeViewModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.wenchao.cardstack.CardStack;
import com.willy.ratingbar.ScaleRatingBar;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements CardStack.CardEventListener {

    private HomeViewModel homeViewModel;
    private PlaceResponse placeResponse;
    private Button testButton;
    private TextView placeText;
    private TextView placeRating;
    private ImageView placeImage;

    private ScaleRatingBar scaleRatingBar; // Rating Bar
    private ArrayList<Place> newPlaceList = new ArrayList<Place>(); // Arraylist of places from places api

    // Card stack
    private CardStack cardStack;
    private CardAdapter cardAdapter;

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
        placeText = (TextView)homeView.findViewById(R.id.tv_places);
        placeImage = (ImageView)homeView.findViewById(R.id.iv_places);
        placeResponse = new PlaceResponse();
        initPlaces();

        cardStack = (CardStack)homeView.findViewById(R.id.card_stack);
        cardStack.setContentResource(R.layout.card_layout);
        cardStack.setStackMargin(20);
        cardStack.setAdapter(cardAdapter);

        cardStack.setListener(this);

        //placeRating = (TextView) homeView.findViewById(R.id.tv_rating);
        scaleRatingBar = homeView.findViewById(R.id.simpleRatingBar);

        geoDataClient = Places.getGeoDataClient(this.getActivity(),null);

        return homeView;
    }

    private void retrievePlace(){
        newPlaceList =  placeResponse.getPlace();
        placeText.setText(newPlaceList.get(counter).getName());
        //placeRating.setText("Rating: " + newPlaceList.get(counter).getRating() + "/5");
        scaleRatingBar.setRating((float)newPlaceList.get(counter).getRating());
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
                if(photoMetadataBuffer.getCount() > 0) {
                    PlacePhotoMetadata photoMetadata = photoMetadataBuffer.get(0);

                    // Get the attribution text.
                    CharSequence attribution = photoMetadata.getAttributions();
                    //                    // Get a full-size bitmap for the photo.
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
            }
        });

        counter++;
    }


    public void initPlaces(){
        Log.e("loadAdapter", "init");
        newPlaceList =  placeResponse.getPlace();
        Log.e("placesAPI", "" + newPlaceList.size());
        cardAdapter = new CardAdapter(getActivity().getApplicationContext(),0);
        for(int i=0; i<newPlaceList.size(); i++){
            Log.e("loadAdapter", " load Adapter id " + newPlaceList.get(i).getPlaceId());
            cardAdapter.add(newPlaceList.get(i));
        }

    }

    @Override
    public boolean swipeEnd(int i, float v) {
        return v>300;
    }

    @Override
    public boolean swipeStart(int i, float v) {
        return false;
    }

    @Override
    public boolean swipeContinue(int i, float v, float v1) {
        return false;
    }

    @Override
    public void discarded(int i, int i1) {

    }

    @Override
    public void topCardTapped() {

    }
}
