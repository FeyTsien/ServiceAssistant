package com.dt.serviceassistant.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.dt.serviceassistant.R;

public class AmapUtils {


    public static void showMapInScreen(AMap aMap, LatLng startLatlng, LatLng endLatlng, LatLng realLatlng) {//显示目标mark与手机屏幕上
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        if (realLatlng != null) {
            builder.include(realLatlng);
        }
        if (startLatlng != null) {
            builder.include(startLatlng);
        }
        if (endLatlng != null) {
            builder.include(endLatlng);
        }
        LatLngBounds bounds = builder.build();
        aMap.moveCamera(CameraUpdateFactory.newLatLngBoundsRect(bounds, DeviceInfo.getInstance().width / 5, DeviceInfo.getInstance().width / 5, DeviceInfo.getInstance().height / 4, DeviceInfo.getInstance().height / 2));
    }

    public static void setMarker(AMap aMap, String barname, double wd, double jd, int mid) {
        LayoutInflater mInflater = LayoutInflater.from(DeviceInfo.getInstance().getContext());
        View view = mInflater.inflate(R.layout.mark_amap_wayy_bill, null);
        TextView textView = view.findViewById(R.id.tete);
        textView.setText(barname);

        Bitmap bitmap = convertViewToBitmap(view);

        //绘制marker
        Marker marker2 = aMap.addMarker(new MarkerOptions()
                .position(new LatLng(wd, jd)).period(mid)//添加markerID
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                .draggable(false));
    }

    public static Bitmap convertViewToBitmap(View view) {

        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.buildDrawingCache();

        Bitmap bitmap = view.getDrawingCache();

        return bitmap;

    }
}
