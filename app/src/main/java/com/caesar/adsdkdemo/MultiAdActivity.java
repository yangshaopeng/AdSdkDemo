package com.caesar.adsdkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.hhmt.ad.AdError;
import com.hhmt.ad.MultiAd;
import com.hhmt.ad.multi.MultiAdListener;
import com.hhmt.ad.nativ.MultiAdDataRef;
import com.hhmt.comm.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 一次请求多个广告位demo。
 */

public class MultiAdActivity extends AppCompatActivity {

    FrameLayout bannerContainer;
    List<String> posIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_ad);
        bannerContainer = (FrameLayout) findViewById(R.id.bannerContainer);
        posIdList = new ArrayList<>();
        posIdList.add(AdConfig.INNER_POS_ID);
        posIdList.add(AdConfig.BANNER_POS_ID);
        posIdList.add(AdConfig.FLOAT_POS_ID);
        /**
         * 最后两个参数是显示浮窗的坐标，如果没有浮窗广告的话则传入空字符串即可。
         */
        final MultiAd multiAd = new MultiAd(this, "appid", posIdList, 800, 1800);

        multiAd.setListener(new MultiAdListener() {
            @Override
            public void onAdReceive(final List<MultiAdDataRef> multiAdDataRefList) {

                for (MultiAdDataRef multiAdDataRef : multiAdDataRefList) {
                    if (multiAdDataRef.getInnerAd() != null) {
                        Logger.i("multi ad inner");
                        (multiAdDataRef.getInnerAd()).showAsPopupWindow();
                    } else if (multiAdDataRef.getBannerView() != null) {
                        Logger.i("multi ad banner");
                        bannerContainer.addView(multiAdDataRef.getBannerView());
                    } else if (multiAdDataRef.getFloatAd() != null) {
                        Logger.i("multi ad float");
                    }
                }

            }

            @Override
            public void onNoAD(AdError adError) {
                Logger.i("multi ad error: " + adError.getErrorMsg());
            }

            @Override
            public void onAdClicked() {
                Logger.i("multi ad click");
            }

            @Override
            public void onAdClosed() {
                Logger.i("multi ad close");
            }
        });

    }


}
