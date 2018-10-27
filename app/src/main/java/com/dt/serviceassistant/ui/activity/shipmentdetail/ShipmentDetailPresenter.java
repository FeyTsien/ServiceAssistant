package com.dt.serviceassistant.ui.activity.shipmentdetail;

import android.app.ProgressDialog;
import android.text.TextUtils;

import com.blankj.utilcode.util.NetworkUtils;
import com.dt.serviceassistant.bean.AppBean;
import com.dt.serviceassistant.manager.HttpManager;
import com.dt.serviceassistant.mvp.BasePresenterImpl;
import com.dt.serviceassistant.utils.CommonUtils;
import com.dt.serviceassistant.utils.UrlUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ShipmentDetailPresenter extends BasePresenterImpl<ShipmentDetailContract.View> implements ShipmentDetailContract.Presenter {

    @Override
    public void addShipment(String jsonData) {

    }
}
