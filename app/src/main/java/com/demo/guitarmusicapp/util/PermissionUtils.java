package com.demo.guitarmusicapp.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class PermissionUtils {
    public interface PermissionCallBack {
        void success();
    }

    //权限声明
    private final int REQUEST_EXTERNAL_STORAGE = 36472;
    private String[] PERMISSIONS_STORAGE = {
            Manifest.permission.INTERNET,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.SYSTEM_ALERT_WINDOW,
            Manifest.permission.RECORD_AUDIO
    };

    //权限申请
    public void verifyStoragePermissions(Activity activity, PermissionCallBack permissionCallBack) {
        for (int i = 0; i < PERMISSIONS_STORAGE.length; i++) {
            int permission = ActivityCompat.checkSelfPermission(activity, PERMISSIONS_STORAGE[i]);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
                return;
            }
        }
        if (permissionCallBack != null) {
            permissionCallBack.success();
        }
    }
}
