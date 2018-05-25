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

    private PlaceResponse placeResponse;

    // buttons
    private ImageView likeButton;
    private ImageView dislikeButton;
    private ImageView infoButton;
    private int stackIndex = 0;

    private ArrayList<Place> newPlaceList = new ArrayList<Place>(); // Arraylist of places from places api

    // Card stack
    private CardStack cardStack;
    private CardAdapter cardAdapter;

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
        getResponse();

        cardAdapter = new CardAdapter(getActivity().getApplicationContext(),0);
        cardStack.setAdapter(cardAdapter);

        return homeView;
    }


    public void fillCardStack(){
        for(int i=1; i<newPlaceList.size(); i++){
            cardAdapter.add(newPlaceList.get(i));
        }
    }

    public void getResponse(){
        Log.e("context", "home fragment context = " + this.getContext());
        placeResponse = new PlaceResponse(this.getContext());
        placeResponse.execute();
        newPlaceList =  placeResponse.getPlace();
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
