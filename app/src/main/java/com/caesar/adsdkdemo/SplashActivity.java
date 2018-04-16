package com.caesar.adsdkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhmt.ad.AdError;
import com.hhmt.ad.splash.SplashAD;
import com.hhmt.ad.splash.SplashADListener;

import static com.caesar.adsdkdemo.AdConfig.APP_ID;
import static com.caesar.adsdkdemo.AdConfig.LOG_TAG;

public class SplashActivity extends AppCompatActivity implements SplashADListener {

    private SplashAD splashAD;
    private ViewGroup container;
    private TextView skipView;
    private ImageView splashHolder;
    private static final String SKIP_TEXT = "点击跳过 %d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        getWindow().setFlags(flag, flag);

        container = (ViewGroup) this.findViewById(R.id.splash_container);
        skipView = (TextView) findViewById(R.id.skip_view);
        splashHolder = (ImageView) findViewById(R.id.splash_holder);

        fetchSplashAD(this, container, skipView, APP_ID, AdConfig.SPLASH_POS_ID, this, 0);

    }


    /**
     * 拉取开屏广告，开屏广告的构造方法有3种，详细说明请参考开发者文档。
     *
     * @param activity      展示广告的activity
     * @param adContainer   展示广告的大容器
     * @param skipContainer 自定义的跳过按钮：传入该view给SDK后，SDK会自动给它绑定点击跳过事件。SkipView的样式可以由开发者自由定制，其尺寸限制请参考activity_splash.xml或者接入文档中的说明。
     * @param appId         应用ID
     * @param posId         广告位ID
     * @param adListener    广告状态监听器
     * @param fetchDelay    拉取广告的超时时长：取值范围[3000, 5000]，设为0表示使用广点通SDK默认的超时时长。
     */
    private void fetchSplashAD(Activity activity, ViewGroup adContainer, View skipContainer,
                               String appId, String posId, SplashADListener adListener, int fetchDelay) {
        splashAD = new SplashAD(activity, adContainer, skipContainer, appId, posId, adListener, fetchDelay);
    }

    @Override
    public void onADDismissed() {
        Log.i(LOG_TAG, "onADDismissed");
        finish();
    }

    @Override
    public void onNoAD(AdError adError) {
        Log.e(LOG_TAG, "code: " + adError.getErrorCode());
        Log.e(LOG_TAG, "msg : " + adError.getErrorMsg());
    }

    @Override
    public void onADPresent() {
        Log.i(LOG_TAG, "onADPresent");
        splashHolder.setVisibility(View.GONE); // 广告展示后一定要把预设的开屏图片隐藏起来
    }

    @Override
    public void onADClicked() {
        Log.i(LOG_TAG, "SplashADClicked");
    }

    @Override
    public void onADTick(long time) {
        Log.i(LOG_TAG, "SplashADTick " + time + "s");
        skipView.setText(String.format(SKIP_TEXT, time));
    }

}
