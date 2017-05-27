package com.alphabgammainc.nestfinder.GoogleService;

/**
 * Created by davidhuang on 2017-05-21.
 */

public class LatLongObj {
    private double lat;
    private double lon;

    public LatLongObj(double lat ,double lon){
        this.lat = lat;
        this.lon = lon;
    }

    public double getlat(){
        return lat;
    }

    public double getlon(){
        return lon;
    }
}
