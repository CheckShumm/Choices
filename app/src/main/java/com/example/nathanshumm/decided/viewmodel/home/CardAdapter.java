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
    private GeoDataClient geoDataClient;

    public CardAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        placeText = (TextView)convertView.findViewById(R.id.tv_places);
        placeImage = (ImageView)convertView.findViewById(R.id.iv_places);
        scaleRatingBar = convertView.findViewById(R.id.simpleRatingBar);
        geoDataClient = Places.getGeoDataClient(this.getContext(),null);

        // Set Text
        placeText.setText(getItem(position).getName());

        // Set Rating
        scaleRatingBar.setRating((float)getItem(position).getRating());

        // Get Photo
        final Task<PlacePhotoMetadataResponse> photoMetadataResponse = geoDataClient.
                getPlacePhotos(getItem(position).getPlaceId());
        final String placeID = getItem(position).getPlaceId();
        photoMetadataResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
                // Get the list of photos.
                PlacePhotoMetadataResponse photos = task.getResult();
                // Get the PlacePhotoMetadataBuffer (metadata for all of the photos).
                PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                // Get the first photo in the list.
                if(photoMetadataBuffer.getCount() > 0) {
                    PlacePhotoMetadata photoMetadata = photoMetadataBuffer.get(0);

                    // Get the attribution text.
                    CharSequence attribution = photoMetadata.getAttributions();
                    // Get a full-size bitmap for the photo.

                    Task<PlacePhotoResponse> photoResponse = geoDataClient.getPhoto(photoMetadata);
                    photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                        @Override
                        public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                            PlacePhotoResponse photo = task.getResult();
                            Bitmap bitmap = photo.getBitmap();
                            // set ImageView
                            Log.e("loadImage", "image id: "+ placeID);
                            placeImage.setImageBitmap(bitmap);
                        }
                    });
                }
            }
        });


        return convertView;
    }
}
