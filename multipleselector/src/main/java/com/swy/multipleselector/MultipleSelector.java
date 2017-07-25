package com.swy.multipleselector;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.swy.multipleselector.interfaces.DataSourceInterface;
import com.swy.multipleselector.interfaces.OnListItemSelectedListener;
import com.swy.multipleselector.interfaces.OnTabItemSelectedListener;
import com.swy.multipleselector.view.IndicatorView;
import com.swy.multipleselector.view.TabView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Swy on 2017/7/20.
 */

public class MultipleSelector extends LinearLayout implements View.OnClickListener {
    private static final String TAG = "MultipleSelector";
    //tab可设置的属性
    //选择和没选择的字体颜色
    private int tabIsSelectedColor = Color.parseColor("#FF4081");
    private int tabUnSelectedColor = Color.parseColor("#333333");
    //字体大小
    private int tabTextSize = 15;
    //上下内边距
    private int tabPadding = 30;
    //tab总个数 默认为三个
    private int tabCount = 3;
    //当前tab 
    private int tabIndex;
    //tab文字提示
    private String tabTextHint = "请选择";
    //tab方向 纵向 横向
    private int tabOrientation;

    private ArrayList<TabView> tabList;

    //分割线
    private IndicatorView indicatorView;
    private int indicatorColor = Color.parseColor("#FF4081");

    //内容列表属性
    private int itemIsSelectedColor = Color.parseColor("#FF4081");
    private int itemUnSelectedColor = Color.parseColor("#FF4081");
    //字体大小
    private int itemTextSize = -1;
    //item图片资源ID
    private int itemIconResource = -1;

    //网格布局时的间距
    private int space=10;
    //网格布局每行个数
    private int spanCount;


    //整个Tab
    private LinearLayout tabGroup;

    private Context mContext;

    //tab和list中间的分隔线
    private View divisionLine;

    private RecyclerView recyclerView;
    private ArrayList<DataSourceInterface> dataList;
    private ArrayList<String> imgList;
    private MultipleSelectorAdapter adapter;
    private OnListItemSelectedListener onListItemSelectedListener;
    private OnTabItemSelectedListener onTabItemSelectedListener;

    //适配器配置
    //默认线性列表
    private boolean isLinear = true;
    private static final int LINEAR_LIST = 1;
    private static final int GRID_LIST = 2;

    private RecyclerView.LayoutManager manager;

    public MultipleSelector(Context context) {
        super(context);
        this.mContext = context;
    }

    public MultipleSelector(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public MultipleSelector(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public void setOnListItemSelectedListener(OnListItemSelectedListener onListItemSelectedListener) {
        this.onListItemSelectedListener = onListItemSelectedListener;
    }

    public void setOnTabItemSelectedListener(OnTabItemSelectedListener onTabItemSelectedListener) {
        this.onTabItemSelectedListener = onTabItemSelectedListener;
    }

    //其他设置属性的方法应该在其之前调用
    public void show() {
        if (tabOrientation == VERTICAL) {
            initVerticalView(mContext);
        } else {
            initHorizontalView(mContext);
        }
    }

    public void show(int orientation) {
        if (orientation == VERTICAL) {
            initVerticalView(mContext);
        } else {
            initHorizontalView(mContext);
        }
    }

    private void initHorizontalView(Context context) {
        removeAllViews();
        setOrientation(VERTICAL);
        tabGroup = new LinearLayout(context);
        tabGroup.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        tabGroup.setWeightSum(tabCount);
        tabGroup.setOrientation(HORIZONTAL);
        addView(tabGroup);

        TabView tab = addTab(tabTextHint, true);
        tabGroup.addView(tab);
        tabList = new ArrayList<>();
        tabList.add(tab);
        for (int i = 1; i < tabCount; i++) {
            TabView nullTab = addTab("", false);
            nullTab.setIndex(i);
            tabGroup.addView(nullTab);
            tabList.add(nullTab);
        }

        indicatorView = new IndicatorView(context, tabCount);
        indicatorView.setBgColor(indicatorColor);
        indicatorView.setTabOrientation(HORIZONTAL);
        indicatorView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 6));
        addView(indicatorView);

        divisionLine = new View(context);
        divisionLine.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, 2));
        divisionLine.setBackgroundColor(context.getResources().getColor(R.color.gray_line));
        addView(divisionLine);

        recyclerView = new RecyclerView(context);
        recyclerView.setLayoutManager(manager);
        if (manager instanceof GridLayoutManager){
            recyclerView.addItemDecoration(new SpaceItemDecoration(space));
        }
        LayoutParams layoutParams=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(10,10,10,0);
        recyclerView.setLayoutParams(layoutParams);
        addView(recyclerView);


    }

    private void initVerticalView(Context context) {
        removeAllViews();
        setOrientation(HORIZONTAL);
        tabGroup = new LinearLayout(context);
        tabGroup.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        tabGroup.setOrientation(VERTICAL);
        tabGroup.setWeightSum(tabCount);
        addView(tabGroup);

        TabView tab = addTab(tabTextHint, true);
        tabGroup.addView(tab);
        tabList = new ArrayList<>();
        tabList.add(tab);
        for (int i = 1; i < tabCount; i++) {
            TabView nullTab = addTab("", false);
            tabList.add(nullTab);
            tabGroup.addView(nullTab);
            nullTab.setIndex(i);
        }

        indicatorView = new IndicatorView(context, tabCount);
        indicatorView.setBgColor(indicatorColor);
        indicatorView.setTabOrientation(VERTICAL);
        indicatorView.setLayoutParams(new LayoutParams(6, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(indicatorView);

        divisionLine = new View(context);
        divisionLine.setLayoutParams(new LayoutParams(
                2, LayoutParams.MATCH_PARENT));
        divisionLine.setBackgroundColor(context.getResources().getColor(R.color.gray_line));
        addView(divisionLine);

        recyclerView = new RecyclerView(context);
        recyclerView.setLayoutManager(manager);
        if (manager instanceof GridLayoutManager){
            recyclerView.addItemDecoration(new SpaceItemDecoration(space));
        }
        LayoutParams layoutParams=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(10,10,10,0);
        recyclerView.setLayoutParams(layoutParams);
        addView(recyclerView);
    }

    private TabView addTab(CharSequence text, boolean isSelected) {
        TabView tab = new TabView(mContext);
        if (tabOrientation == VERTICAL) {
            tab.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
            tab.setPadding(tabPadding/2, tabPadding/2, tabPadding/2, tabPadding/2);
        } else {
            tab.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            tab.setPadding(0, tabPadding, 0, tabPadding);
        }
        tab.setIsSelectedColor(tabIsSelectedColor);
        tab.setUnSelectedColor(tabUnSelectedColor);
        tab.setTabTextSize(tabTextSize);
        tab.setGravity(Gravity.CENTER);
        tab.setSelected(isSelected);
        tab.setText(text);
        tab.setOnClickListener(this);
        return tab;
    }


    //不能少于2个tab
    public void setTabCount(int tabCount) {
        if (tabCount >= 2) {
            this.tabCount = tabCount;
//            initHorizontalView(mContext);
        } else {
            throw new RuntimeException("MultipleSelector tab count at least more than 2");
        }
    }


    //设置列表数据
    public void setDataSource(ArrayList list) {
        if (list == null || list.size() <= 0)
            return;
        if (list.get(0) instanceof DataSourceInterface) {
            this.dataList = list;
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }else {
                adapter = new MultipleSelectorAdapter();
                recyclerView.setAdapter(adapter);
            }
        } else {
            throw new RuntimeException("DataSource must implement DataSourceInterface");
        }
    }

    //如果是网格布局 可以设置图片数据
    public void setImgSource(ArrayList list){
        if (list==null || list.size()==0)
            return;
        this.imgList=list;
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }else {
            adapter=new MultipleSelectorAdapter();
            recyclerView.setAdapter(adapter);
        }
    }

    /**
     * 使用默认的地址选择器
     * @param context
     * @param selector
     */
    public void userDefaultSelector(Context context,MultipleSelector selector){
        DefaultSelector defaultSelector=new DefaultSelector();
        defaultSelector.addressSelector(context,selector);
    }

    /**
     * 获取当前所有选中Item的值
     * @return
     */
    public List<String> getAllTabText(){
        ArrayList<String> allTabTextList=new ArrayList<>();
        for (int i = 0; i < tabList.size()-1; i++) {
            if (!(tabList.get(i).getText().toString()).equals(tabTextHint)){
                allTabTextList.add(tabList.get(i).getText().toString());
            }
        }
        return allTabTextList;
    }

    /**
     * 获取当前所有选中Item的对应的下标
     * @return
     */
    public List<Integer> getAllTabPosition(){
        ArrayList<Integer> allTabPositionList=new ArrayList<>();
        for (int i = 0; i < tabList.size()-1; i++) {
            if (!(tabList.get(i).getText().toString()).equals(tabTextHint)){
                allTabPositionList.add(i);
            }
        }
        return allTabPositionList;
    }

    private void resetAllTabs(int tabIndex) {
        if (tabList != null) {
            for (int i = 0; i < tabList.size(); i++) {
                tabList.get(i).resetStatus();
                if (i > tabIndex) {
                    tabList.get(i).resetTxt();
                }
            }
        }
    }


    private void resetAllTabStatus() {
        if (tabList != null) {
            for (int i = 0; i < tabList.size(); i++) {
                tabList.get(i).resetStatus();
                if (i == tabIndex) {
                    tabList.get(i).setStatus(tabList.get(i));
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        TabView tab = (TabView) v;
        if (TextUtils.isEmpty(tab.getText()))
            return;
        tabIndex = tab.getIndex();
        resetAllTabStatus();
        if (onTabItemSelectedListener != null) {
            if (tab.isSelected())
                onTabItemSelectedListener.TabItemReSelected(this, tab);
            else
                onTabItemSelectedListener.TabItemSelected(this, tab);
        }
        indicatorView.scrollView(tabIndex);
        tab.setSelected(true);
        Log.i(TAG, "tabIndex=" + tabIndex);
    }


    /**
     * 设置网格布局边距
     * @param space
     */
    public void setSpace(int space) {
        this.space = space;
    }

    /**
     * 设置列表布局方式
     *
     * @param manager
     */
    public void setManager(RecyclerView.LayoutManager manager) {
        this.manager = manager;
        if (manager instanceof GridLayoutManager) {
            isLinear = false;
            spanCount=((GridLayoutManager) manager).getSpanCount();
        }
    }

    public void setTabOrientation(int tabOrientation) {
        this.tabOrientation = tabOrientation;
    }

    /**
     * 设置tab选中文字颜色
     *
     * @param tabIsSelectedColor
     */
    public void setTabIsSelectedColor(int tabIsSelectedColor) {
        this.tabIsSelectedColor = Color.parseColor(getResources().getString(tabIsSelectedColor));
    }

    /**
     * 设置tab未选中的文字颜色
     *
     * @param tabUnSelectedColor
     */
    public void setTabUnSelectedColor(int tabUnSelectedColor) {
        this.tabUnSelectedColor = Color.parseColor(getResources().getString(tabUnSelectedColor));
    }

    /**
     * 设置tab文字大小
     *
     * @param tabTextSize
     */
    public void setTabTextSize(int tabTextSize) {
        this.tabTextSize = tabTextSize;
    }

    /**
     * 设置tab默认提示文字
     *
     * @param tabTextHint
     */
    public void setTabTextHint(String tabTextHint) {
        this.tabTextHint = tabTextHint;
    }

    /**
     * 设置Tab横线的颜色
     */
    public void setIndicatorColor(int lineColor) {
        this.indicatorColor = Color.parseColor(getResources().getString(lineColor));
    }

    /**
     * 设置tab上下内边距
     *
     * @param tabPadding
     */
    public void setTabPadding(int tabPadding) {
        this.tabPadding = tabPadding;
    }
    /**
     * 设置列表item选中颜色
     *
     * @param itemIsSelectedColor
     */
    public void setItemIsSelectedColor(int itemIsSelectedColor) {
        this.itemIsSelectedColor = Color.parseColor(getResources().getString(itemIsSelectedColor));
    }

    /**
     * 设置列表item未选择颜色
     *
     * @param itemUnSelectedColor
     */
    public void setItemUnSelectedColor(int itemUnSelectedColor) {
        this.itemUnSelectedColor = Color.parseColor(getResources().getString(itemUnSelectedColor));
    }

    /**
     * 设置列表item文字大小
     *
     * @param itemTextSize
     */
    public void setItemTextSize(int itemTextSize) {
        this.itemTextSize = itemTextSize;
    }

    /**
     * 设置列表item图片icon
     *
     * @param itemIconResource
     */
    public void setItemIconResource(int itemIconResource) {
        this.itemIconResource = itemIconResource;
    }

    class MultipleSelectorAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case LINEAR_LIST:
                    view = LayoutInflater.from(mContext).inflate(R.layout.select_linear_list_item_layout, parent, false);
                    return new MultipleSelectorLinearViewHolder(view);
                case GRID_LIST:
                    view = LayoutInflater.from(mContext).inflate(R.layout.select_grid_list_item_layout, parent, false);
                    return new MultipleSelectorGridViewHolder(view);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            if (holder instanceof MultipleSelectorLinearViewHolder) {
                if (itemIconResource != -1) {
                    ((MultipleSelectorLinearViewHolder) holder).itemIconImg.setImageResource(itemIconResource);
                }
                if (itemTextSize != -1) {
                    ((MultipleSelectorLinearViewHolder) holder).itemText.setTextSize(itemTextSize);
                }
                if (TextUtils.equals(tabList.get(tabIndex).getText(), dataList.get(position).getTextName())) {
                    ((MultipleSelectorLinearViewHolder) holder).itemIconImg.setVisibility(VISIBLE);
                    ((MultipleSelectorLinearViewHolder) holder).itemText.setTextColor(itemIsSelectedColor);
                } else {
                    ((MultipleSelectorLinearViewHolder) holder).itemIconImg.setVisibility(GONE);
                    ((MultipleSelectorLinearViewHolder) holder).itemText.setTextColor(itemUnSelectedColor);
                }
                ((MultipleSelectorLinearViewHolder) holder).itemText.setText(dataList.get(position).getTextName());
                ((MultipleSelectorLinearViewHolder) holder).itemView.setTag(dataList.get(position));
                ((MultipleSelectorLinearViewHolder) holder).itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onListItemSelectedListener != null) {
                            onListItemSelectedListener.ListItemSelected(MultipleSelector.this, (DataSourceInterface) v.getTag(), tabIndex,position);
                            tabList.get(tabIndex).setText(((DataSourceInterface) v.getTag()).getTextName());
                            tabList.get(tabIndex).setTag(v.getTag());
                            if (tabIndex + 1 < tabList.size()) {
                                tabIndex += 1;
                                resetAllTabs(tabIndex);//重制所有tab状态
                                indicatorView.scrollView(tabIndex);
                                tabList.get(tabIndex).setSelected(true);//设置当前tab为选中状态
                                tabList.get(tabIndex).setText(tabTextHint);
                            }
                        }
                    }
                });
            } else if (holder instanceof MultipleSelectorGridViewHolder) {
//                if (itemIconResource != -1) {
//                    ((MultipleSelectorGridViewHolder) holder).itemIconImg.setImageResource(itemIconResource);
//                }
                if (imgList!=null && imgList.size()>0){
                    if (position<imgList.size()-1){
                        if (imgList.get(position)!=null){
                            Glide.with(mContext)
                                    .load(imgList.get(position))
                                    .asBitmap()
                                    .placeholder(R.mipmap.loading)
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .error(R.mipmap.fail)
                                    .centerCrop()
                                    .into(((MultipleSelectorGridViewHolder) holder).itemIconImg);
                        }
                    }else {
                        ((MultipleSelectorGridViewHolder) holder).itemIconImg.setImageResource(R.drawable.ic_no_image_bg);}
                }else {
                    ((MultipleSelectorGridViewHolder) holder).itemIconImg.setVisibility(GONE);}


                if (itemTextSize != -1) {
                    ((MultipleSelectorGridViewHolder) holder).itemText.setTextSize(itemTextSize);
                }
                if (TextUtils.equals(tabList.get(tabIndex).getText(), dataList.get(position).getTextName())) {
                    ((MultipleSelectorGridViewHolder) holder).itemText.setTextColor(itemIsSelectedColor);
                } else {
                    ((MultipleSelectorGridViewHolder) holder).itemText.setTextColor(itemUnSelectedColor);
                }
                ((MultipleSelectorGridViewHolder) holder).itemText.setText(dataList.get(position).getTextName());
                ((MultipleSelectorGridViewHolder) holder).itemView.setTag(dataList.get(position));
                ((MultipleSelectorGridViewHolder) holder).itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onListItemSelectedListener != null) {
                            onListItemSelectedListener.ListItemSelected(MultipleSelector.this, (DataSourceInterface) v.getTag(), tabIndex,position);
                            tabList.get(tabIndex).setText(((DataSourceInterface) v.getTag()).getTextName());
                            Log.i(TAG, "onClick: " + ((DataSourceInterface) v.getTag()).getTextName());
                            tabList.get(tabIndex).setTag(v.getTag());
                            if (tabIndex + 1 < tabList.size()) {
                                tabIndex += 1;
                                resetAllTabs(tabIndex);//重制所有tab状态
                                indicatorView.scrollView(tabIndex);
                                tabList.get(tabIndex).setSelected(true);//设置当前tab为选中状态
                                tabList.get(tabIndex).setText(tabTextHint);
                            }
                        }
                    }
                });
            }

        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return isLinear ? LINEAR_LIST : GRID_LIST;
        }

        class MultipleSelectorLinearViewHolder extends RecyclerView.ViewHolder {

            private ImageView itemIconImg;
            private TextView itemText;
            private View itemView;

            public MultipleSelectorLinearViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                itemIconImg = (ImageView) itemView.findViewById(R.id.itemIconImg);
                itemText = (TextView) itemView.findViewById(R.id.itemText);
            }

        }

        class MultipleSelectorGridViewHolder extends RecyclerView.ViewHolder {
            private ImageView itemIconImg;
            private TextView itemText;
            private View itemView;

            public MultipleSelectorGridViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                itemIconImg = (ImageView) itemView.findViewById(R.id.itemIconImg);
                itemText = (TextView) itemView.findViewById(R.id.itemText);
            }
        }


    }

     class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //不是第一个的格子都设一个左边和底部的间距
            outRect.left = space;
            outRect.bottom = space*3;
            //由于每行都只有spanCount个，所以第一个都是spanCount的倍数，把左边距设为0
            if (parent.getChildLayoutPosition(view) % spanCount==0) {
                outRect.left = 0;
            }
        }

    }


}
