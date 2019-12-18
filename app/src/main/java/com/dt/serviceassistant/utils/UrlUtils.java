package com.dt.serviceassistant.utils;

/**
 * Created by Administrator on 2017/4/10.
 */

public class UrlUtils {

    //app主链接服务器
    public static final String BASEURL = "http://27.191.130.130:9001/dtApi/";
    //本地测试使用
//    public static final String BASEURL = "http://192.168.3.72:9001/dtApi/";
//    public static final String BASEURL = "https://easydoc.xyz/mock/";

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

    //员工获取任务列表
    public static final String GET_TASKS_LIST = "user/getTasks.do";
    //员工处理任务
    public static final String DO_WITH_TASK = "user/doWithTasks.do";
    //获取员工发货列表
    public static final String GET_STAFF_SHIPMENT_LIST = "user/getAllGoods.do";
    //获取员工保险列表
    public static final String GET_STAFF_INSURANCE_LIST = "user/getAllInsurances.do";


    //老板界面（0:业务员分析;1:客户分析；4实时库存;5:船舶分析 6:任务信息）
    public static final String BOSS_ANALYSIS_LIST = "boss/bossAnalysisList.do";
    //老板界面（2：应收账款;3:资金分析; ）
    public static final String BOSS_ANALYSIS_SINGLE = "boss/bossAnalysisSingle.do";
    //业务员分析
    public static final String BOSS_STAFF_ANALYSIS = "boss/staffAnalysis";
    //实时库存
    public static final String BOSS_TIMELY_INVENTORY = "boss/timelyInventory";
    //实时船运
    public static final String BOSS_SHIP_ANALYSIS = "boss/shipAnalysis";
    //客户分析
    public static final String BOSS_CUSTOMER_ANALYSIS = "boss/customerAnalysis.do";

    //资金分析
    public static final String BOSS_FUNDS_ANALYSIS = "boss/fundsAnalysis.do";

    //综合分析
    public static final String BOSS_COMPLEX_ANALYSIS = "boss/complexAnalysis.do";

}
