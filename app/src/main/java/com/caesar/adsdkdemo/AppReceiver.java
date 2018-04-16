package com.caesar.adsdkdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.hhmt.comm.manager.ADManager;
import com.hhmt.comm.util.Logger;

/**
 * @author : yangshaopeng
 *         email     : ysp@btomorrow.cn
 *         date      : 2018/04/11  14:56
 *         desc      : <p> </p>
 *         package   : com.hhmt.adlib
 *         project   : HH_ADSDK
 */

public class AppReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            return;
        }
        if (TextUtils.equals(action, Intent.ACTION_PACKAGE_ADDED)
                || TextUtils.equals(action, Intent.ACTION_PACKAGE_REPLACED)) {
            sendBroadcast(context, intent);
        }
    }

    private void sendBroadcast(Context context, Intent data) {
        String action = data.getAction();
        String pkg = data.getData().getSchemeSpecificPart();
        ADManager.getInstance().reportInstalled(action, pkg);
    }

}
