package com.alphabgammainc.nestfinder.DetailsPage;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.alphabgammainc.nestfinder.R;
import com.alphabgammainc.nestfinder.Utilities.ImageManager;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by davidhuang on 2017-05-20.
 */

public class DetailsPageTwoAdapter extends BaseAdapter {

    private ArrayList<String> imgReferences;
    private Activity mActivity;
    private ImageView imageView;

    public DetailsPageTwoAdapter(Activity activity, ArrayList<String> imgRef){
        this.mActivity = activity;
        this.imgReferences = imgRef;
    }

    @Override
    public int getCount() {
        return 5;
        //return imgReferences.size();
    }

    @Override
    public Object getItem(int position) {
        return imgReferences.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.details_page_two_component, null);

        // performance of this is really bad right not and lags the app but once we actually pul lreal data it wont lag.
        imageView = (ImageView) row.findViewById(R.id.image);

        /*
            // use this when we actually are pulling images from the db
             ImageManager.downloadImage(mActivity, imageView, imgReferences.get(position));
        */
        return row;
    }
}
