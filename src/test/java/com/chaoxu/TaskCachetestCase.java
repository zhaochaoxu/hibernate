package com.chaoxu;

import com.chaoxu.pojo.Task;
import com.chaoxu.util.FactoryUtil;
import org.hibernate.Session;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by dell on 2016/7/28.
 */
public class TaskCachetestCase {

    @Test
    public void testSave() {
        Session session = FactoryUtil.getSession();
        session.beginTransaction();

        Task task = new Task("再见 过去！");
        task.setId(UUID.randomUUID().toString());

        session.save(task);

        session.getTransaction().commit();
    }

    @Test
    public void testFindById() {
        Session session = FactoryUtil.getSession();
        session.beginTransaction();

        Task task = (Task) session.get(Task.class, "402881a0563055120156305514960000");
        System.out.println(task.getTitle());

        session.getTransaction().commit();
//===========================================================================
        Session session1 = FactoryUtil.getSession();
        session1.beginTransaction();

        Task task1 = (Task) session1.get(Task.class, "402881a0563055120156305514960000");
        System.out.println(task1.getTitle());

        session1.getTransaction().commit();

    }
}
