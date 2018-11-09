# Android联动选择控件——MultipleS
[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](https://github.com/yeshuwei/MultipleS/blob/master/LICENSE)
[![Travis](https://img.shields.io/travis/rust-lang/rust.svg)](https://travis-ci.org/yeshuwei/MultipleS)
[![Hex.pm](https://img.shields.io/badge/Download-1.0.0-orange.svg)](https://github.com/yeshuwei/MultipleS)

一款Android的联动选择控件，类似京东地址选择器，内置默认地址选择器，可自定义数据源,自定义属性，自定义布局走向的控件

### 特点
- [x] Tab效果类似TabLayout **&&** 内容列表RecyclerView
- [x] Tab和列表都是可重复点击的 带动画效果，属性自定义
- [x] 多种布局方式(Tab横向和纵向,内容列表线性和网格)
- [x] 数据自定义，根据不同数据显示不同内容
### 部分效果图
(ps:看官们忽略数据内容和图片)<br>

![](http://oquj35wa4.bkt.clouddn.com/multiples1.gif)
![](http://oquj35wa4.bkt.clouddn.com/multiples_dialog1.gif)<br>

![](http://oquj35wa4.bkt.clouddn.com/multiples.change_color1.gif)
![](http://oquj35wa4.bkt.clouddn.com/multiples_tab_horizontal_list_grid1.gif)<br>

![](http://oquj35wa4.bkt.clouddn.com/multiples_tab_vertical_list_linear1.gif)
![](http://oquj35wa4.bkt.clouddn.com/multiples_tab_vertical_list_grid1.gif)

## 介绍
### 顶部Tab常量

常量名 | 描述 | 方法
:--: | :--: | :--:
tabIsSelectedColor| Tab选中时的颜色|setTabIsSelectedColor
tabUnSelectedColor | Tab未选中时的颜色|setTabUnSelectedColor
tabTextSize|Tab字体大小 | setTabTextSize
tabPadding | Tab上下边距 | setTabPadding
tabCount | Tab总个数 | setTabCount
tabTextHint | Tab文字提示 | setTabTextHint
tabOrientation |Tab横纵走向 | setTabOrientation
indicatorColor |　分割线颜色|setIndicatorColor

### 内容列表常量
常量名 | 描述 | 方法
:--: | :--: | :--:
itemIsSelectedColor　|选中时文字颜色 |setItemIsSelectedColor
itemUnSelectedColor | 选中时未文字颜色 | setItemUnSelectedColor
itemTextSize | 字体大小 | setItemTextSize
itemIconResource | 选中时右侧图片 | setTtemIconResource

### 方法
方法名 | 描述 | 限制
:--: | :--: | :--:
userDefaultSelector(Context context,MultipleSelector selector) | 使用内置默认地址选择器 | 其他属性设置方法应该在其之前调用 
setManager(RecyclerView.LayoutManager manager) | 设置列表布局方式 | 无
show() | 显示视图 | 其他属性设置方法必须在其之前调用
show(int orientation) | 设置Tab横纵走向(默认HORIZONTAL) | 其他属性设置方法必须在其之前调用
setTabCount(int tabCount) | 设置Tab个数 | 至少大于两个
setDataSource(ArrayList list) | 设置数据源 | 数据源必须实现DataSourceInterface接口
setImgSource(ArrayList list) | 设置图片数据源 | 数据源必须实现DataSourceInterface接口
getAllTabText() | 获取当前所有选中Item的值 | 无
getAllTabPosition() | 获取当前所有选中Item的对应的下标 | 无
setSpace(int space) |设置网格布局时的边距 | 无

## 使用步骤

### 添加依赖

Moudle:Grade<br>

```
compile 'com.swy:multipleselector:1.0.0'
```

### 添加权限到 AndroidManifest.xml

```
<!-- if you want to load images from the internet -->
<uses-permission android:name="android.permission.INTERNET" /> 
```

### 布局文件中添加

```
<com.swy.multipleselector.MultipleSelector
        android:id="@+id/select"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```

### 使用默认地址选择器

```
        MultipleSelector selector= (MultipleSelector) findViewById(R.id.select);
        ....//这里可进行相关属性常量设置
        selector.setTabIsSelectedColor(R.color.itemIsSelect);//true
```
### 自定义使用

> 所有的属性设置方法应该在show()之前调用，而setDataSource()/setImgSource()方法则应该在show()方法之后调用。

> 本项目为在学期间完成。现在看来，扩展性太差！有机会会进行重构。

```
MultipleSelector selector= (MultipleSelector) findViewById(R.id.select);//必须
....//其他相关属性常量设置
selector.setManager(new LinearLayoutManager(this));//必须
selector.show();//必须
selector.setDataSource(cities1);//必须
        selector.setImgSource(imgList);//可选  线性布局则显示默认 网格布局则不显示  图片数量和数据数量不对等时使用默认图片显示
selector.setOnListItemSelectedListener(new OnListItemSelectedListener() {
            @Override
            public void ListItemSelected(MultipleSelector selector, DataSourceInterface dataSourceInterface, int tabPosition, int position) {
                switch (tabPosition){
                    case 0:
                        selector.setDataSource(xxx;
                        break;
                    case 1:
                        selector.setDataSource(yyy);
                        break;
                    case 2:
                        ccc
                        break;
                    default:
                        break;
                }
            }
        });//列表点击时间 跟换数据
selector.setOnTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void TabItemSelected(MultipleSelector selector, TabView tabView) {
                switch (tabView.getIndex()){
                    case 0:
                        selector.setDataSource(xxx);
                        break;
                    case 1:
                        selector.setDataSource(yyy);
                        break;
                    case 2:
                        selector.setDataSource(ccc);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void TabItemReSelected(MultipleSelector selector, TabView tabView) {
                switch (tabView.getIndex()){
                    case 0:
                        selector.setDataSource(xxx);
                        break;
                    case 1:
                        selector.setDataSource(yyy);
                        break;
                    case 2:
                        selector.setDataSource(ccc);
                        break;
                    default:
                        break;
                }
            }
        });
```


### 关于我
大学狗一枚，欢迎联系我和我一起讨论

- [个人博客](http://www.swyiww.com/)
- [简书](http://www.jianshu.com/u/07886c430033)
- [CSDN](http://blog.csdn.net/qq_26309061)
