package com.alphabgammainc.nestfinder.DetailsPage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by davidhuang on 2017-05-20.
 */

public class ScreenPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<DetailsPageInterface> screens;

    public ScreenPagerAdapter(FragmentManager fm, ArrayList<DetailsPageInterface> screens) {
        super(fm);

        this.screens = screens;
    }

    @Override
    public Fragment getItem(int position) {
        return (Fragment) screens.get(position);
    }

    @Override
    public int getCount() {
        return screens.size();
    }
}
