package com.caesar.adsdkdemo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class AdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);

        /**
         * sdk大于23，要动态申请权限，否则无法正常下载apk。
         */
        if (Build.VERSION.SDK_INT >= 23) {
            PermissionUtil.requestStoragePermission(this);
            PermissionUtil.requestLocalPermission(this);
        }
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

                // 原生广告示例
                startActivity(new Intent(AdActivity.this, NativeActivity.class));

            }
        });

        findViewById(R.id.bt_float).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //浮窗广告
                startActivity(new Intent(AdActivity.this, FloatActivity.class));
            }
        });

    }

}
