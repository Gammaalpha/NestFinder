package com.alphabgammainc.nestfinder.LoadingDialog;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by davidhuang on 2017-05-21.
 */

public class ProgressBarManager {
    private ProgressDialog pDialog;
    private Activity mActivity;
    private String Message;
    public ProgressBarManager(Activity activity,String m){
        Message=m;
        mActivity=activity;
    }

    public void ShowProgressDialog() { // progress
        if (pDialog == null) {
            pDialog = new ProgressDialog(mActivity);
            pDialog.setMessage(Message);
            pDialog.setIndeterminate(true);
        }
        pDialog.show();
    }
    public void HideProgressDialog() {
        if(pDialog!=null && pDialog.isShowing()){
            pDialog.dismiss();
            pDialog.cancel();
        }
    }
}
