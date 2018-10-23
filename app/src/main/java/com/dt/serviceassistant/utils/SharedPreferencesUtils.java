package com.dt.serviceassistant.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.dt.serviceassistant.app.MyApplication;

public class SharedPreferencesUtils {

    public static String SP_NAME = "config";
    private static SharedPreferences sp;

    /**
     * SharedPreferences 保存boolean
     *
     * @param key
     * @param value
     */
    public static void saveBoolean(String key, boolean value) {
        if (sp == null) {
            sp = MyApplication.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        if (sp == null) {
            sp = MyApplication.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }


    /**
     * SharedPreferences 保存字符串
     *
     * @param key
     * @param value
     */
    public static void saveString(String key, String value) {
        if (sp == null) {
            sp = MyApplication.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }

    public static String getString(String key, String defValue) {
        if (sp == null) {
            sp = MyApplication.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }
    /**
     * SharedPreferences 保存int类型数据
     *
     * @param key
     * @param value
     */
    public static void saveInt(String key, int value) {
        if (sp == null) {
            sp = MyApplication.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).commit();
    }

    public static int getInt(String key, int defValue) {
        if (sp == null) {
            sp = MyApplication.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return sp.getInt(key, defValue);
    }

}
