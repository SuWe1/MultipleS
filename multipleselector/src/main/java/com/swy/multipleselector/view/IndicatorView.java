package com.swy.multipleselector.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Swy on 2017/7/20.
 */

public class IndicatorView extends LinearLayout {
    private int sum;
    private int fromPage = 0;
    private int toPage = 0;
    private View view;
    private int bgColor = Color.parseColor("#FF4081");

    private int viewOrientation;

    private Context mContext;

    public IndicatorView(Context context) {
        super(context);
        this.mContext = context;
    }

    public IndicatorView(Context context, int sum) {
        super(context);
        this.sum = sum;
        this.mContext = context;
    }

    public IndicatorView(Context context, int sum, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.sum = sum;
        this.mContext = context;
    }

    public IndicatorView(Context context, int sum, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.sum = sum;
        this.mContext = context;
    }

    //其他设置方法应该在其之前调用
    public void setTabOrientation(int orientation) {
        if (orientation == HORIZONTAL) {
            initHorizontalView(mContext);
        } else if (orientation == VERTICAL) {
            initVerticalView(mContext);
        }
    }

    private void initHorizontalView(Context context) {
        viewOrientation = 0;
        setOrientation(HORIZONTAL);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 6));
        setWeightSum(sum);
        view = new View(context);
        view.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
        view.setBackgroundColor(bgColor);
        addView(view);
    }

    private void initVerticalView(Context context) {
        viewOrientation = 1;
        setOrientation(VERTICAL);
        setLayoutParams(new LayoutParams(6, ViewGroup.LayoutParams.MATCH_PARENT));
        setWeightSum(sum);
        view = new View(context);
        view.setBackgroundColor(bgColor);
        view.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        addView(view);
    }

    //滑动效果
    public void scrollView(int to) {
        this.toPage = to;
        int width = getWidth() / sum;
        int height = getHeight() / sum;
        ObjectAnimator animator;
        if (viewOrientation == 0) {
            animator = ObjectAnimator.ofFloat(view, TRANSLATION_X, view.getTranslationX(), (toPage - fromPage) * width);
            animator.setDuration(400);
            animator.start();
        } else if (viewOrientation == 1) {
            animator = ObjectAnimator.ofFloat(view, TRANSLATION_Y, view.getTranslationY(), (toPage - fromPage) * height);
            animator.setDuration(400);
            animator.start();
        }
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }
}
