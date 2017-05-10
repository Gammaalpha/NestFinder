package com.alphabgammainc.nestfinder.Landlord;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.alphabgammainc.nestfinder.R;

/**
 * Created by davidhuang on 2017-05-09.
 */

public class AdPostingPageTwo  extends Fragment implements View.OnClickListener, Pages{

    private EditText adAddress;
    private EditText adPostalCode;
    private View mView;
    private Activity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.ad_posting_page_two, container ,false);

        adAddress = (EditText) mView.findViewById(R.id.adAddress);
        adPostalCode = (EditText)mView.findViewById(R.id.adPostalCode);

        mView.findViewById(R.id.nextpage).setOnClickListener(this);

        return mView;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.nextpage){
            String adAddressString = adAddress.getText().toString();
            String adPostalCodeString = adPostalCode.getText().toString();

            if(AdPostingValidation.checkForEmptyString(adAddressString) &&
                    AdPostingValidation.validatePostalCode(adPostalCodeString)){
                // validate, store info then call back.
                // create some error feedback

            }
        }
    }
}
