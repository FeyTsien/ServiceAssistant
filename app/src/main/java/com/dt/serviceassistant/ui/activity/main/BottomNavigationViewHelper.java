package com.dt.serviceassistant.ui.activity.main;

import android.annotation.SuppressLint;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;

public class BottomNavigationViewHelper {
    /**
     * @param view
     * @param goneOptions 是需要隐藏的Item的下标数组
     */
    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view, int... goneOptions) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);


        for (int opt : goneOptions) {
            menuView.removeViewAt(opt);//去掉不需要展示的menu
        }

        for (int i = 0; i < menuView.getChildCount(); i++) {

            BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
            item.setChecked(item.getItemData().isChecked());
        }

    }
}