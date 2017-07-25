package com.swy.multiples;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.swy.multipleselector.MultipleSelector;
import com.swy.multipleselector.interfaces.onDialogListener;


/**
 * Created by Swy on 2017/7/24.
 */

public class MultipleSelectorDialog extends Dialog {

    private Context mContext;
    public MultipleSelector selector;
    private TextView dialogTitle;
    private ImageView dialogImg;

    private onDialogListener listener;

    private static final String TAG = "MultipleSelectorDialog";

    public MultipleSelectorDialog(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public MultipleSelectorDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    protected MultipleSelectorDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }


    private void initView(Context context){
        setContentView(R.layout.multipleselector_dialog_main_layout);
        selector= (MultipleSelector) findViewById(R.id.dialogMultipleSelector);
        dialogTitle= (TextView) findViewById(R.id.dialogTitle);
        dialogImg= (ImageView) findViewById(R.id.dialogImg);
        dialogImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onDismissClick(selector.getAllTabText());
                    Log.i(TAG, "onClick: "+selector.getAllTabText().size());
                }
                dismiss();
            }
        });
//        multipleSelector=selector;
        selector.setTabPadding(20);
        selector.userDefaultSelector(context,selector);
        Window window=getWindow();
        WindowManager.LayoutParams params=window.getAttributes();
        params.width=WindowManager.LayoutParams.MATCH_PARENT;
        params.height=getWidth(context);
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);

    }

    public void setOnDialogListener (onDialogListener listener){
        this.listener=listener;
    }

    private static int getWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight()*2/3;
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }


    public  MultipleSelectorDialog show(Context context){
        MultipleSelectorDialog dialog=new MultipleSelectorDialog(context, com.swy.multipleselector.R.style.MultipleSelectorDialog);
        dialog.show();
        return dialog;
    }
    /**
     * 设置tab选中文字颜色
     *
     * @param tabIsSelectedColor
     */
    public void setTabIsSelectedColor(int tabIsSelectedColor) {
        selector.setTabIsSelectedColor(tabIsSelectedColor);
    }

    /**
     * 设置tab未选中的文字颜色
     *
     * @param tabUnSelectedColor
     */
    public void setTabUnSelectedColor(int tabUnSelectedColor) {
        selector.setTabUnSelectedColor(tabUnSelectedColor);
    }

    /**
     * 设置tab文字大小
     *
     * @param tabTextSize
     */
    public void setTabTextSize(int tabTextSize) {
        selector.setTabTextSize(tabTextSize);
    }

    /**
     * 设置Tab横线的颜色
     */
    public void setIndicatorColor(int lineColor) {
        selector.setIndicatorColor(lineColor);
    }

    /**
     * 设置列表item选中颜色
     *
     * @param itemIsSelectedColor
     */
    public void setItemIsSelectedColor(int itemIsSelectedColor) {
        selector.setItemIsSelectedColor(itemIsSelectedColor);
    }

    /**
     * 设置列表item未选择颜色
     *
     * @param itemUnSelectedColor
     */
    public void setItemUnSelectedColor(int itemUnSelectedColor) {
        selector.setItemUnSelectedColor(itemUnSelectedColor);
    }

    /**
     * 设置列表item文字大小
     *
     * @param itemTextSize
     */
    public void setItemTextSize(int itemTextSize) {
        selector.setItemTextSize(itemTextSize);
    }

    /**
     * 设置列表item图片icon
     *
     * @param itemIconResource
     */
    public void setItemIconResource(int itemIconResource) {
        selector.setItemIconResource(itemIconResource);
    }
}
