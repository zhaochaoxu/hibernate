package com.chaoxu;

import com.chaoxu.pojo.Student;
import com.chaoxu.util.FactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.sql.Update;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by dell on 2016/7/25.
 */
public class HibernateSavetestCase {

    @Test
    public void testSave() {
        Session session = FactoryUtil.getSession();
        Transaction transaction = session.beginTransaction();

        Student student = new Student();
        student.setUsername("赵玉静");
        student.setPassword("031428");
        //session.saveOrUpdate(student);
        session.persist(student);

        /*Integer id = (Integer) session.save(student);
        System.out.println(id);*/
        transaction.commit();
    }

    @Test
    public void testFindByGet() {
        Session session = FactoryUtil.getSession();
        session.beginTransaction();

        Student student = (Student) session.get(Student.class, 18);

        session.getTransaction().commit();
        Assert.assertNull(student);
    }

    @Test
    public void testFindByLoad() {
        Session session = FactoryUtil.getSession();
        Transaction transaction = session.beginTransaction();

        Student stu = (Student) session.get(Student.class, 16);
        System.out.println(stu == null);
        //System.out.println(stu.getUsername());
        transaction.commit();

    }

    @Test
    public void testUpdate() {
        Session session = FactoryUtil.getSession();
        Transaction transaction = session.beginTransaction();

        Student student = (Student) session.get(Student.class, 12);

        transaction.commit();

        student.setPassword("33333333");


        Session session1 = FactoryUtil.getSession();
        Transaction transaction1 = session1.beginTransaction();

        session1.update(student);

        transaction1.commit();

    }

    @Test
    public void testSaveOrUpdate() {

        Session session = FactoryUtil.getSession();
        session.beginTransaction();

        //Student student = new Student("赵旭","071028");

        //session.saveOrUpdate(student);

        Student student = (Student) session.get(Student.class, 15);

        session.getTransaction().commit();

        student.setPassword("113028");

        Session session1 = FactoryUtil.getSession();
        session1.beginTransaction();

        session1.saveOrUpdate(student);

        session1.flush();
        session1.getTransaction().commit();

    }

    @Test
    public void testMerge() {
        Session session = FactoryUtil.getSession();
        session.beginTransaction();

        /*Student student = new Student("黄燕","999999");
        session.merge(student);

        System.out.println(student.getId());*///无ID

        Student student = (Student) session.get(Student.class, 17);
        session.getTransaction().commit();

        student.setPassword("336699");

        Session session1 = FactoryUtil.getSession();
        session1.beginTransaction();

        session1.merge(student);
        System.out.println(student.getId());

        session1.getTransaction().commit();

    }

    @Test
    public void testClear() {
        Session session = FactoryUtil.getSession();
        session.beginTransaction();

        Student student = (Student) session.get(Student.class,17);
       /* session.clear();
        student.setPassword("0000000000");
        session.update(student);*/

        student.setPassword("66666666");

        session.flush();

        System.out.println("=====================");
        System.out.println("=====================");
        System.out.println("=====================");
        System.out.println("=====================");
        session.getTransaction().commit();
    }


}
