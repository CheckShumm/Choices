package com.example.nathanshumm.decided.viewmodel.home;

import android.arch.lifecycle.ViewModel;


import com.example.nathanshumm.decided.model.api.Place;
import com.example.nathanshumm.decided.model.api.PlaceResponse;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel{

    private PlaceResponse placeResponse;
    private ArrayList<Place> newPlaceList = new ArrayList<Place>();

    public HomeViewModel() {
        this.placeResponse = new PlaceResponse();
        this.newPlaceList = placeResponse.getPlace();
    }

    public void getPhotos(){

    }
}
