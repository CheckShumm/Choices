package com.example.nathanshumm.decided.viewmodel.filter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nathanshumm.decided.R;

public class Filter {

    private TextView title;
    private ImageView icon;
    private CardView card;
    private boolean selected;
    private Context context;

    public Filter(Context context) {
        this.context = context;
    }

    public Filter(Context context, TextView title, ImageView icon, CardView card) {
        this.title = title;
        this.icon = icon;
        this.card = card;
        this.selected = false;
        this.context = context;
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

    private void select(){
        this.title.setTextColor(context.getResources().getColor(R.color.colorWhite));
        this.icon.setColorFilter(context.getResources().getColor(R.color.colorWhite));
        this.card.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
    }

    private void deSelect(){
        this.title.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        this.icon.setColorFilter(context.getResources().getColor(R.color.colorPrimary));
        this.card.setCardBackgroundColor(context.getResources().getColor(R.color.colorWhite));
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

}
