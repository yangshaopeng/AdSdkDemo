package com.caesar.adsdkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;

import com.hhmt.ad.AdError;
import com.hhmt.ad.banner.AbsBannerListener;
import com.hhmt.ad.banner.BannerView;

import static com.caesar.adsdkdemo.AdConfig.APP_ID;
import static com.caesar.adsdkdemo.AdConfig.LOG_TAG;


public class BannerActivity extends AppCompatActivity {

    ViewGroup bannerContainer;
    BannerView bv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        bannerContainer = (ViewGroup) this.findViewById(R.id.bannerContainer);
        this.initBanner();
        this.bv.loadAD();

    }

    private void initBanner() {
        this.bv = new BannerView(this, APP_ID, AdConfig.BANNER_POS_ID);
        bv.setAdListener(new AbsBannerListener() {

            @Override
            public void onNoAD(AdError error) {
                Log.e(LOG_TAG, "code: " + error.getErrorCode());
                Log.e(LOG_TAG, "msg : " + error.getErrorMsg());
            }

            @Override
            public void onADReceiv() {
                Log.i(LOG_TAG, "ONBannerReceive");
            }

            @Override
            public void onADClicked() {
                Log.i(LOG_TAG, "banner click");
            }
        });
        bannerContainer.addView(bv);

    }

}
