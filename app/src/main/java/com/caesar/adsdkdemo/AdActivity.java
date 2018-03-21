package com.caesar.adsdkdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.logging.Logger;


public class AdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);

        /**
         * sdk大于23，要动态申请权限，否则无法正常下载apk。
         */
        PermissionUtil.requestStoragePermission(this);
        PermissionUtil.requestLocalPermission(this);
        findViewById(R.id.bt_banner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 横幅 广告示例
                startActivity(new Intent(AdActivity.this, BannerActivity.class));

            }
        });


        findViewById(R.id.bt_chaping).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initDevice();
                // 插屏 广告示例
                startActivity(new Intent(AdActivity.this, InnerActivity.class));

            }
        });

        findViewById(R.id.bt_splash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 闪屏广告示例
                startActivity(new Intent(AdActivity.this, SplashActivity.class));

            }
        });


        findViewById(R.id.bt_native).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 原生广告  示例
                startActivity(new Intent(AdActivity.this, NativActivity.class));

            }
        });


        findViewById(R.id.bt_native_express).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 原生广告 模板示例
                startActivity(new Intent(AdActivity.this, NativeExpressAdActivity.class));
            }
        });

        findViewById(R.id.bt_native_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 原生广告 模板示例
                startActivity(new Intent(AdActivity.this, VedioActivity.class));
            }
        });

        findViewById(R.id.bt_float).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdActivity.this, FloatActivity.class));
            }
        });

        findViewById(R.id.multi_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdActivity.this, MultiAdActivity.class));
            }
        });

        otherTest();
    }

    private void otherTest() {

        findViewById(R.id.bt_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdActivity.this, WebAt.class));
            }
        });




        findViewById(R.id.bt_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdActivity.this, DownLoadActivity.class));
            }
        });
    }

    private void initDevice() {
        /*DeviceStatus status = new DeviceStatus(AdActivity.this);
        status.getCarrier();
        status.getDataNet();
        status.getDeviceDensity();
        status.getDid();
        status.getLat();
        status.getLocationAccuracy();
        status.getLng();
        status.getOperator();
        status.getLacAndCeilId();
        status.getDeviceHeight();
        status.getDeviceWidth();
        status.getLanguage();
        status.getScreenOrientation();*/
    }



}
