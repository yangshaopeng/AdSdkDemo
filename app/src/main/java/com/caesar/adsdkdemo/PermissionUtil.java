package com.caesar.adsdkdemo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * author    : yangshaopeng
 * email     : ysp@btomorrow.cn
 * date      : 2017/09/08  17:12
 * desc      : <p> 动态申请权限工具类，需要在Activity中回调onRequestPermissionsResult方法。 </p>
 * package   : com.hhmt.market.utils
 * project   : HHMarket
 */

public class PermissionUtil {

    public static final int REQ_CAMERA_CODE = 1;
    public static final int REQ_STORAGE_CODE = 2;
    public static final int REQ_LOCATION_CODE = 3;

    /**
     * 动态申请相机权限。
     *
     * @param activity 申请的Activity。
     */
    public static void requestCameraPermission(final Activity activity) {
        if (needDynamicRequest()) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager
                    .PERMISSION_GRANTED) {
                //标明用户没有禁止弹出权限请求。
                if (!ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        Manifest.permission.CAMERA)) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, REQ_CAMERA_CODE);
                }
            } else {
                openCamera(activity);
            }
        } else {
            openCamera(activity);
        }
    }

    /**
     * 判断是否需要动态申请
     *
     * @return
     */
    private static boolean needDynamicRequest() {
        return Build.VERSION.SDK_INT >= 23;
    }

    /**
     * 打开相机
     *
     * @param activity 调用的 Activity。
     */
    public static void openCamera(Activity activity) {
        Intent intent = new Intent();
        intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    /**
     * 动态申请SD卡读写权限
     *
     * @param activity
     */
    public static void requestStoragePermission(Activity activity) {
        String[] wrPermission = {Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (needDynamicRequest()) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, wrPermission, REQ_STORAGE_CODE);
            }
        }
    }

    /**
     * 获取定位权限。
     *
     * @param activity
     */
    public static void requestLocalPermission(Activity activity) {
        String[] localPermission = {Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.ACCESS_FINE_LOCATION};
        if (needDynamicRequest()) {
            if (needDynamicRequest()) {
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, localPermission, REQ_STORAGE_CODE);
                }
            }
        }
    }

}
