package com.caesar.adsdkdemo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.hhmt.comm.manager.ADManager;

import java.util.List;

/**
 * Created by hapi on 2017/8/25.
 */

public class BaseApplication extends Application {


    private static final String TAG = "BaseApplication";
    public static String sPackageName = "";

    public static String sProcessName;

    private static BaseApplication sApplication;

    public static BaseApplication getApplication() {
        return sApplication;
    }

    public static Context getContext() {
        return sApplication;
    }


    @Override
    public void onCreate() {
        if (sApplication != null) {
            return;
        }
        sPackageName = getPackageName();
        sApplication = this;
        super.onCreate();


        sProcessName = getProcessName();

        init();


    }

    private void init() {

        ADManager.getInstance().init(this,"fdsafsad");

    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();

    }


    public static boolean isMainProcess() {
        return sPackageName.equals(sProcessName);
    }


    /**
     * 一些比较耗时需延时加载的任务
     */
    public Runnable delayTaskRunner = new Runnable() {

        @Override
        public void run() {

        }
    };

    private static final String NAME_DEFAULT = "unknown";

    public static String getProcessName() {
        Context context = BaseApplication.getContext();
        List<ActivityManager.RunningAppProcessInfo> appList = null;
        try {
            ActivityManager actMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            appList = actMgr.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo info : appList) {
                if (null != info && info.pid == android.os.Process.myPid()) {
                    return info.processName;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "get process name failed.", e);
        }
        return NAME_DEFAULT;
    }
}
