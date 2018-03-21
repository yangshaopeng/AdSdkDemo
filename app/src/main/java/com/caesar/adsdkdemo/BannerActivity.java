package com.caesar.adsdkdemo;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;

import com.hhmt.ad.AdError;
import com.hhmt.ad.banner.AbsBannerListener;
import com.hhmt.ad.banner.BannerView;


public class BannerActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImg;

    ViewGroup bannerContainer;
    BannerView bv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        bannerContainer = (ViewGroup) this.findViewById(R.id.bannerContainer);
        this.findViewById(R.id.refreshBanner).setOnClickListener(this);
        this.findViewById(R.id.closeBanner).setOnClickListener(this);
        this.initBanner();
        this.bv.loadAD();

        mImg = ((ImageView) findViewById(R.id.img));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String baseOs = Build.VERSION.BASE_OS;
            Log.d("BannerActivity", "--->" + baseOs);
        }


//        mImg.animate().translationX(500);
        mImg.animate().translationXBy(500);


        new AnticipateOvershootInterpolator();

    }

    private void initBanner() {
        this.bv = new BannerView(this, "34234324", AdConfig.BANNER_POS_ID);
        // 注意：如果开发者的banner不是始终展示在屏幕中的话，请关闭自动刷新，否则将导致曝光率过低。
        // 并且应该自行处理：当banner广告区域出现在屏幕后，再手动loadAD。
        //bv.setRefresh(30);
        bv.setAdListener(new AbsBannerListener() {

            @Override
            public void onNoAD(AdError error) {
                Log.i("AD_DEMO",
                        String.format("Banner onNoAD，eCode = %d, eMsg = %s", error.getErrorCode(),
                                error.getErrorMsg()));
            }

            @Override
            public void onADReceiv() {
                Log.i("AD_DEMO", "ONBannerReceive");
            }

            @Override
            public void onADClicked() {
                Log.i("yang", "banner click");
            }
        });
        bannerContainer.addView(bv);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.refreshBanner:
                doRefreshBanner();
                break;
            case R.id.closeBanner:
                doCloseBanner();
                break;
            default:
                break;
        }
    }

    private void doRefreshBanner() {
        if (bv == null) {
            initBanner();
        }
        bv.loadAD();
    }

    private void doCloseBanner() {
        bannerContainer.removeAllViews();
        if (bv != null) {
//            bv.destroy();
            bv = null;
        }
    }
}
