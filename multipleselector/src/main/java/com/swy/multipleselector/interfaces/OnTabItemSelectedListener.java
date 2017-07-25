package com.swy.multipleselector.interfaces;

import com.swy.multipleselector.MultipleSelector;
import com.swy.multipleselector.view.TabView;

/**
 * Created by Swy on 2017/7/20.
 */

public interface OnTabItemSelectedListener {
    void TabItemSelected(MultipleSelector selector, TabView tabView);

    void TabItemReSelected(MultipleSelector selector, TabView tabView);
}
