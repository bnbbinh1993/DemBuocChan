package com.bnb.binh.dembuocchan.models;

public class PolygonModel {
    private long lat;
    private long lng;

    public PolygonModel() {
    }

    public PolygonModel(long lat, long lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public long getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public long getLng() {
        return lng;
    }

    public void setLng(long lng) {
        this.lng = lng;
    }
}
