package com.alphabgammainc.nestfinder.FrontPage;

import android.Manifest;
import android.app.Activity;

import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alphabgammainc.nestfinder.Classes.Locations;
import com.alphabgammainc.nestfinder.MapsActivity;
import com.alphabgammainc.nestfinder.R;
import com.alphabgammainc.nestfinder.Utilities.FabManager.FabManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collections;

import static android.content.ContentValues.TAG;

/**
 * Created by davidhuang on 2017-04-25.
 */

public class FrontPage extends Fragment implements OnMapReadyCallback, ManageMap,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private View mView;
    private Activity mActivity;
    private MapManager mapManager;
    private GoogleMap mMap;
    private RecyclerView listView;
    private ArrayList<Locations> locations;
    private FrontPageAdapter adapter;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FabManager fabManager;
    // Temporary until we actually start pulling data
    private AppBarLayout mAppBarLayout;
    private GoogleApiClient mGoogleApiClient;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;


    /**
     * Get the instance of activity here if we do it somewhere else then we might run into issues
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mapManager = new MapManager();

        if(checkPlayServices()){

        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.front_page, container, false);

        MapView mMapView = (MapView) mView.findViewById(R.id.map);

        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);

        FloatingActionButton generalTooling = (FloatingActionButton) mView.findViewById(R.id.generalTool);
        FloatingActionButton createListing = (FloatingActionButton) mView.findViewById(R.id.createListing);
        FloatingActionButton centerLocation = (FloatingActionButton) mView.findViewById(R.id.centerLocation);


        fabManager = FabManager.getInstance(generalTooling, createListing, centerLocation, mActivity); // calling get instance automically shows the fab
        fabManager.setListeners();
        fabManager._hideAllNonGeneralButtons();

        mAppBarLayout = (AppBarLayout) mView.findViewById(R.id.app_bar);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(AppBarLayout appBarLayout) {
                return false;
            }
        });
        params.setBehavior(behavior);

        swipeRefreshLayout = (SwipeRefreshLayout)mView.findViewById(R.id.swipe);
       // swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setEnabled(false);

        locations = new ArrayList<>();

        listView = (RecyclerView) mView.findViewById(R.id.rview);

        layoutManager = new LinearLayoutManager(mActivity);

        listView.setLayoutManager(layoutManager);

        locations = ((MapsActivity)mActivity).getLocationses();

        adapter = new FrontPageAdapter(mActivity, locations, this, listView);

        listView.setAdapter(adapter);


        return mView;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //final MarkerManager markerManager = new MarkerManager(mActivity , this, locations, adapter);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            MapStyleOptions style =MapStyleOptions.loadRawResourceStyle(this.getContext(),R.raw.mapstyle);
            mMap.setMapStyle(style);
        }

      //  LatLng location = new LatLng(43.887501 , -79.428406);
       // markerManager.fetchMarkers(43.887501 , -79.428406);

        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.887501 , -79.428406), 10);
        mMap.animateCamera(cameraUpdate);
        LoadMap(locations);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Locations location = mapManager.getListMarker(marker);

                // swap the two location objects in the array list so that it can be displayed at the top of the list
                // when the user selects a marker.
                Collections.swap(locations, 0, locations.indexOf(location));
                adapter.notifyDataSetChanged();

                return false;
            }
        });

    }

    /**
     * callback once we fetch all the locations on the given marker
     * @param locations
     */
    @Override
    public void LoadMap(ArrayList<Locations> locations) {
        //swipeRefreshLayout.setEnabled(false);
        // Add a marker in Sydney and move the camera
        for(Locations location : locations){
            LatLng position = new LatLng(location.getLat(),location.getLon());
            Marker marker = mMap.addMarker(new MarkerOptions().position(position).title(location.getAddress()));
            mapManager.addMapMarker(marker, location);
        }
    //    swipeRefreshLayout.setRefreshing(false);
    }


    /**
     * we probably dont need this function but just leave it here for now.
     * right now it is used for callback function for the adapter since we cann set a overall list listener
     *
     * @param location the key object that is used to search the map for the corresponding value.
     */
    @Override
    public void setMarkerFocusCallback(Locations location) {
        Marker marker = mapManager.getMapmarker(location);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(),14));
        marker.showInfoWindow();
    }


    /**
     * Helper functions for getting the users locations
     * @return
     */
    private boolean checkPlayServices(){
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(mActivity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(mActivity, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                mActivity.finish();
            }
            return false;
        }
        return true;
    }
    /**
     * Creating google api client object
     * */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
