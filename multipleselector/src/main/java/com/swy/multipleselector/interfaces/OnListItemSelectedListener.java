package com.swy.multipleselector.interfaces;

import com.swy.multipleselector.MultipleSelector;

/**
 * Created by Swy on 2017/7/20.
 */

public interface OnListItemSelectedListener {
    void ListItemSelected(MultipleSelector selector, DataSourceInterface dataSourceInterface, int tabPosition, int position);
}
