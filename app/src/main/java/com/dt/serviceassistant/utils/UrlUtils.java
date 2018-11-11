package com.dt.serviceassistant.utils;

/**
 * Created by Administrator on 2017/4/10.
 */

public class UrlUtils {

    //app主链接
    public static final String BASEURL = "http://114.215.93.127:9001/dtApi/";
    //根据域名获取IP地址的链接
    public static final String GET_IP_URL = "http://ip-api.com/json/";

    //=================接口=================
    //登录
    public static final String LOGIN = "user/login.do";
    //注册
    public static final String REGISTER = "api/register";
    //注册
    public static final String DO_CHECK_IN = "api/doCheckIn";

    //消息类型
    public static final String GET_MESSAGE_TYPES = "message/getTypeMessages.do";
    //消息列表
    public static final String GET_MESSAGE_LIST = "message/getMessages.do";
    //资讯列表
    public static final String GET_NEWS_LIST = "news/getNews.do";
    //发货列表
    public static final String GET_SHIPMENT_LIST = "goods/getGoods.do";
    //增加发货
    public static final String ADD_SHIPMENT = "goods/addGoods.do";
    //保险列表
    public static final String GET_INSURANCE_LIST = "insurance/getInsurances.do";
    //添加保险
    public static final String ADD_INSURANCE = "insurance/addInsurance.do";


    //老板界面（0:业务员分析;1:客户分析；4实时库存;5:船舶分析 6:任务信息）
    public static final String BOSS_ANALYSIS_LIST = "boss/bossAnalysisList.do";
    //老板界面（2：应收账款;3:资金分析; ）
    public static final String BOSS_ANALYSIS_SINGLE = "boss/bossAnalysisSingle.do";

}
