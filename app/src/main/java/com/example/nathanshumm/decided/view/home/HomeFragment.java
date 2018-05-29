package com.example.nathanshumm.decided.view.home;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.SwipeDirection;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener, Serializable{

    private PlaceResponse placeResponse;

    // buttons
    private ImageView likeButton;
    private ImageView dislikeButton;
    private ImageView infoButton;

    private ArrayList<Place> newPlaceList = new ArrayList<Place>(); // Arraylist of places from places api

    // Card stack
    private CardStackView cardStackView;
    private CardAdapter cardAdapter;
    private ProgressBar progressBar;

    // Bundle
    Bundle bundle = new Bundle();

    // Favorites List
    ArrayList<Place> favoritesList = new ArrayList<>();
    Place currPlace;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View homeView = inflater.inflate(R.layout.fragment_home, container, false);
        getResponse();
        setup(homeView);

        likeButton = (ImageView)homeView.findViewById(R.id.btn_like);
        dislikeButton = (ImageView)homeView.findViewById(R.id.btn_dislike);
        infoButton = (ImageView)homeView.findViewById(R.id.btn_info);

        likeButton.setOnClickListener(this);
        dislikeButton.setOnClickListener(this);
        infoButton.setOnClickListener(this);


        return homeView;
    }

    @Override
    public void onResume() {
        Log.d("resume","on resume");
        super.onResume();
    }

    public void paginate(){
        //cardStackView.setPaginationReserved();
        newPlaceList =  placeResponse.getPlace();
        for(int i=1; i<newPlaceList.size(); i++){
            cardAdapter.add(newPlaceList.get(i));
            Log.d("CardStackView", "Paginate cardadapter add: " + newPlaceList.get(i).getName());
        }
        //cardAdapter.notifyDataSetChanged();
        Log.d("paginate", "adapter size: " + cardAdapter.getCount());
    }


    public void getResponse(){
        Log.e("context", "home fragment context = " + this.getContext());
        placeResponse = new PlaceResponse(this.getContext());
        placeResponse.execute();
    }

    public void setup(View v){
        progressBar = (ProgressBar)v.findViewById(R.id.card_stack_progress_bar);

        cardStackView = (CardStackView)v.findViewById(R.id.card_stack);
        cardStackView.setCardEventListener(new CardStackView.CardEventListener() {
            @Override
            public void onCardDragging(float percentX, float percentY) {
               // Log.d("CardStackView", "onCardDragging");
            }

            @Override
            public void onCardSwiped(SwipeDirection direction) {
                if (cardStackView.getTopIndex() == cardAdapter.getCount() - 10) {
                    Log.d("CardStackView", "Paginate: " + cardStackView.getTopIndex() + "  count: " + cardAdapter.getCount());
                    Log.d("CardStackView", "get next response");
                    placeResponse.executeNextResponse();
                }

                if (cardStackView.getTopIndex() == cardAdapter.getCount() - 5) {
                    Log.d("CardStackView", "Paginate: " + cardStackView.getTopIndex() + "  count: " + cardAdapter.getCount());
                    paginate();
                }

                if(direction == SwipeDirection.Right){
                }

                if(direction == SwipeDirection.Left){
                    favoritesList.add(currPlace);
                    Log.d("CardStackView", "Liked: " + currPlace.getName());
                }
                if(!cardAdapter.isEmpty())
                    currPlace = cardAdapter.getItem(cardStackView.getTopIndex());
            }

            @Override
            public void onCardReversed() {

            }

            @Override
            public void onCardMovedToOrigin() {

            }

            @Override
            public void onCardClicked(int index) {

            }
        });
    }

    public void swipeLeft() {
        View target = cardStackView.getTopView();
        View targetOverlay = cardStackView.getTopView().getOverlayContainer();

        ValueAnimator rotation = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("rotation", -10f));
        rotation.setDuration(200);
        ValueAnimator translateX = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationX", 0f, -2000f));
        ValueAnimator translateY = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationY", 0f, 500f));
        translateX.setStartDelay(100);
        translateY.setStartDelay(100);
        translateX.setDuration(500);
        translateY.setDuration(500);
        AnimatorSet cardAnimationSet = new AnimatorSet();
        cardAnimationSet.playTogether(rotation, translateX, translateY);

        ObjectAnimator overlayAnimator = ObjectAnimator.ofFloat(targetOverlay, "alpha", 0f, 1f);
        overlayAnimator.setDuration(200);
        AnimatorSet overlayAnimationSet = new AnimatorSet();
        overlayAnimationSet.playTogether(overlayAnimator);

        cardStackView.swipe(SwipeDirection.Left, cardAnimationSet, overlayAnimationSet);
    }

    public void swipeRight() {
        View target = cardStackView.getTopView();
        View targetOverlay = cardStackView.getTopView().getOverlayContainer();

        ValueAnimator rotation = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("rotation", 10f));
        rotation.setDuration(200);
        ValueAnimator translateX = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationX", 0f, 2000f));
        ValueAnimator translateY = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationY", 0f, 500f));
        translateX.setStartDelay(100);
        translateY.setStartDelay(100);
        translateX.setDuration(500);
        translateY.setDuration(500);
        AnimatorSet cardAnimationSet = new AnimatorSet();
        cardAnimationSet.playTogether(rotation, translateX, translateY);

        ObjectAnimator overlayAnimator = ObjectAnimator.ofFloat(targetOverlay, "alpha", 0f, 1f);
        overlayAnimator.setDuration(200);
        AnimatorSet overlayAnimationSet = new AnimatorSet();
        overlayAnimationSet.playTogether(overlayAnimator);

        cardStackView.swipe(SwipeDirection.Right, cardAnimationSet, overlayAnimationSet);
    }

    public void reload() {
        cardStackView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cardAdapter = new CardAdapter(getActivity().getApplicationContext(),0);
                newPlaceList =  placeResponse.getPlace();
                for(int i=1; i<newPlaceList.size(); i++){
                    cardAdapter.add(newPlaceList.get(i));
                    Log.d("CardStackView", "reload: " + newPlaceList.get(i).getName());
                }
                if(!cardAdapter.isEmpty())
                    currPlace = cardAdapter.getItem(cardStackView.getTopIndex());
                cardStackView.setAdapter(cardAdapter);
                cardStackView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        }, 4000);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_like:
                swipeRight();
                break;
            case R.id.btn_dislike:
                swipeLeft();
                break;
            case R.id.btn_info:
                reload();
                break;
        }
    }

    public ArrayList<Place> getFavoritesList(){
        return this.favoritesList;
    }
}
