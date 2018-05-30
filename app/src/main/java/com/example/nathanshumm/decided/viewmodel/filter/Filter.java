package com.example.nathanshumm.decided.viewmodel.filter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nathanshumm.decided.R;
import com.example.nathanshumm.decided.model.api.PlaceResponse;

public class Filter {

    private TextView title;
    private ImageView icon;
    private CardView card;
    private boolean selected;
    private Context context;
    private PlaceResponse placeResponse;

    private String type;

    public Filter(Context context) {
        this.context = context;
    }

    public Filter(Context context, TextView title, ImageView icon, CardView card, String type) {
        this.title = title;
        this.icon = icon;
        this.card = card;
        this.selected = false;
        this.context = context;
        this.type = type;

        placeResponse = new PlaceResponse(this.context);
    }

    public void onClick(){
        if(!selected){
            select();
            selected = true;
        }else{
            deSelect();
            selected = false;
        }
    }

    public boolean isSelected() {
        return this.selected;
    }

    private void select(){
        this.title.setTextColor(context.getResources().getColor(R.color.colorWhite));
        this.icon.setColorFilter(context.getResources().getColor(R.color.colorWhite));
        this.card.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        placeResponse.setType(this.type);
    }

    private void deSelect(){
        this.title.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        this.icon.setColorFilter(context.getResources().getColor(R.color.colorPrimary));
        this.card.setCardBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        placeResponse.setType("null");
    }


    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }
}
