package com.dt.serviceassistant.bean;

/**
 * Created by admin on 2018/7/2.
 */

public class AppBean extends CodeBean {// implements Serializable


    /**
     * code : 1
     * data : {"sex":"1","phone":"","nickname":"刘万全","userid":"10004939","password":"xMLJRiSkAaA=","headurl":"","roletype":"0"}
     * msg":"手机号不存在，请联系管理员"
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * sex : 1
         * phone :
         * nickname : 刘万全
         * userid : 10004939
         * password : xMLJRiSkAaA=
         * headurl :
         * roletype : 0
         */

        private String sex;
        private String phone;
        private String nickname;
        private String userid;
        private String password;
        private String headurl;

        private String roletype;

        private int messagetype;

        private String keyword;
        private String starttime;
        private String endtime;
        private int analysistype;
        private int start;

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getHeadurl() {
            return headurl;
        }

        public void setHeadurl(String headurl) {
            this.headurl = headurl;
        }

        public String getRoletype() {
            return roletype;
        }

        public void setRoletype(String roletype) {
            this.roletype = roletype;
        }

        public int getMessagetype() {
            return messagetype;
        }

        public void setMessagetype(int messagetype) {
            this.messagetype = messagetype;
        }


        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public int getAnalysistype() {
            return analysistype;
        }

        public void setAnalysistype(int analysistype) {
            this.analysistype = analysistype;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }
    }
}
