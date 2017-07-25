package com.swy.multipleselector.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Swy on 2017/7/20.
 */

public class TabView extends TextView {
    private boolean isSelected=false;
    private int isSelectedColor= Color.parseColor("#FF4081");
    private int unSelectedColor=Color.parseColor("#333333");
    private int index=0;
    private int tabTextSize=15;
    private Context context;
    public TabView(Context context) {
        super(context);
        initView();
    }

    public TabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    private void initView(){
        setTextSize(tabTextSize);
    }

    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
        setText(getText());
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (isSelected){
            setTextColor(isSelectedColor);
        }else {
            setTextColor(unSelectedColor);
        }
        super.setText(text, type);
    }

    public void  resetTxt(){
        setText("");
    }

    public void resetStatus(){
        isSelected=false;
        setTextColor(unSelectedColor);
    }

    public void setStatus(TabView tab){
        tab.isSelected=true;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setIsSelectedColor(int isSelectedColor) {
        this.isSelectedColor = isSelectedColor;
    }

    public void setUnSelectedColor(int unSelectedColor) {
        this.unSelectedColor = unSelectedColor;
    }

    public void setTabTextSize(int tabTextSize) {
        this.tabTextSize = tabTextSize;
        //更改文字大小
        initView();
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }
}
