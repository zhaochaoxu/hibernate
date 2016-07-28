package com.chaoxu.pojo;

import java.io.Serializable;

/**
 * Created by dell on 2016/7/28.
 */
public class Task implements Serializable {

    private String  id;
    private String title;
    private Integer version;


    public Task() {
    }

    public Task(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
