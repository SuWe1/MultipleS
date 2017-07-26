package com.swy.multiples;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.swy.multipleselector.MultipleSelector;
import com.swy.multipleselector.bean.Address;
import com.swy.multipleselector.interfaces.DataSourceInterface;
import com.swy.multipleselector.interfaces.OnListItemSelectedListener;
import com.swy.multipleselector.interfaces.OnTabItemSelectedListener;
import com.swy.multipleselector.view.TabView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ArrayList<Address.CitylistBean> cities1 = new ArrayList<>();
    private ArrayList<Address.CitylistBean.CBean> cities2 = new ArrayList<>();
    private ArrayList<Address.CitylistBean.CBean.ABean> cities3 = new ArrayList<>();
    private ArrayList<String> imgList=new ArrayList<>();
    Address address;
//    MultipleSelectorDialog dialog;

    List<String> arraylist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager manager = getAssets();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(manager.open("city.json")));
            String line = "";
            while ((line = bufferedReader.readLine())!=null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //得到json的字符串
        String json = stringBuilder.toString();
        Gson gson=new Gson();
        address=gson.fromJson(json,Address.class);
        for (int i = 0; i < 10; i++) {
            imgList.add("http://oquj35wa4.bkt.clouddn.com/picture.png");
        }

//        MultipleSelector selector= (MultipleSelector) findViewById(R.id.select);
//        selector.userDefaultSelector(this,selector);

        MultipleSelector selector= (MultipleSelector) findViewById(R.id.select);
//        selector.setItemIsSelectedColor(R.color.colorPrimary);//true
//        selector.setItemUnSelectedColor(R.color.itemUnSelect);//true
//        selector.setTabIsSelectedColor(R.color.colorPrimary);//true
//        selector.setTabUnSelectedColor(R.color.itemUnSelect);//true
//        selector.setIndicatorColor(R.color.colorPrimary);
        selector.setTabCount(8);//true
//        selector.setItemIconResource(R.mipmap.ic_launcher);//true
//        selector.setTabTextSize(20);//true
//        selector.setTabTextHint("快选啊");//true
//        selector.setTabPadding(30);//true
//        selector.setManager(new GridLayoutManager(this,3));
        selector.setManager(new LinearLayoutManager(this));
        selector.setTabOrientation(LinearLayout.VERTICAL);
        selector.show();
        cities1= (ArrayList<Address.CitylistBean>) address.getCitylist();
        selector.setDataSource(cities1);
//        selector.setImgSource(imgList);
        selector.setOnListItemSelectedListener(new OnListItemSelectedListener() {
            @Override
            public void ListItemSelected(MultipleSelector selector, DataSourceInterface dataSourceInterface, int tabPosition, int position) {
                switch (tabPosition){
                    case 0:
                        cities2= (ArrayList<Address.CitylistBean.CBean>) cities1.get(position).getC();
                        selector.setDataSource(cities2);
                        break;
                    case 1:
                        cities3= (ArrayList<Address.CitylistBean.CBean.ABean>) cities2.get(position).getA();
                        selector.setDataSource(cities3);
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this,"tabPosition ："+tabPosition+" "+dataSourceInterface.getTextName(), Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
        selector.setOnTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void TabItemSelected(MultipleSelector selector, TabView tabView) {
                switch (tabView.getIndex()){
                    case 0:
                        selector.setDataSource(cities1);
                        break;
                    case 1:
                        selector.setDataSource(cities2);
                        break;
                    case 2:
                        selector.setDataSource(cities3);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void TabItemReSelected(MultipleSelector selector, TabView tabView) {
                switch (tabView.getIndex()){
                    case 0:
                        selector.setDataSource(cities1);
                        break;
                    case 1:
                        selector.setDataSource(cities2);
                        break;
                    case 2:
                        selector.setDataSource(cities3);
                        break;
                    default:
                        break;
                }
            }
        });

//        dialog=new MultipleSelectorDialog(this);
//        dialog.setOnDialogListener(new onDialogListener() {
//            @Override
//            public void onDismissClick(List<String> list) {
//                arraylist.addAll(dialog.selector.getAllTabText());
//                Log.i(TAG, "onCreate: "+arraylist.size());
//            }
//        });
//        dialog.show(this);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(dialog!=null){
//            dialog.dismiss();
//            dialog=null;
//        }
    }
}
