package com.alphabgammainc.nestfinder.Utilities;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by soutrikbarua on 2017-05-20.
 */

public class PermissionsManager {
    public static boolean isExternalStorageWritable(Activity mActivity) {
        String permission = "android.permission.WRITE_EXTERNAL_STORAGE"; // get permissions
        int res= mActivity.checkCallingOrSelfPermission(permission);
        return (res== PackageManager.PERMISSION_GRANTED);
    }
    public static boolean checkReadExternalPermission(Activity mActivity){
        String permission = "android.permission.READ_EXTERNAL_STORAGE"; // get permissions
        int res= mActivity.checkCallingOrSelfPermission(permission);
        return (res== PackageManager.PERMISSION_GRANTED);
    }
    public static void requestForSpecificPermission(Activity mActivity) {
        ActivityCompat.requestPermissions(mActivity, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
    }

}
