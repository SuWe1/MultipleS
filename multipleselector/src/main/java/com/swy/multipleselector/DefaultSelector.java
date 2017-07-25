package com.swy.multipleselector;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.swy.multipleselector.bean.Address;
import com.swy.multipleselector.interfaces.DataSourceInterface;
import com.swy.multipleselector.interfaces.OnListItemSelectedListener;
import com.swy.multipleselector.interfaces.OnTabItemSelectedListener;
import com.swy.multipleselector.view.TabView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Swy on 2017/7/24.
 */

public class DefaultSelector {
    private ArrayList<Address.CitylistBean> cities1 = new ArrayList<>();
    private ArrayList<Address.CitylistBean.CBean> cities2 = new ArrayList<>();
    private ArrayList<Address.CitylistBean.CBean.ABean> cities3 = new ArrayList<>();
    Address address;

    public void addressSelector(final Context context, MultipleSelector selector){
        getCityJson(context);
        cities1= (ArrayList<Address.CitylistBean>) address.getCitylist();
        selector.setTabCount(3);
        selector.setManager(new LinearLayoutManager(context));
        selector.show();
        selector.setDataSource(cities1);
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
    }

    private void getCityJson(Context c){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager manager = c.getAssets();
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
    }


}
