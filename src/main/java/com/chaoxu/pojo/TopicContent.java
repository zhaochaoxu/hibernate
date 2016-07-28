package com.chaoxu.pojo;

/**
 * Created by dell on 2016/7/28.
 */
public class TopicContent {


    private Integer id;
    private String content;

    public TopicContent() {
    }

    public TopicContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
