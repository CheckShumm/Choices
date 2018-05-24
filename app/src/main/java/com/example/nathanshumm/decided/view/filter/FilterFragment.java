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

    CardView cafeCard;
    TextView cafeTitle;
    ImageView cafeIcon;
    Filter cafeFilter;
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
        return filterView;
    }

    public void selectFilter(TextView title, ImageView icon, RelativeLayout layout){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cv_cafe:
                cafeFilter.onClick();
                placeResponse.setType("cafe");
                break;
        }
    }
}
