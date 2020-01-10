package com.dt.serviceassistant.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.dt.serviceassistant.R;
import com.dt.serviceassistant.bean.TimeLineBean;
import com.dt.serviceassistant.mvp.MVPActivity;
import com.dt.serviceassistant.mvp.MVPContract;
import com.dt.serviceassistant.mvp.MVPPresenter;
import com.dt.serviceassistant.ui.adapter.TimeLineAdapter;
import com.dt.serviceassistant.utils.AmapUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class WaybillDetailsActivity extends MVPActivity<MVPContract.View, MVPPresenter> implements MVPContract.View {

    //AMap是地图对象
    private AMap aMap;
    private ArrayList<TimeLineBean> mTimeLineList;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.nestedScrollView)
    View bottomSheet;
    @BindView(R.id.fab_my_location)
    FloatingActionButton fabMyLocation;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private BottomSheetBehavior<View> bottomSheetBehavior;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_waybill_details;
    }

    @Override
    protected void initData() {
        mTimeLineList = new ArrayList<>();
        mTimeLineList.add(new TimeLineBean("Item successfully delivered", "", TimeLineBean.OrderStatus.INACTIVE));
        mTimeLineList.add(new TimeLineBean("Courier is out to delivery your order", "2017-02-12 08:00", TimeLineBean.OrderStatus.ACTIVE));
        mTimeLineList.add(new TimeLineBean("Item has reached courier facility at New Delhi", "2017-02-11 21:00", TimeLineBean.OrderStatus.COMPLETED));
        mTimeLineList.add(new TimeLineBean("Item has been given to the courier", "2017-02-11 18:00", TimeLineBean.OrderStatus.COMPLETED));
        mTimeLineList.add(new TimeLineBean("Item is packed and will dispatch soon", "2017-02-11 09:30", TimeLineBean.OrderStatus.COMPLETED));
        mTimeLineList.add(new TimeLineBean("Order is being readied for dispatch", "2017-02-11 08:00", TimeLineBean.OrderStatus.COMPLETED));
        mTimeLineList.add(new TimeLineBean("Order processing initiated", "2017-02-10 15:00", TimeLineBean.OrderStatus.COMPLETED));
        mTimeLineList.add(new TimeLineBean("Order confirmed by seller", "2017-02-10 14:30", TimeLineBean.OrderStatus.COMPLETED));
        mTimeLineList.add(new TimeLineBean("Order placed successfully", "2017-02-10 14:00", TimeLineBean.OrderStatus.COMPLETED));
    }

    int maxHeight;

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        setToolBar(mToolbar, mTvTitle, "运单详情");
        initMap(savedInstanceState);
        initBottomSheet();
        initTimeLine();
    }

    List<LatLng> latLngs = new ArrayList<LatLng>();
    private void initMap(@Nullable Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        latLngs.add(new LatLng(39.999391, 116.135972));
        latLngs.add(new LatLng(39.898323, 116.057694));
        latLngs.add(new LatLng(39.900430, 116.265061));

        AmapUtils.setMarker(aMap,"大象网吧",39.999391,116.135972,1);
        AmapUtils.setMarker(aMap,"小猴网吧",39.898323,116.057694,2);
        AmapUtils.setMarker(aMap,"网鱼网咖",39.900430,116.265061,2);

        AmapUtils.showMapInScreen(aMap,latLngs.get(0),latLngs.get(2),latLngs.get(1));
        aMap.setMapTextZIndex(2);
        aMap.addPolyline((new PolylineOptions())
                //手动数据测试
                //.add(new LatLng(26.57, 106.71),new LatLng(26.14,105.55),new LatLng(26.58, 104.82), new LatLng(30.67, 104.06))
                //集合数据
                .addAll(latLngs)
                //线的宽度
                .width(10).setDottedLine(true).geodesic(true)
                //颜色
                .color(Color.argb(255, 255, 20, 147)));

    }

    private void initBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        int peekHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        bottomSheetBehavior.setPeekHeight(peekHeight);//设置最小高度
        bottomSheetBehavior.setHideable(false);//设置是否可隐藏,false不可隐藏
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);//设置当前为完全展开状态

        final View fabContainer = findViewById(R.id.fab_container);
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) fabContainer.getLayoutParams();
        lp.setMargins(0, 0, 0, peekHeight);
        fabContainer.setLayoutParams(lp);


        //获取屏幕的高度
        int heightPixels = getResources().getDisplayMetrics().heightPixels;
        final View llTop = findViewById(R.id.ll_top);
        llTop.post(new Runnable() {
            @Override
            public void run() {
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) llTop.getLayoutParams();
                //底部列表的最大高度
                maxHeight = heightPixels - llTop.getMeasuredHeight();
            }
        });

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
                if (bottomSheet.getHeight() > maxHeight) {
                    layoutParams.height = maxHeight;
                    bottomSheet.setLayoutParams(layoutParams);
                }
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        //折叠状态,就是peekHeight 设置的窥视高度
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        //完全展开
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        //拖动中
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        //拖动/滑动手势后，将稳定到特定高度。如果用户操作导致底部页面隐藏，则这将是峰值高度，扩展高度或0。
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        //隐藏
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                float distance;
                if (slideOffset > 0) {//在peekHeight位置以上 滑动(向上、向下) slideOffset bottomSheet.getHeight() 是展开后的高度的比例
                    distance = bottomSheet.getHeight() * slideOffset;
                } else {//在peekHeight位置以下 滑动(向上、向下)  slideOffset 是PeekHeight的高度的比例
                    distance = bottomSheetBehavior.getPeekHeight() * slideOffset;
                }
                if (distance < 0) {
                    fabContainer.setTranslationY(-distance);
                    mapView.setTranslationY(0);
                } else {
                    if (distance <= peekHeight) {
                        fabContainer.setTranslationY(-distance);
                        mapView.setTranslationY(-distance);
                    }
                }
            }
        });
    }

    private void initTimeLine(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        TimeLineAdapter timeLineAdapter = new TimeLineAdapter(this,mTimeLineList);
        recyclerView.setAdapter(timeLineAdapter);
    }

    @OnClick({R.id.fab_my_location})
    public void onClicks(View view){
        switch (view.getId()){
            case R.id.fab_my_location:
                AmapUtils.showMapInScreen(aMap,latLngs.get(0),latLngs.get(2),latLngs.get(1));
                break;
        }
    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
