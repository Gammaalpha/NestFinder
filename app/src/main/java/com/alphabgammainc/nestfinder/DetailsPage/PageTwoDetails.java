package com.alphabgammainc.nestfinder.DetailsPage;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alphabgammainc.nestfinder.R;

import java.util.ArrayList;

/**
 * Created by davidhuang on 2017-05-20.
 */

public class PageTwoDetails extends Fragment implements DetailsPageInterface{

    private View mView;
    private Activity mActivity;
    private ArrayList<String> imgRef;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.details_page_two, container , false);

        ListView listView = (ListView)mView.findViewById(R.id.imageListview);



        DetailsPageTwoAdapter detailsPageTwoAdapter = new DetailsPageTwoAdapter(mActivity, imgRef);
        listView.setAdapter(detailsPageTwoAdapter);


        return mView;
    }
}
