package com.example.nathanshumm.decided.viewmodel.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nathanshumm.decided.R;
import com.example.nathanshumm.decided.model.api.Place;
import com.example.nathanshumm.decided.model.api.PlaceResponse;
import com.example.nathanshumm.decided.view.main.MainActivity;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;


public class CardAdapter extends ArrayAdapter<Place>{

    private TextView placeText;
    private ImageView placeImage;
    private ScaleRatingBar scaleRatingBar;

    public CardAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.card_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        // Set Text
        holder.placeText.setText(getItem(position).getName());

        // Set Rating
        holder.scaleRatingBar.setRating((float)getItem(position).getRating());

        // Set Photo
        holder.placeImage.setImageBitmap(getItem(position).getPhoto());
        Log.e("photoRef", "display photo: " + getItem(position).getName());


        return convertView;
    }

    private static class ViewHolder {
        public TextView placeText;
        public ScaleRatingBar scaleRatingBar;
        public ImageView placeImage;

        public ViewHolder(View view) {
            this.placeText = (TextView) view.findViewById(R.id.tv_places);
            this.scaleRatingBar = (ScaleRatingBar) view.findViewById(R.id.simpleRatingBar);
            this.placeImage = (ImageView) view.findViewById(R.id.iv_places);
        }
    }

}
