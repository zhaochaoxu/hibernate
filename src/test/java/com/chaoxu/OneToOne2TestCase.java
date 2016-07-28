package com.chaoxu;

import com.chaoxu.pojo.Topic;
import com.chaoxu.pojo.TopicContent;
import com.chaoxu.util.FactoryUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.junit.Test;

/**
 * Created by dell on 2016/7/28.
 */
public class OneToOne2TestCase {

    @Test
    public void testSave() {
        Session session = FactoryUtil.getSession();
        session.beginTransaction();

        Topic topic = new Topic("去除Unique");

        TopicContent topicContent = new TopicContent("结果怎样！");

        topic.setTopicContent(topicContent);

        session.save(topic);
        session.save(topicContent);

        session.getTransaction().commit();
    }

    @Test
    public void testFind() {
        Session session = FactoryUtil.getSession();
        session.beginTransaction();

        Topic topic = (Topic) session.get(Topic.class,6);

        Hibernate.initialize(topic.getTopicContent());
        session.getTransaction().commit();

        System.out.println(topic.getTopicContent().getContent());
    }




}
