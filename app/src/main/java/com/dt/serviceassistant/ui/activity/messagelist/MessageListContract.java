package com.dt.serviceassistant.ui.activity.messagelist;

import com.dt.serviceassistant.bean.MessageBean;
import com.dt.serviceassistant.mvp.BasePresenter;
import com.dt.serviceassistant.mvp.BaseView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MessageListContract {
    public interface View extends BaseView {

        void getMessageListSuccess(MessageBean messageBean);

        void getMessageListFail(String error);

    }

    interface Presenter extends BasePresenter<View> {

        //获取消息列表

        void getMessageList(String url,String JsonData);

    }
}
