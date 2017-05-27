package com.alphabgammainc.nestfinder.Landlord;

import android.Manifest;
import android.app.Activity;

import android.content.ClipData;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.alphabgammainc.nestfinder.GoogleService.ConversionCallback;
import com.alphabgammainc.nestfinder.GoogleService.LatLong;
import com.alphabgammainc.nestfinder.GoogleService.LatLongObj;
import com.alphabgammainc.nestfinder.LoadingDialog.ProgressBarManager;
import com.alphabgammainc.nestfinder.R;
import com.alphabgammainc.nestfinder.Utilities.PermissionsManager;


import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by soutrikbarua on 2017-05-16.
 */

public class AdPostingPageFour extends Fragment implements Pages,View.OnClickListener,PostingCallback{

    private View mView;
    private Activity mActivity;
    private ImageView mCamera;
    private ImageView myImage;
    static final int REQUEST_IMAGE_CAPTURE =1;
    static final int REQUEST_IMAGE_GALLERY =2;
    private HorizontalGridView horizontalGridView;
    private ArrayList<Bitmap> mImage;

    /**
     * @myCamera : this part lets the user access their device camera
     */
    private ImageView.OnClickListener cameraListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (myCameraIntent.resolveActivity(mActivity.getPackageManager()) != null) {

                if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(mActivity,
                            new String[]{Manifest.permission.CAMERA},
                            0);
                }
                else{
                    startActivityForResult(myCameraIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        }
    };

    /**
     * @myImage : this part lets the user access the devices gallery
     */
    private ImageView.OnClickListener galleryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(PermissionsManager.checkReadExternalPermission(mActivity)){
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
            }
            else{
                PermissionsManager.requestForSpecificPermission(mActivity);
            }
        }
    };

    private GridElementAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ((AdPostingManager)mActivity).setTitle(R.string.ad_creation_four);

        mView = inflater.inflate(R.layout.ad_posting_page_four, container ,false);
        mCamera=(ImageView)mView.findViewById(R.id.ivCamera);
        myImage=(ImageView)mView.findViewById(R.id.ivGallery);
        //myUpload=(ImageView)mView.findViewById(R.id.upload);
        mImage = new ArrayList<Bitmap>();

        mCamera.setOnClickListener(cameraListener);

        myImage.setOnClickListener(galleryListener);

        Button upload = (Button)mView.findViewById(R.id.upload);
        upload.setOnClickListener(this);

        horizontalGridView = (HorizontalGridView) mView.findViewById(R.id.gridView);
        mAdapter = new GridElementAdapter(mActivity,mImage);
        horizontalGridView.setAdapter(mAdapter);
        return mView;
    }

    /**
     * This part saves the image taken by the camera to an image view
     * @// TODO: 2017-05-16 - Make an image view to display the picture taken by camera
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImage.add(imageBitmap);
            mAdapter.notifyDataSetChanged();
        }

        if(requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            /**
             * For a single Image selection from gallery
             */
            if(data.getClipData() != null)
            {
                for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                    Uri targetUri = data.getClipData().getItemAt(i).getUri();
                    setImage(targetUri);
                }
            }
            else{
                /**
                 * For multiple image slection from gallery
                 */
                Uri targetUri = data.getData();
                setImage(targetUri);
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.upload:
                LatLong latlon = new LatLong(((AdPostingManager)mActivity).getAddress());
                latlon.createAddress();
                try {
                    ProgressBarManager progress = new ProgressBarManager(mActivity, "Uploading Ad...");
                    progress.ShowProgressDialog();
                    ((AdPostingManager)mActivity).setProgressBar(progress);
                    ((AdPostingManager)mActivity).setImages(mImage);

                    ((AdPostingManager)mActivity).getlocation().setmAddress(((AdPostingManager)mActivity).getAddress());
                    latlon.execute((ConversionCallback) mActivity);



                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                break;
        }

    }
    @Override
    public void switchViews(Bitmap Identifier) {

    }
    /**
     *
     * @param targetUri:method that selects images from the gallery.
     */
    public void setImage(Uri targetUri){
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(mActivity.getContentResolver().openInputStream(targetUri));
            mImage.add(bitmap);
            mAdapter.notifyDataSetChanged();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
