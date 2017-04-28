package com.alphabgammainc.nestfinder.FrontPage;

import android.app.Activity;
import android.app.Fragment;

import com.alphabgammainc.nestfinder.Classes.Locations;
import com.alphabgammainc.nestfinder.FirebaseConnection.DataBaseConnectionPresenter;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by davidhuang on 2017-04-25.
 */

public class MarkerManager {
    private Activity mActivity;
    private LoadMap callback;
    private ArrayList<Locations> locations;
    private FrontPageAdapter adapter;

    public MarkerManager(Activity activity, LoadMap callback, ArrayList<Locations> locations, FrontPageAdapter adapter){
        this.mActivity = activity;
        this.callback = callback;
        this.locations = locations;
        this.adapter = adapter;
    }

    /**
     * for now just fetch everything
     * @param lat
     * @param lon
     */
    public void fetchMarkers(Double lat, Double lon){


        DataBaseConnectionPresenter.getInstance().getDbReference().child("Locations").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            Locations locationObj = snapshot.getValue(Locations.class);
                            locations.add(locationObj);
                            adapter.notifyDataSetChanged();

                        }
                        callback.LoadMap(locations);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.print("Something went wrong");
                    }
                }
        );

    }
}