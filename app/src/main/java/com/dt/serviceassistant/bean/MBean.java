package com.dt.serviceassistant.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2018/10/27
 *     desc   :
 * </pre>
 */
public class MBean extends CodeBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        @SerializedName("msg")
        private List<MsgBean> msgX;

        public List<MsgBean> getMsgX() {
            return msgX;
        }

        public void setMsgX(List<MsgBean> msgX) {
            this.msgX = msgX;
        }

        public static class MsgBean implements Serializable {

            private String sex;
            private String phone;
            private String nickname;
            private String userid;
            private String password;
            private String headurl;
            private String roletype;
            private int messagetype;
            private int id;
            private int isclick;
            private String content;
            private int messagecount;
            private String mtimage;
            private String rtime;
            private String typename;
            private String url;
            private String ntitle;
            private String mimage;
            private String scompany;    //发货公司
            private String rcompany; //收货公司
            private String contact; //联系人
            private String status;  //状态
            private String description;//描述
            private int start;//页数
            private List<String> analysis;
            private String createtime;

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

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

            public String getScompany() {
                return scompany;
            }

            public void setScompany(String scompany) {
                this.scompany = scompany;
            }

            public String getRcompany() {
                return rcompany;
            }

            public void setRcompany(String rcompany) {
                this.rcompany = rcompany;
            }

            public String getContact() {
                return contact;
            }

            public void setContact(String contact) {
                this.contact = contact;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getStart() {
                return start;
            }

            public void setStart(int start) {
                this.start = start;
            }

            public List<String> getAnalysis() {
                return analysis;
            }

            public void setAnalysis(List<String> analysis) {
                this.analysis = analysis;
            }
        }
    }
}
