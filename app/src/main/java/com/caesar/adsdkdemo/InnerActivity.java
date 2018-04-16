package com.caesar.adsdkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hhmt.ad.AdError;
import com.hhmt.ad.inner.AbsInnerAdListener;
import com.hhmt.ad.inner.InnerAD;

import static com.caesar.adsdkdemo.AdConfig.APP_ID;
import static com.caesar.adsdkdemo.AdConfig.LOG_TAG;

public class InnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner);

        final InnerAD innerAD = new InnerAD(InnerActivity.this, APP_ID, AdConfig.INNER_POS_ID);

        innerAD.setADListener(new AbsInnerAdListener() {

            /**
             * 加载成功。
             */
            @Override
            public void onADReceive() {
                innerAD.showAsPopupWindow();
                Log.i(LOG_TAG, "onADReceive");
            }

            /**
             * 加载错误。
             * @param var1
             */
            @Override
            public void onNoAD(AdError var1) {
                Log.e(LOG_TAG, "code: " + var1.getErrorCode() + "");
                Log.e(LOG_TAG, "msg : " + var1.getErrorMsg());
            }

            @Override
            public void onADClosed() {
                Log.i(LOG_TAG, "inner close");
            }

            @Override
            public void onADClicked() {
                Log.i(LOG_TAG, "inner click");
            }
        });
        innerAD.loadAD();

    }
}
