package com.alphabgammainc.nestfinder.DetailsPage;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alphabgammainc.nestfinder.Classes.Locations;
import com.alphabgammainc.nestfinder.MapsActivity;
import com.alphabgammainc.nestfinder.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


/**
 * Created by soutrikbarua on 2017-04-26.
 */

public class DetailsPage extends AppCompatActivity {

    private static final int NUM_PAGES =2;
    private ViewPager viewPager;
    private PagerAdapter mPagerAdapter;
    private ArrayList<DetailsPageInterface> pages;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_page);

        viewPager = (ViewPager) findViewById(R.id.pager);

        // later change this to dynamic by setting it to something relating to this posting.
        setTitle("Details");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle = getIntent().getExtras();
        // use this to populate details later.
        Locations locations = (Locations) bundle.getBundle("bundle").getSerializable("location");

        initPages();

        viewPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenPagerAdapter(getSupportFragmentManager(), pages);
        viewPager.setAdapter(mPagerAdapter);

    }

    public void initPages(){
        // add the pages
        pages = new ArrayList<>();
        pages.add(new PageOneDetails());
        pages.add(new PageTwoDetails());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}