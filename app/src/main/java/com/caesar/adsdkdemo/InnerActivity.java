package com.caesar.adsdkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.hhmt.ad.AdError;
import com.hhmt.ad.inner.AbsInnerAdListener;
import com.hhmt.ad.inner.InnerAD;

public class InnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner);

        final InnerAD innerAD = new InnerAD(InnerActivity.this, "13433242342", AdPosIdConfig.INNER_POS_ID);

        innerAD.setADListener(new AbsInnerAdListener() {

            /**
             * 加载成功。
             */
            @Override
            public void onADReceive() {
                innerAD.showAsPopupWindow();
                Log.i("InnerActivity: ", "onADReceive");
            }

            /**
             * 加载错误。
             * @param var1
             */
            @Override
            public void onNoAD(AdError var1) {
                Log.i("InnerActivity: ", var1.getErrorMsg());
            }

            @Override
            public void onADClosed() {

            }

            @Override
            public void onADClicked() {
                Log.i("yang: ", "inner click");
            }
        });
        innerAD.loadAD();


        findViewById(R.id.load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                innerAD.loadAD();

            }
        });

        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                innerAD.closePopupWindow();
            }
        });

    }
}
