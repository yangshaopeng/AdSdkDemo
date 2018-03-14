package com.caesar.adsdkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hhmt.ad.AdError;
import com.hhmt.ad.floatad.AbsFloatAdListener;
import com.hhmt.ad.floatad.FloatAd;

public class FloatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float);

        final FloatAd floatAd = new FloatAd(this, "appid", "hhmtsp000002", 100, 300);
        floatAd.setFloatAdListener(new AbsFloatAdListener() {
            @Override
            public void onADReceive() {
                //floatAd.showAsPopupWindow();
            }

            @Override
            public void onNoAD(AdError var1) {

            }

            @Override
            public void onADOpened() {

            }

            @Override
            public void onADExposure() {

            }

            @Override
            public void onADClicked() {

            }

            @Override
            public void onADLeftApplication() {

            }

            @Override
            public void onADClosed() {

            }
        });

    }
}
