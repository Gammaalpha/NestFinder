package com.alphabgammainc.nestfinder.Landlord;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.BundleCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.alphabgammainc.nestfinder.MapsActivity;
import com.alphabgammainc.nestfinder.R;
import com.alphabgammainc.nestfinder.Utilities.ConfirmationDialog;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by soutrikbarua on 2017-05-17.
 */

public class GridElementAdapter extends RecyclerView.Adapter<GridElementAdapter.SimpleViewHolder>
        implements ImageDeletionCallBack, Serializable{

    private Activity mActivity;
    private ArrayList<Bitmap> imageList;
    private GridElementAdapter self;


    public GridElementAdapter(Activity mActivity,ArrayList<Bitmap> imageList){
        this.mActivity = mActivity;
        this.imageList=imageList;
        self = this;
    }

    @Override
    public void DeleteImage(int position) {
        imageList.remove(position);
        notifyDataSetChanged();
    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final ImageView myImage;

        public SimpleViewHolder(View view) {
            super(view);
            myImage=(ImageView)view.findViewById(R.id.Img);
        }
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(this.mActivity).inflate(R.layout.scrollable_grid_elements, parent, false);

        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        holder.myImage.setImageBitmap(imageList.get(position));
        holder.myImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("Title", "Deleting Image");
                bundle.putString("Message", "Are you sure you want to delete the image?");
                bundle.putSerializable("CallBack", self);
                bundle.putInt("Position", position);

                ConfirmationDialog confirmationDialog = new ConfirmationDialog();
                confirmationDialog.setArguments(bundle);
                confirmationDialog.show(((AdPostingManager)mActivity).getFragmentManager(),"Alert Dialog Fragment");

                return true;
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return this.imageList.size();
    }
}