package com.dt.serviceassistant.bean;

/**
 * Created by Administrator on 2017/4/21.
 */

public class CodeBean {

    /**
     * code : 0000
     * SMSCode : 1234
     * msg : ok
     */

    private String code;
    private int sid;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
