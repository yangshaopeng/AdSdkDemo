package com.caesar.adsdkdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;
import android.widget.Toast;

/**
 * @author : yangshaopeng
 *         email     : ysp@btomorrow.cn
 *         date      : 2018/01/17  17:48
 *         desc      : <p> </p>
 *         package   : com.caesar.adsdkdemo
 *         project   : AdSdkDemo
 */

public class MyScrollView extends ScrollView {

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (getMeasuredHeight() <= getScrollY() + getHeight()) {
            Toast.makeText(getContext(), "bottom", Toast.LENGTH_SHORT).show();
        } else if (getScrollY() == 0) {
            Toast.makeText(getContext(), "top", Toast.LENGTH_SHORT).show();
        }
    }
}
