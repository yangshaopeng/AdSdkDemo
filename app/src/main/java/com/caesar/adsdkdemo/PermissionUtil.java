package com.caesar.adsdkdemo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * author    : yangshaopeng
 * email     : ysp@btomorrow.cn
 * date      : 2017/09/08  17:12
 * desc      : <p> 动态申请权限工具类，需要在Activity中回调onRequestPermissionsResult方法。 </p>
 * package   : com.hhmt.market.utils
 * project   : HHMarket
 */

public class PermissionUtil {

    public static final int REQ_STORAGE_CODE = 2;

    /**
     * 判断是否需要动态申请
     *
     * @return
     */
    private static boolean needDynamicRequest() {
        return Build.VERSION.SDK_INT >= 23;
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
