package com.example.nathanshumm.decided.view.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.nathanshumm.decided.R;
import com.example.nathanshumm.decided.model.api.Place;
import com.example.nathanshumm.decided.model.api.PlaceResponse;
import com.example.nathanshumm.decided.viewmodel.home.HomeViewModel;

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
    private ArrayList<Place> newPlaceList = new ArrayList<Place>();
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
        placeResponse = new PlaceResponse();
        testButton.setOnClickListener(this);
        return homeView;
    }

    private void retrievePlace(){
        newPlaceList =  placeResponse.getPlace();
        Log.e("placesAPI", "" + newPlaceList.size());
        placeText.setText(newPlaceList.get(counter).getName());
        counter++;

    }

    @Override
    public void onClick(View v) {
        retrievePlace();
    }
}
