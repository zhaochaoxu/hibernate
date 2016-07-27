package com.chaoxu.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by dell on 2016/7/11.
 */
public class Notice implements Serializable {

    private static final long serialVersionUID = 7041477585864245569L;
    private Integer id;
    private Integer userid;
    private String title;
    private String Context;
    private Timestamp createtime;

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    private String realname;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid(Integer integer) {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getContext() {
        return Context;
    }

    public void setContext(String context) {
        Context = context;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }




}
