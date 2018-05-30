package com.example.nathanshumm.decided.view.filter;


import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nathanshumm.decided.R;
import com.example.nathanshumm.decided.model.api.PlaceResponse;
import com.example.nathanshumm.decided.view.main.MainActivity;
import com.example.nathanshumm.decided.viewmodel.filter.Filter;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends Fragment implements View.OnClickListener{


    private Filter cafeFilter;
    private Filter restaurantFilter;
    private Filter clubFilter;
    private Filter barFilter;

    private String currentType = "none";
    private Boolean newFilter = true;
    private ArrayList<Filter> filterList = new ArrayList<>();
    private PlaceResponse placeResponse;
    public FilterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View filterView = inflater.inflate(R.layout.fragment_filter, container, false);
        Log.e("context", "filter context: " + this.getContext());
        placeResponse = new PlaceResponse(this.getContext());

        // cafe Filter
        TextView cafeTitle = (TextView)filterView.findViewById(R.id.tv_cafe);
        ImageView cafeIcon = (ImageView)filterView.findViewById(R.id.iv_cafe);
        CardView cafeCard = (CardView)filterView.findViewById(R.id.cv_cafe);
        cafeFilter = new Filter(this.getContext(),cafeTitle, cafeIcon, cafeCard, "cafe");
        filterList.add(cafeFilter);
        cafeCard.setOnClickListener(this);

        // restaurant filter
        TextView restaurantTitle = (TextView)filterView.findViewById(R.id.tv_food);
        ImageView restaurantIcon = (ImageView)filterView.findViewById(R.id.iv_food);
        CardView restaurantCard = (CardView)filterView.findViewById(R.id.cv_food);
        restaurantFilter = new Filter(this.getContext(),restaurantTitle, restaurantIcon, restaurantCard, "restaurant");
        filterList.add(restaurantFilter);
        restaurantCard.setOnClickListener(this);

        // club filter
        TextView clubTitle = (TextView)filterView.findViewById(R.id.tv_club);
        ImageView clubIcon = (ImageView)filterView.findViewById(R.id.iv_club);
        CardView clubCard = (CardView)filterView.findViewById(R.id.cv_club);
        clubFilter = new Filter(this.getContext(),clubTitle, clubIcon, clubCard, "night_club");
        filterList.add(clubFilter);
        clubCard.setOnClickListener(this);

        // club filter
        TextView barTitle = (TextView)filterView.findViewById(R.id.tv_bar);
        ImageView barIcon = (ImageView)filterView.findViewById(R.id.iv_bar);
        CardView barCard = (CardView)filterView.findViewById(R.id.cv_bar);
        barFilter = new Filter(this.getContext(),barTitle, barIcon, barCard, "bar");
        filterList.add(barFilter);
        barCard.setOnClickListener(this);

        return filterView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cv_cafe:
                cafeFilter.onClick();
                newFilterSelected(cafeFilter.getType());
                break;
            case R.id.cv_food:
                restaurantFilter.onClick();
                newFilterSelected(restaurantFilter.getType());
                break;
            case R.id.cv_club:
                clubFilter.onClick();
                newFilterSelected(clubFilter.getType());
                break;
            case R.id.cv_bar:
                barFilter.onClick();
                newFilterSelected(barFilter.getType());
                break;
        }
    }

    private void deSelectAll(String type){
        for(int i = 0; i < filterList.size(); i++) {
           if(filterList.get(i).isSelected() && !filterList.get(i).getType().equals(type))
               filterList.get(i).onClick();
        }
    }

    public void newFilterSelected(String type){
        deSelectAll(type);
        if(type.equals(currentType) || currentType.equals("none")) {
            currentType = type;
            newFilter = false;
            Log.d("newFilter", "setfilter false");
        } else {
            newFilter = true;
            currentType = type;
            Log.d("newFilter", "setfilter true");
        }
    }

    public String getCurrentType(){
        return currentType;
    }

}
