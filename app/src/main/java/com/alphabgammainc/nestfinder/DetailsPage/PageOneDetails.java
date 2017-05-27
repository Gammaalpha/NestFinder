package com.alphabgammainc.nestfinder.DetailsPage;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alphabgammainc.nestfinder.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;

/**
 * Created by davidhuang on 2017-05-20.
 */

public class PageOneDetails extends Fragment implements OnMapReadyCallback, DetailsPageInterface {

    private Activity mActivity;
    private View mView;
    private NearbyPlaces myPlaces;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.details_page_one, container ,false);

        MapView mMapView = (MapView) mView.findViewById(R.id.map);

        mMapView.onCreate(savedInstanceState);



        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(mActivity.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        mMapView.getMapAsync(this);



        return mView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.887501 , -79.428406), 15);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            MapStyleOptions style =MapStyleOptions.loadRawResourceStyle(this.getContext(),R.raw.mapstyle);
            googleMap.setMapStyle(style);
        }
        myPlaces = new NearbyPlaces();
        /**
         * this is where you pass the latitude and longitude along with the search type
         */
        myPlaces.getPlaces(43.887501 , -79.428406,"gas_station",googleMap);
        googleMap.animateCamera(cameraUpdate);
    }

    /**
     * The methods below are used to implement all the listed places
     */


}
