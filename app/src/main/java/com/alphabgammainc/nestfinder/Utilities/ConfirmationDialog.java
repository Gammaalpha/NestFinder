package com.alphabgammainc.nestfinder.Utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.alphabgammainc.nestfinder.Landlord.ImageDeletionCallBack;

/**
 * Created by soutrikbarua on 2017-05-20.
 */

public class ConfirmationDialog extends DialogFragment {
    private Activity mActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mActivity);

        final Bundle bundle =getArguments();


        if(bundle!=null){
            try {
                alertDialog.setTitle(bundle.getString("Title"));
                alertDialog.setMessage(bundle.getString("Message", ""));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            alertDialog.setMessage("");
        }
        alertDialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setShowsDialog(false);
                dismiss();
            }
        });

        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              // delete image
                ImageDeletionCallBack callback = (ImageDeletionCallBack) bundle.getSerializable("CallBack");
                int position = bundle.getInt("Position");
                if(callback != null) callback.DeleteImage(position);
            }
        });
        return alertDialog.create();
    }

}
