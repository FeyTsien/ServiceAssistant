package com.dt.serviceassistant.ui.fragment.message;

import com.dt.serviceassistant.bean.MessageBean;
import com.dt.serviceassistant.mvp.BasePresenter;
import com.dt.serviceassistant.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MessageContract {
    interface View extends BaseView {

        void getTpyeMessagesSuccess(MessageBean messageBean);

        void getTpyeMessagesFail(String error);

    }

    interface  Presenter extends BasePresenter<View> {

        void getTpyeMessages(String userId);
    }
}
