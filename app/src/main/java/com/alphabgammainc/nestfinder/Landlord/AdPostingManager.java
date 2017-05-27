package com.alphabgammainc.nestfinder.Landlord;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.alphabgammainc.nestfinder.Classes.Address;
import com.alphabgammainc.nestfinder.Classes.Locations;
import com.alphabgammainc.nestfinder.FirebaseConnection.DataBaseConnectionPresenter;
import com.alphabgammainc.nestfinder.GoogleService.ConversionCallback;
import com.alphabgammainc.nestfinder.GoogleService.LatLongObj;
import com.alphabgammainc.nestfinder.LoadingDialog.ProgressBarManager;
import com.alphabgammainc.nestfinder.MapsActivity;
import com.alphabgammainc.nestfinder.R;
import com.alphabgammainc.nestfinder.Utilities.ImageManager;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by davidhuang on 2017-05-09.
 */

public class AdPostingManager extends AppCompatActivity implements ConversionCallback, CompletionCallback {

    private Locations location;
    private Address address;
    private ProgressBarManager progressBarManager;
    // pointers to images here because if we failed to get the lat and lon then the uploaded image would cause issues
    private ArrayList<Bitmap> images;

    private AdPostingPageOne adPostingPageOne = new AdPostingPageOne();
    private AdPostingPageTwo adPostingPageTwo = new AdPostingPageTwo();
    private AdPostingPageThree adPostingPageThree = new AdPostingPageThree();
    private AdPostingPageFour adPostingPageFour = new AdPostingPageFour();


    private ArrayList<Pages> pages = new ArrayList<>();

    public AdPostingManager() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ad_posting_container);

        pages.add(adPostingPageOne);
        pages.add(adPostingPageTwo);
        pages.add(adPostingPageThree);
        pages.add(adPostingPageFour);

        location = new Locations();
        address = new Address();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        ft.add(R.id.content_frame, (android.support.v4.app.Fragment) pages.get(0),"fragment").commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);

        /*
         * not in use cases
         */
        MenuItem item = menu.findItem(R.id.action_settings);
        item.setVisible(false);

        return true;
    }

    /**
     * used to modify object in different fragments
     * @return
     */
    public Locations getlocation(){
        return location;
    }

    public Address getAddress(){return address;}

    public void setLocation(Locations location){
        this.location = location;
    }

    public void setAddress(Address address){
        this.address = address;
    }

    public ArrayList<Pages> getPages(){return pages;}

    public void setProgressBar(ProgressBarManager progressBar){
        this.progressBarManager = progressBar;
    }

    /**
     * change view
     */
    public void nextView(int position){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        ft.replace(R.id.content_frame, (android.support.v4.app.Fragment) pages.get(position),"fragment").addToBackStack("ad"+position).commit();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details, menu);
    }

    @Override
    public void setlatlongobj(LatLongObj latLongObj) {
        location.setLat(latLongObj.getlat());
        location.setLon(latLongObj.getlon());

        /**
         * for now we can do this but we must make it so that once its successfully uploaded then we can
         * actually store the reference under the rent ad or else we will run into all kinds of issues.
         */
        for(Bitmap bitmap: images){
            DatabaseReference reference = DataBaseConnectionPresenter.getInstance(this).getDbReference().child("temp").push();
            ImageManager.uploadImageWithBitMap(bitmap,reference.getKey());
            location.pushBackImage(reference.getKey());
        }
        upLoadAd();
    }

    private void upLoadAd(){
        try {
            DatabaseReference reference = DataBaseConnectionPresenter.getInstance(this).getDbReference().child("Locations").push();
            reference.setValue(location);
            onCompleteUpload(true);
        }catch (DatabaseException e){
            e.printStackTrace();
            onCompleteUpload(false);
        }

    }

    @Override
    public void onCompleteUpload(boolean success) {
        progressBarManager.HideProgressDialog();
        if(success) {
            for (Pages page : getPages()) {
                ((android.support.v4.app.Fragment)page).onDestroy();
            }
            // exit
            //((MapsActivity)getParent()).showSnackBar();

            finish();
        }
        else Toast.makeText(this, "an error has occured while uploading", Toast.LENGTH_LONG).show();
    }

    public void setImages(ArrayList<Bitmap> images) {
        this.images = images;
    }
}
