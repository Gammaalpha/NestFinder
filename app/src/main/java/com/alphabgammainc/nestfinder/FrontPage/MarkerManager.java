package com.alphabgammainc.nestfinder.FrontPage;

import android.app.Activity;
import android.app.Fragment;

import com.alphabgammainc.nestfinder.Classes.Locations;
import com.alphabgammainc.nestfinder.FirebaseConnection.DataBaseConnectionPresenter;
import com.alphabgammainc.nestfinder.Loading.LoadingOnComplete;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by davidhuang on 2017-04-25.
 */

public class MarkerManager {
    private Activity mActivity;
    private ManageMap callback;
    private ArrayList<Locations> locations;
    private FrontPageAdapter adapter;

    public MarkerManager(Activity activity, ManageMap callback, ArrayList<Locations> locations, FrontPageAdapter adapter){
        this.mActivity = activity;
        this.callback = callback;
        this.locations = locations;
        this.adapter = adapter;
    }

    public MarkerManager(Activity activity){
        mActivity = activity;
    }

    public void initLoad(final LoadingOnComplete onComplete) {
        DatabaseReference databaseReference = DataBaseConnectionPresenter.getInstance(mActivity).getDbReference();

        if (databaseReference != null) {
            databaseReference.child("Locations").addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            ArrayList<Locations> locationses = new ArrayList<Locations>();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Locations locationObj = snapshot.getValue(Locations.class);
                                locationses.add(locationObj);
                            }
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            onComplete.callBack(locationses);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.print("Something went wrong");
                            onComplete.callBack(null);
                        }
                    }
            );
        }
    }



    /**
     * for now just fetch everything
     * @param lat
     * @param lon
     */
    public void fetchMarkers(Double lat, Double lon){

        DatabaseReference databaseReference = DataBaseConnectionPresenter.getInstance(mActivity).getDbReference();

        if(databaseReference != null) {
        databaseReference.child("Locations").addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Locations locationObj = snapshot.getValue(Locations.class);
                                locations.add(locationObj);
                            }
                            adapter.notifyDataSetChanged();

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
}
