package com.alphabgammainc.nestfinder.Landlord;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.alphabgammainc.nestfinder.Classes.Address;
import com.alphabgammainc.nestfinder.R;

import java.util.ArrayList;

/**
 * Created by davidhuang on 2017-05-09.
 */

public class AdPostingPageThree extends Fragment implements View.OnClickListener, Pages{


    private View mView;
    private Activity mActivity;
    // probably put this into a resource file

   /* String[] countryList = {
            "Country",
            "Canada",
            "UNITED STATES"
    };*/

   /* String[] provinceList = {
            "Province",
            "Alberta (AB)",
            "British Columbia (BC)",
            "Manitoba (MB)",
            "New Brunswick (NB)",
            "Newfoundland and Labrador (NL)",
            "Northwest Territories (NT)",
            "Nova Scotia (NS)",
            "Nunavut (NU)",
            "Ontario (ON)",
            "Prince Edward Island (PE)",
            "Quebec (QC)",
            "Saskatchewan (SK)",
            "Yukon (YT)"
    };*/

   /* String [] statesList = {
            "States",
            "Alabama",
            "Alaska",
            "Arizona",
            "Arkansas",
            "California",
            "Colorado",
            "Connecticut",
            "Delaware",
            "Florida",
            "Georgia",
            "Hawaii",
            "Idaho",
            "Illinois",
            "Indiana",
            "Iowa",
            "Kansas",
            "Kentucky",
            "Louisiana",
            "Maine",
            "Maryland",
            "Massachusetts",
            "Michigan",
            "Minnesota",
            "Mississippi",
            "Missouri",
            "Montana ",
            "Nebraska",
            "Nevada",
            "New Hampshire",
            "New Jersey",
            "New Mexico",
            "New York",
            "North Carolina",
            "North Dakota",
            "Ohio",
            "Oklahoma",
            "Oregon",
            "Pennsylvania",
            "Rhode Island",
            "South Carolina",
            "South Dakota",
            "Tennessee",
            "Texas",
            "Utah",
            "Vermont",
            "Virginia",
            "Washington",
            "West Virginia",
            "Wisconsin",
            "Wyoming"
    };*/

    private Spinner adProvinceState;
    private EditText adCity;
    private Spinner adCountry;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ((AdPostingManager)mActivity).setTitle(R.string.ad_creation_three);

        mView = inflater.inflate(R.layout.ad_posting_page_three, container, false);


        adProvinceState = (Spinner)mView.findViewById(R.id.adProvince);
        adCountry = (Spinner)mView.findViewById(R.id.adCountry);

        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.country));

        adCountry.setAdapter(countryAdapter);

        adCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    ArrayAdapter<String> emptyAdapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_dropdown_item, new ArrayList<String>());
                    adProvinceState.setAdapter(emptyAdapter);

                } else if( position == 1) {
                    ArrayAdapter<String> provinceAdapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.province));
                    adProvinceState.setAdapter(provinceAdapter);
                } else if( position == 2){
                    ArrayAdapter<String> provinceAdapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.states));
                    adProvinceState.setAdapter(provinceAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adCity = (EditText)mView.findViewById(R.id.adCity);

        mView.findViewById(R.id.nextpage).setOnClickListener(this);
        return mView;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.nextpage){
            String adPostingCityString = adCity.getText().toString();
            int provpos = adProvinceState.getSelectedItemPosition();
            int countrypos = adCountry.getSelectedItemPosition();

            if(AdPostingValidation.checkForEmptyString(adPostingCityString) && provpos != 0 && countrypos!= 0) {
                // validate, store info then call back.
                // create some error feedback
                //Address address = ((AdPostingManager)mActivity).getAddress();
               // address.setCity();
                Address address = ((AdPostingManager)mActivity).getAddress();
                address.setCountry(adCountry.getSelectedItem().toString());
                address.setProvince(adProvinceState.getSelectedItem().toString());
                address.setCity(adPostingCityString);
             //   ((AdPostingManager)mActivity).setAddress(address);

                ((AdPostingManager)mActivity).nextView(3);
                /**
                 * @// TODO: 2017-05-16 Implement the existing Google services to the modified views
                 */
            }
        }
    }
}
