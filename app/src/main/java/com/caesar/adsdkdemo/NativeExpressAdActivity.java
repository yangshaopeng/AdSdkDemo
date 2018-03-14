package com.caesar.adsdkdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hhmt.ad.AdError;
import com.hhmt.ad.nativ.ADSize;
import com.hhmt.ad.nativ.NativeExpressAD;
import com.hhmt.ad.nativ.NativeExpressADView;

import java.util.List;

public class NativeExpressAdActivity extends AppCompatActivity implements View.OnClickListener, NativeExpressAD.NativeExpressADListener {

    private ViewGroup container;
    private NativeExpressAD nativeExpressAD;
    private NativeExpressADView nativeExpressADView;
    private Button buttonRefresh, buttonResize;
    private EditText editTextWidth, editTextHeight; // 编辑框输入的宽高
    private int adWidth, adHeight; // 广告宽高


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_express_ad);

        container = (ViewGroup) findViewById(R.id.container);
        editTextWidth = (EditText) findViewById(R.id.editWidth);
        editTextHeight = (EditText) findViewById(R.id.editHeight);
        buttonRefresh = (Button) findViewById(R.id.buttonRefresh);
        buttonResize = (Button) findViewById(R.id.buttonDestroy);
        buttonRefresh.setOnClickListener(this);
        buttonResize.setOnClickListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 使用完了每一个NativeExpressADView之后都要释放掉资源
        if (nativeExpressADView != null) {
            nativeExpressADView.destroy();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonRefresh:
                refreshAd();
                break;
            case R.id.buttonDestroy:
                resizeAd();
                break;
            default:
                break;
        }
    }


    private void refreshAd() {
        if (checkEditTextEmpty()) {
            return;
        }

        if (nativeExpressAD == null || checkEditTextChanged()) {
            adWidth = Integer.valueOf(editTextWidth.getText().toString());
            adHeight = Integer.valueOf(editTextHeight.getText().toString());
            hideSoftInput();
            buttonRefresh.setText("刷新广告");
            ADSize adSize = new ADSize(adWidth, adHeight); // 不支持MATCH_PARENT or WRAP_CONTENT，必须传入实际的宽高
            nativeExpressAD =
                    new NativeExpressAD(this, adSize, "sdfdsaf", "dsafsafds", this);
        }

        nativeExpressAD.loadAd(1);
    }

    /**
     * 在接入、调试模板广告的过程中，可以利用这个方法调整广告View的大小，找到与自己的App需求适合的最佳广告位尺寸。
     * 确定最佳尺寸后，应该把这个ADSize固定下来，并在构造NativeExpressAD的时候传入，给一个固定的广告位ID去使用。
     * 所以生产环境中，可以不需要再调用NativeExpressADView#setAdSize方法。
     */
    private void resizeAd() {
        if (nativeExpressADView == null) {
            return;
        }

        if (checkEditTextEmpty()) {
            return;
        }

        if (checkEditTextChanged()) {
            adWidth = Integer.valueOf(editTextWidth.getText().toString());
            adHeight = Integer.valueOf(editTextHeight.getText().toString());
            nativeExpressADView.setAdSize(new ADSize(adWidth, adHeight));
            hideSoftInput();
        }
    }





    private boolean checkEditTextEmpty() {
        String width = editTextWidth.getText().toString();
        String height = editTextHeight.getText().toString();
        if (TextUtils.isEmpty(width) || TextUtils.isEmpty(height)) {
            Toast.makeText(this, "请先输入广告位的宽、高！", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    private boolean checkEditTextChanged() {
        return Integer.valueOf(editTextWidth.getText().toString()) != adWidth
                || Integer.valueOf(editTextHeight.getText().toString()) != adHeight;
    }

    // 隐藏软键盘，这只是个简单的隐藏软键盘示例实现，与广告sdk功能无关
    private void hideSoftInput() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                NativeExpressAdActivity.this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onNoAD(AdError adError) {

    }

    @Override
    public void onADLoaded(List<NativeExpressADView> adList) {

        Log.i("NativeExpressAdActivity", "onADLoaded: " + adList.size());
        // 释放前一个NativeExpressADView的资源
        if (nativeExpressADView != null) {
            nativeExpressADView.destroy();
        }

        if (container.getVisibility() != View.VISIBLE) {
            container.setVisibility(View.VISIBLE);
        }

        if (container.getChildCount() > 0) {
            container.removeAllViews();
        }

        nativeExpressADView = adList.get(0);
        // 保证View被绘制的时候是可见的，否则将无法产生曝光和收益。
        container.addView(nativeExpressADView);
        nativeExpressADView.render();

    }

    @Override
    public void onRenderFail(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onRenderSuccess(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADExposure(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADClicked(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADClosed(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADLeftApplication(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADOpenOverlay(NativeExpressADView nativeExpressADView) {

    }
}
