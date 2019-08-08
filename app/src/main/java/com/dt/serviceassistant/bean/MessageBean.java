package com.dt.serviceassistant.bean;

import java.util.List;

public class MessageBean {

    private List<MsgBean> biz;

    public List<MsgBean> getBiz() {
        return biz;
    }

    public void setBiz(List<MsgBean> biz) {
        this.biz = biz;
    }

    public static class MsgBean {

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
        private String author;


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

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }
}
