package com.alphabgammainc.nestfinder.DetailsPage;

import com.alphabgammainc.nestfinder.GoogleService.GetNearbyPlacesData;
import com.google.android.gms.maps.GoogleMap;

import java.util.Objects;

import static com.alphabgammainc.nestfinder.R.string.google_maps_key;

/**
 * Created by soutrikbarua on 2017-05-21.
 */

public class NearbyPlaces {
    private int PROXIMITY_RADIUS = 5000;
    private String URL;
    private String store;



    private String getURL(double latitude,double longitude,String nearbyPlaces){
        StringBuilder googlePlacesURL =
                new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesURL.append("location=" +latitude+","+longitude);
        googlePlacesURL.append("&radius=" +PROXIMITY_RADIUS );
        googlePlacesURL.append("&type=" + nearbyPlaces);
        googlePlacesURL.append("&sensor=true");
        googlePlacesURL.append("&key=AIzaSyCb14xML7qnQ4AXGQ5ymUzgQwSmvcGa3IE");

        return (googlePlacesURL.toString());
    }

    public void getPlaces(double latitude, double longitude,String store, GoogleMap googleMap){
        URL= getURL(latitude,longitude,store);
        Object [] dataTransfer = new Object[2];
        dataTransfer[0] =googleMap;
        dataTransfer[1] =URL;
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        getNearbyPlacesData.execute(dataTransfer);
        //call the get GetNearbyPlacesData class

    }


}
