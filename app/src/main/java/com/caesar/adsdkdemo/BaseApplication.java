package com.caesar.adsdkdemo;

import android.app.Application;

import com.hhmt.comm.manager.ADManager;

import static com.caesar.adsdkdemo.AdConfig.APP_ID;

/**
 * Created by hapi on 2017/8/25.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ADManager.getInstance().init(this, APP_ID);
    }

}
