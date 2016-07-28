package com.chaoxu.pojo;

/**
 * Created by dell on 2016/7/28.
 */
public class Topic {

    private Integer id;
    private String title;
    private TopicContent topicContent;

    public Topic() {
    }

    public Topic(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TopicContent getTopicContent() {
        return topicContent;
    }

    public void setTopicContent(TopicContent topicContent) {
        this.topicContent = topicContent;
    }
}



