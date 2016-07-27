package com.chaoxu.pojo;

import java.io.Serializable;

/**
 * Created by dell on 2016/7/8.
 */
public class UserLog implements Serializable {

    private static final long serialVersionUID = 7724066303982974884L;
    private Integer id;
    private Integer userid;
    private String logintime;
    private String loginip;

    public String getLoginip() {
        return loginip;
    }

    public void setLoginip(String loginip) {
        this.loginip = loginip;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getLogintime() {
        return logintime;
    }

    public void setLogintime(String logintime) {
        this.logintime = logintime;
    }

    @Override
    public String toString() {
        return "UserLog{" +
                "id=" + id +
                ", userid=" + userid +
                ", logintime='" + logintime + '\'' +
                ", loginip='" + loginip + '\'' +
                '}';
    }
}
