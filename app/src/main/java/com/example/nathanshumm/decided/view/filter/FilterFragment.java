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


/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends Fragment implements View.OnClickListener{


    Filter cafeFilter;
    Filter restaurantFilter;
    Filter clubFilter;
    Filter barFilter;
    private PlaceResponse placeResponse = new PlaceResponse();
    public FilterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View filterView = inflater.inflate(R.layout.fragment_filter, container, false);

        // cafe Filter
        TextView cafeTitle = (TextView)filterView.findViewById(R.id.tv_cafe);
        ImageView cafeIcon = (ImageView)filterView.findViewById(R.id.iv_cafe);
        CardView cafeCard = (CardView)filterView.findViewById(R.id.cv_cafe);
        cafeFilter = new Filter(this.getContext(),cafeTitle, cafeIcon, cafeCard);
        cafeCard.setOnClickListener(this);

        // restaurant filter
        TextView restaurantTitle = (TextView)filterView.findViewById(R.id.tv_food);
        ImageView restaurantIcon = (ImageView)filterView.findViewById(R.id.iv_food);
        CardView restaurantCard = (CardView)filterView.findViewById(R.id.cv_food);
        restaurantFilter = new Filter(this.getContext(),restaurantTitle, restaurantIcon, restaurantCard);
        restaurantCard.setOnClickListener(this);

        // club filter
        TextView clubTitle = (TextView)filterView.findViewById(R.id.tv_club);
        ImageView clubIcon = (ImageView)filterView.findViewById(R.id.iv_club);
        CardView clubCard = (CardView)filterView.findViewById(R.id.cv_club);
        clubFilter = new Filter(this.getContext(),clubTitle, clubIcon, clubCard);
        clubCard.setOnClickListener(this);

        // club filter
        TextView barTitle = (TextView)filterView.findViewById(R.id.tv_bar);
        ImageView barIcon = (ImageView)filterView.findViewById(R.id.iv_bar);
        CardView barCard = (CardView)filterView.findViewById(R.id.cv_bar);
        barFilter = new Filter(this.getContext(),barTitle, barIcon, barCard);
        barCard.setOnClickListener(this);

        return filterView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cv_cafe:
                cafeFilter.onClick();
                placeResponse.setType("cafe");
                break;
            case R.id.cv_food:
                restaurantFilter.onClick();
                placeResponse.setType("restaurant");
                break;
            case R.id.cv_club:
                clubFilter.onClick();
                placeResponse.setType("club");
                break;
            case R.id.cv_bar:
                barFilter.onClick();
                placeResponse.setType("bar");
                break;
        }
    }
}
