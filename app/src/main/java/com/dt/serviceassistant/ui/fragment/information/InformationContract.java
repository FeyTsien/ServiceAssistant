package com.dt.serviceassistant.ui.fragment.information;

import com.dt.serviceassistant.bean.MessageBean;
import com.dt.serviceassistant.mvp.BasePresenter;
import com.dt.serviceassistant.mvp.BaseView;

public class InformationContract {
    interface View extends BaseView {

        void getNewsSuccess(MessageBean messageBean);

        void getNewsFail(String error);
    }

    interface  Presenter extends BasePresenter<View> {
        void getNews(String userId);
    }
}
