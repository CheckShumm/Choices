package com.example.nathanshumm.decided.view.home;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
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
import android.widget.Switch;
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
public class HomeFragment extends Fragment implements CardStack.CardEventListener, View.OnClickListener {

    private HomeViewModel homeViewModel;
    private PlaceResponse placeResponse;

    // buttons
    private ImageView likeButton;
    private ImageView dislikeButton;
    private ImageView infoButton;
    private int stackIndex = 0;

    private ScaleRatingBar scaleRatingBar; // Rating Bar
    private ArrayList<Place> newPlaceList = new ArrayList<Place>(); // Arraylist of places from places api

    // Card stack
    private CardStack cardStack;
    private CardAdapter cardAdapter;

    // clients
    private GeoDataClient geoDataClient;
    int counter = 0;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View homeView = inflater.inflate(R.layout.fragment_home, container, false);

        likeButton = (ImageView)homeView.findViewById(R.id.btn_like);
        dislikeButton = (ImageView)homeView.findViewById(R.id.btn_dislike);
        infoButton = (ImageView)homeView.findViewById(R.id.btn_info);

        cardStack = (CardStack)homeView.findViewById(R.id.card_stack);

        cardStack.setContentResource(R.layout.card_layout);
        cardStack.setStackMargin(20);

        cardStack.setListener(this);
        likeButton.setOnClickListener(this);
        dislikeButton.setOnClickListener(this);
        infoButton.setOnClickListener(this);

        initPlaces();


        geoDataClient = Places.getGeoDataClient(this.getActivity(),null);
        cardAdapter = new CardAdapter(getActivity().getApplicationContext(),0);
        cardStack.setAdapter(cardAdapter);
        setRetainInstance(true);
        return homeView;
    }


    public void initPlaces(){
        Log.e("npt", "initializing places, size: " + newPlaceList.size());
        for(int i=1; i<newPlaceList.size(); i++){
            if(newPlaceList.get(i).getPhoto() == null) {
                setPhoto(newPlaceList.get(i));
            }
            Log.e("npt", "name: " + newPlaceList.get(i).getName());
        }
    }

    public void fillCardStack(){
        for(int i=1; i<newPlaceList.size(); i++){
            cardAdapter.add(newPlaceList.get(i));
        }
    }

    public void getResponse(){
        placeResponse = new PlaceResponse();
        placeResponse.execute();
        newPlaceList =  placeResponse.getPlace();
    }

    public void setPhoto(final Place place){
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
                if(photoMetadataBuffer.getCount() > 0) {
                    final PlacePhotoMetadata photoMetadata = photoMetadataBuffer.get(0);

                    // Get the attribution text.
                    CharSequence attribution = photoMetadata.getAttributions();
                    // Get a full-size bitmap for the photo.

                    final Task<PlacePhotoResponse> photoResponse = geoDataClient.getScaledPhoto(photoMetadata,512,512);
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
        stackIndex++;
        Log.e("cardcounter", "count: " + cardAdapter.getCount());
        if(stackIndex == 5 ){
            placeResponse.executeNextResponse();
        }
        if (stackIndex == 13){
            newPlaceList =  placeResponse.getPlace();
            initPlaces();
        }
        if(stackIndex == 15){
            fillCardStack();
            stackIndex = -5;
        }
    }
    @Override
    public void topCardTapped() {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_like:
                cardStack.discardTop(1);
                break;
            case R.id.btn_dislike:
                cardStack.discardTop(0);
                break;
            case R.id.btn_info:
                fillCardStack();
                break;
        }
    }
}
