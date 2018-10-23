package com.dt.serviceassistant.manager;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2017/4/21.
 */

public class GsonManger {
    public static GsonManger gsonManger = new GsonManger();
    private final Gson gson;

    public static GsonManger getGsonManger() {
        return gsonManger;
    }

    public String toJson(List<String> list) {
        String str = gson.toJson(list);
        return str;
    }

    public Object gsonFromat(String s, Object o) {
        Object o1 = gson.fromJson(s, o.getClass());
        return o1;
    }

    private GsonManger() {
        gson = new Gson();
    }
}
