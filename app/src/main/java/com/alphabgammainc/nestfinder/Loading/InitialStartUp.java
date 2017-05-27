package com.alphabgammainc.nestfinder.Loading;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alphabgammainc.nestfinder.Classes.Locations;
import com.alphabgammainc.nestfinder.FrontPage.FrontPage;
import com.alphabgammainc.nestfinder.FrontPage.MarkerManager;
import com.alphabgammainc.nestfinder.MapsActivity;
import com.alphabgammainc.nestfinder.R;

import java.util.ArrayList;

/**
 * Created by davidhuang on 2017-05-27.
 */

public class InitialStartUp extends Fragment implements LoadingOnComplete{

    private Activity mActivity;
    private View mView;
    private MarkerManager markerManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.init_loading_page, container ,false);
        markerManager = new MarkerManager(mActivity);
        ((MapsActivity)mActivity).getSupportActionBar().hide();

        markerManager.initLoad(this);


        return mView;

    }

    @Override
    public void callBack(ArrayList<Locations> locationses) {
        if(locationses!=null)((MapsActivity)mActivity).showFrontPage(locationses);

        else Toast.makeText(mActivity, "Something went wrong", Toast.LENGTH_LONG).show();
    }
}
