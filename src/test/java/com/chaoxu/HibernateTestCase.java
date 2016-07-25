package com.chaoxu;

import com.chaoxu.pojo.Student;
import com.chaoxu.pojo.User;
import com.chaoxu.util.FactoryUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.util.List;

/**
 * Created by dell on 2016/7/25.
 */
public class HibernateTestCase {


    Session session = FactoryUtil.getSession();

    @Test
    public void testSave() {

        Transaction transaction = session.beginTransaction();

        User user = new User();
        user.setUsername("Tom");
        user.setPassword("123123");
        user.setAddress("china");

        session.save(user);
        transaction.commit();
    }

    @Test
    public void testSaveStudent(){

        Transaction transaction = session.beginTransaction();
        Student stu = new Student();
        stu.setUsername("陈丽婷");
        stu.setPassword("66666");
        session.save(stu);

        transaction.commit();
    }

    @Test
    public void testFindStu(){
        Transaction transaction = session.beginTransaction();

        Student stu = (Student) session.get(Student.class,2);
        System.out.println(stu.getUsername());
        transaction.commit();
    }

    @Test
    public void testDel(){
        Transaction transaction = session.beginTransaction();

        Student stu = (Student) session.get(Student.class,1);
        session.delete(stu);
        transaction.commit();
    }

    @Test
    public void testUpdate(){
        Transaction transaction = session.beginTransaction();
        Student student = (Student) session.get(Student.class,2);
        student.setPassword("369369");
        transaction.commit();
    }
    @Test
    public void FindAll(){
        Transaction transaction = session.beginTransaction();

        String hql = "from Student";
        Query query = session.createQuery(hql);

        List<Student> studentList = query.list();

        for (Student stu:studentList) {
            System.out.println(stu);
        }

        transaction.commit();
    }
}
