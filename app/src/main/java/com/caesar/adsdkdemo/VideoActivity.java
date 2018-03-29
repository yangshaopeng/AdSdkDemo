package com.caesar.adsdkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.hhmt.ad.AdError;
import com.hhmt.ad.nativ.MediaView;
import com.hhmt.ad.nativ.NativeMediaAD;
import com.hhmt.ad.nativ.NativeMediaADData;

import java.util.Formatter;
import java.util.List;
import java.util.Locale;

public class VideoActivity extends AppCompatActivity implements NativeMediaAD.NativeMediaADListener {

    private MediaView mediaView;
    private TextView progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio);


        mediaView = ((MediaView) findViewById(R.id.mediaView));

        progress = ((TextView) findViewById(R.id.progress));
        NativeMediaAD ad = new NativeMediaAD(this, "sdfa", "fasfdsa", this);
        ad.loadAd(10);
    }

    /**
     * 加载普通原生广告。
     *
     * @param list
     */
    @Override
    public void onADLoaded(List<NativeMediaADData> list) {
        Log.i("yang", "VideoActivity onADLoaded");
    }


    @Override
    public void onNoAD(AdError adError) {
        Log.i("yang", "VideoActivity onNoAD");
    }

    @Override
    public void onADStatusChanged(NativeMediaADData nativeMediaADData) {
        Log.i("yang", "VideoActivity onADStatusChanged");
        progress.setText("进度->" + formatTime(nativeMediaADData.getCurrentPosition()));

        Log.d("VideoActivity", "nativeMediaADData.getProgress():" + nativeMediaADData.getProgress());
    }

    @Override
    public void onADError(NativeMediaADData nativeMediaADData, AdError adError) {
        Log.i("yang", "VideoActivity onADError： " + adError.getErrorMsg());
    }

    private NativeMediaADData nativeMediaADData;

    /**
     * 加载原生视频广告。
     *
     * @param nativeMediaADData
     */
    @Override
    public void onADVideoLoaded(NativeMediaADData nativeMediaADData) {
        Log.i("yang", "VideoActivity onADVideoLoaded");
        if (nativeMediaADData != null) {
            this.nativeMediaADData = nativeMediaADData;
            nativeMediaADData.bindView(mediaView, false);
            nativeMediaADData.play();
        }
    }

    @Override
    public void onADExposure(NativeMediaADData nativeMediaADData) {
        Log.i("yang", "VideoActivity onADExposure");
    }

    @Override
    public void onADClicked(NativeMediaADData nativeMediaADData) {
        Log.i("yang", "VideoActivity onADClicked");
    }


    /**
     * 将毫秒数格式化为"##:##"的时间
     *
     * @param milliseconds 毫秒数
     * @return ##:##
     */
    public static String formatTime(long milliseconds) {
        if (milliseconds <= 0 || milliseconds >= 24 * 60 * 60 * 1000) {
            return "00:00";
        }
        long totalSeconds = milliseconds / 1000;
        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;
        StringBuilder stringBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(stringBuilder, Locale.getDefault());
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (nativeMediaADData != null) {
            nativeMediaADData.stop();
        }
    }
}
