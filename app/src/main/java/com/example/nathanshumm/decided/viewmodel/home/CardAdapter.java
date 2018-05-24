package com.example.nathanshumm.decided.viewmodel.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
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
        placeText = (TextView)convertView.findViewById(R.id.tv_places);
        placeImage = (ImageView)convertView.findViewById(R.id.iv_places);
        scaleRatingBar = convertView.findViewById(R.id.simpleRatingBar);

        // Set Text
        placeText.setText(getItem(position).getName());

        // Set Rating
        scaleRatingBar.setRating((float)getItem(position).getRating());

        // Set Photo
        placeImage.setImageBitmap(getItem(position).getPhoto());
        Log.e("photoRef", "display photo: " + getItem(position).getName());


        return convertView;
    }
}
