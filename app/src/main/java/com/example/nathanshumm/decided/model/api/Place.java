package com.example.nathanshumm.decided.model.api;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

public class Place {

    private double lat;
    private double lng;
    private String name;
    private String vicinity;
    private LatLng latLng;
    private String placeId;
    private double rating;


    private double priceLevel;
    private Bitmap photo;

    public Place() {
    }


    public Place(double lat, double lng, String name, String vicinity, LatLng latLng, String placeId, double rating, double priceLevel) {
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.vicinity = vicinity;
        this.latLng = latLng;
        this.placeId = placeId;
        this.rating = rating;
        this.priceLevel = priceLevel;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public double getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(double priceLevel) {
        this.priceLevel = priceLevel;
    }
}
