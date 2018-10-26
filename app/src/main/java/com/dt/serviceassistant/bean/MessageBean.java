package com.dt.serviceassistant.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MessageBean extends CodeBean {


    /**
     * code : 1
     * data : [{"content":"2018年9月19日集港12吨","messagecount":5,"messagetype":0,"mtimage":"201409880823402","rtime":"2018-10-26 02:35","typename":"集港消息"},{"content":"你的货物已于2018年9月24日乘船泰达110起运。","messagecount":5,"messagetype":1,"mtimage":"201409880823402","rtime":"2018-10-26 02:35","typename":"起运消息"},{"content":"船泰达140发布船讯，请查看。","messagecount":5,"messagetype":2,"mtimage":"201409880823402","rtime":"2018-10-26 02:35","typename":"船讯消息"},{"content":"船泰达110已于2018年10月5日到港","messagecount":5,"messagetype":3,"mtimage":"201409880823402","rtime":"2018-10-26 02:35","typename":"靠港消息"},{"content":"鼎天发布了10月份的对账单，请及时核对。","messagecount":5,"messagetype":4,"mtimage":"201409880823402","rtime":"2018-10-26 02:35","typename":"对账消息"},{"content":"船泰达110于10月5日启航，有部分仓位。需要及时联系。","messagecount":5,"messagetype":5,"mtimage":"201409880823402","rtime":"2018-10-26 02:35","typename":"鼎天消息"}]
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * content : 2018年9月19日集港12吨
         * messagecount : 5
         * messagetype : 0
         * mtimage : 201409880823402
         * rtime : 2018-10-26 02:35
         * typename : 集港消息
         */

        private int id;
        private int isclick;
        private String content;
        private int messagecount;
        private int messagetype;
        private String mtimage;
        private String rtime;
        private String typename;
        private String url;
        private String ntitle;
        private String mimage;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsclick() {
            return isclick;
        }

        public void setIsclick(int isclick) {
            this.isclick = isclick;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getMessagecount() {
            return messagecount;
        }

        public void setMessagecount(int messagecount) {
            this.messagecount = messagecount;
        }

        public int getMessagetype() {
            return messagetype;
        }

        public void setMessagetype(int messagetype) {
            this.messagetype = messagetype;
        }

        public String getMtimage() {
            return mtimage;
        }

        public void setMtimage(String mtimage) {
            this.mtimage = mtimage;
        }

        public String getRtime() {
            return rtime;
        }

        public void setRtime(String rtime) {
            this.rtime = rtime;
        }

        public String getTypename() {
            return typename;
        }

        public void setTypename(String typename) {
            this.typename = typename;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getNtitle() {
            return ntitle;
        }

        public void setNtitle(String ntitle) {
            this.ntitle = ntitle;
        }

        public String getMimage() {
            return mimage;
        }

        public void setMimage(String mimage) {
            this.mimage = mimage;
        }
    }

}
