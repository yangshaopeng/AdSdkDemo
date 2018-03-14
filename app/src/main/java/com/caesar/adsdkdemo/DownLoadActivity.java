package com.caesar.adsdkdemo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hhmt.comm.download.DownloadManager;


public class DownLoadActivity extends AppCompatActivity implements DownloadManager.IDownloadStatusListener {


    Button button;
    private int i = 0;

    public static final String URL = "http://113.96.154.33/appdl.hicloud.com/dl/appdl/application/apk/b5/b5f4355977474e16b136b63ac2c7df66/com.ss.android.article.news.1710140852.apk?mkey=59f83cfe68787184&f=9e16&c=0&sign=portal@portal1509431817425&source=portalsite&p=.apk";


    public static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    private DownloadManager downloadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);

        downloadManager = DownloadManager.fromContext(DownLoadActivity.this);
        downloadManager.deleteWrongTask();


        button = (Button) findViewById(R.id.bt_bind);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


        findViewById(R.id.bt_down1).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                downloadManager.createTask(DownLoadActivity.this, URL, PATH + "/HHMTDOWNLOAD/jrtt.apk", "", true);

            }
        });


        findViewById(R.id.bt_down2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk

                downloadManager.createTask(DownLoadActivity.this, "http://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk",
                        PATH + "/HHMTDOWNLOAD/qq.apk", "", true);

            }
        });


        findViewById(R.id.bt_down3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                downloadManager.deleteTask("http://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk");
                downloadManager.deleteTask(URL);
            }
        });


        downloadManager.registerListener(this);


    }


    @Override
    public void onStatusChange(long id, String url, int state, long downloaded, long fileSize, long speed) {
//        Log.d("DownLoadActivity", "fileSize:" + fileSize);
//        Log.d("DownLoadActivity", "downloaded:" + downloaded);
        if (fileSize > 0) {
            int progress = ((int) ((downloaded * 100) / fileSize));
            Log.d("DownLoadActivity", id + " - progress->" + progress);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        downloadManager.unregisterListener(this);
    }
}
