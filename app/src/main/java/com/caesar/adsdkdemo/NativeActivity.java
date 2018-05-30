package com.caesar.adsdkdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.download.DownloadTask;
import com.hhmt.ad.AdError;
import com.hhmt.ad.nativ.NativeAD;
import com.hhmt.ad.nativ.NativeADDataRef;
import com.hhmt.ad.nativ.NativeAdListener;
import com.hhmt.comm.util.Logger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class NativeActivity extends AppCompatActivity implements NativeAdListener {

    public static List<String> urls = new ArrayList<>();
    private NativeAD ad;
    NativeADDataRef nativeADDataRef;

    static {

        urls.add("http://file.anruan.com/soft/7/android_7993.apk");
        urls.add("http://file.anruan.com/soft/7/android_7993.apk");

    }

    private TextView tvprogress;
    private TextView title;
    private TextView description;
    private ImageView imageView;
    int downX;
    int downY;
    int upX;
    int upY;

    private void testDownloadO() {
        /*String downloadUrl = "http://imtt.dd.qq.com/16891/464C58CB3F0DC596E9CCCDE239359817.apk?fsname=com.snda.wifilocating_4.2.83_3203.apk&csr=1bbd";
        Aria.download(this).load(downloadUrl).setDownloadPath(Environment.getExternalStoragePublicDirectorygetAbsolutePath() + File.separator + "HHMTMARKET"
                + File.separator + "testO" + ".apk").start();*/
    }

    String path = "";
    @Download.onTaskComplete
    void taskComplete(DownloadTask task) {
        path = task.getDownloadPath();
        ApkUtil.installApk(path, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nagiv);
        Aria.download(this).register();
        testDownloadO();

        tvprogress = (TextView) findViewById(R.id.progress);
        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        imageView = (ImageView) findViewById(R.id.image);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 5) {
            ApkUtil.installApk(path, this);
        }
    }

    List<String> posIdList;

    private void loadAd() {
        posIdList = new ArrayList<>();
        posIdList.add(AdConfig.NATIVE_POS_ID);
        /*posIdList.add(AdConfig.NATIVE_POS_ID);
        posIdList.add(AdConfig.NATIVE_POS_ID);
        posIdList.add(AdConfig.NATIVE_POS_ID);
        posIdList.add(AdConfig.NATIVE_POS_ID);*/
        if (ad == null) {
            ad = new NativeAD(NativeActivity.this, "2132432142", posIdList);
            ad.setNativeAdListener(this);
        }
        ad.loadAd(12);
    }

    @Override
    public void onADLoaded(List<NativeADDataRef> list) {
        //加载成功获取数据。
        for (int i = 0; i < list.size(); i++) {
            nativeADDataRef = list.get(i);
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
            //加载成功之后曝光。
            nativeADDataRef.onExposured(findViewById(R.id.progress), 1080, 320);
            final Bitmap bitmap = getBitmap(nativeADDataRef.getImgUrl());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //回调方法是子线程，所以需要在主线程更新UI。
                    title.setText(nativeADDataRef.getTitle());
                    description.setText(nativeADDataRef.getDesc());
                    imageView.setImageBitmap(bitmap);
                }
            });
            Logger.i("NativeActivity: " + nativeADDataRef.getTitle());
            Logger.i("NativeActivity: " + nativeADDataRef.getDesc());
            Logger.i("NativeActivity: " + nativeADDataRef.getIconUrl());
            Logger.i("NativeActivity: " + nativeADDataRef.getImgUrl());
        }
    }

    /**
     * 加载失败
     *
     * @param adError
     */
    @Override
    public void onNoAD(AdError adError) {
        Logger.e("adError.getErrorCode():" + adError.getErrorCode());
        Logger.e("msg: " + adError.getErrorMsg());
    }

    /**
     * 状态发生改变
     *
     * @param nativeADDataRef
     */
    @Override
    public void onADStatusChanged(NativeADDataRef nativeADDataRef) {

    }

    /**
     * 广告调用曝光、点击接口时发生的错误
     *
     * @param adError
     */
    @Override
    public void onADError(AdError adError) {
        Logger.e("load native ad error: " + adError.getErrorMsg() + "; code: " + adError.getErrorCode());
    }

    public Bitmap getBitmap(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            int length = http.getContentLength();

            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }


}
