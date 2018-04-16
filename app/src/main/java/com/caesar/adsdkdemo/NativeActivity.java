package com.caesar.adsdkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.hhmt.ad.AdError;
import com.hhmt.ad.nativ.NativeAD;
import com.hhmt.ad.nativ.NativeADDataRef;
import com.hhmt.ad.nativ.NativeAdListener;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.caesar.adsdkdemo.AdConfig.APP_ID;
import static com.caesar.adsdkdemo.AdConfig.LOG_TAG;


public class NativeActivity extends AppCompatActivity implements NativeAdListener {

    private NativeAD ad;
    NativeADDataRef nativeADDataRef;

    TextView tvprogress;
    private int downX;
    private int downY;
    private int upX;
    private int upY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nagiv);

        tvprogress = (TextView) findViewById(R.id.progress);
        loadAd();

        tvprogress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = (int) event.getX();
                        downY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        upX = (int) event.getX();
                        upY = (int) event.getY();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

    }

    private void loadAd() {
        List<String> posList = new ArrayList<>();
        posList.add(AdConfig.NATIVE_POS_ID);
        if (ad == null) {
            ad = new NativeAD(NativeActivity.this, APP_ID, posList);
            ad.setNativeAdListener(this);
        }
        ad.loadAd(12);
    }

    @Override
    public void onADLoaded(List<NativeADDataRef> list) {
        //加载成功获取数据。
        if (list.size() > 0) {
            nativeADDataRef = list.get(0);
            /**
             * 加载成功之后上报曝光。
             * 加载成功之后才能上报点击，否则nativeADDataRef为null。
             */
            tvprogress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //需要上报点击坐标，由调入方传入。
                    nativeADDataRef.onClicked(view, downX, downY, upX, upY);
                }
            });
            nativeADDataRef.onExposured(findViewById(R.id.progress));
            Log.i(LOG_TAG, nativeADDataRef.getTitle());
            Log.i(LOG_TAG, nativeADDataRef.getDesc());
            Log.i(LOG_TAG, nativeADDataRef.getIconUrl());
            Log.i(LOG_TAG, nativeADDataRef.getImgUrl());
        }
    }

    /**
     * 加载失败
     *
     * @param adError
     */
    @Override
    public void onNoAD(AdError adError) {
        Log.d(LOG_TAG, "code: " + adError.getErrorCode());
        Log.d(LOG_TAG, "msg : " + adError.getErrorMsg());
    }

    /**
     * 状态发生改变
     *
     * @param nativeADDataRef
     */
    @Override
    public void onADStatusChanged(NativeADDataRef nativeADDataRef) {

    }

    @Override
    public void onADError(AdError adError) {
        Log.e(LOG_TAG, "msg : " + adError.getErrorMsg());
        Log.e(LOG_TAG, "code: " + adError.getErrorCode() + "");
    }
}
