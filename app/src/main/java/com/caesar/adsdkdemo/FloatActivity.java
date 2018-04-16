package com.caesar.adsdkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.hhmt.ad.AdError;
import com.hhmt.ad.floatad.AbsFloatAdListener;
import com.hhmt.ad.floatad.FloatAd;

import static com.caesar.adsdkdemo.AdConfig.APP_ID;
import static com.caesar.adsdkdemo.AdConfig.FLOAT_POS_ID;
import static com.caesar.adsdkdemo.AdConfig.LOG_TAG;

public class FloatActivity extends AppCompatActivity {

    private FrameLayout floatContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float);
        floatContainer = (FrameLayout) findViewById(R.id.float_container);
        final FloatAd floatAd = new FloatAd(this, APP_ID, FLOAT_POS_ID);
        floatAd.setFloatAdListener(new AbsFloatAdListener() {
            @Override
            public void onADReceive() {
                Log.e(LOG_TAG, "onADReceive");
                floatContainer.addView(floatAd.getFloatView());
            }

            @Override
            public void onNoAD(AdError var1) {
                Log.e(LOG_TAG, "code: " + var1.getErrorCode());
                Log.e(LOG_TAG, "msg : " + var1.getErrorMsg());
            }

            @Override
            public void onADOpened() {

            }

            @Override
            public void onADExposure() {

            }

            @Override
            public void onADClicked() {
                Log.e(LOG_TAG, "onADClicked");
            }

            @Override
            public void onADLeftApplication() {

            }

            @Override
            public void onADClosed() {
                Log.e(LOG_TAG, "float close");
                floatContainer.setVisibility(View.GONE);
            }
        });

    }
}
