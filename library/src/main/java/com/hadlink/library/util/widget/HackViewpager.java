package com.hadlink.library.util.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by lyao on 2015/5/27.
 */
public class HackViewpager extends ViewPager {
    private boolean noScroll = false;

    public HackViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HackViewpager(Context context) {
        super(context);
    }

    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        //viewpager滚动在这里
        return !noScroll && super.onTouchEvent(e);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return !noScroll && super.onInterceptTouchEvent(e);
    }
}