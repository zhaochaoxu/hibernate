package com.chaoxu;

import com.chaoxu.pojo.Student;
import com.chaoxu.util.FactoryUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import java.util.List;

/**
 * Created by dell on 2016/7/26.
 */
public class HqlTestCase {

    @Test
    public void findAll() {
        Session session = FactoryUtil.getSession();
        session.beginTransaction();
        String hql = "from Student";

        Query query = session.createQuery(hql);
        List<Student> studentList = query.list();
        for (Student stu : studentList) {
            System.out.println(stu);
        }

        session.getTransaction().commit();

    }

    @Test
    public void findByPassword() {
        Session session = FactoryUtil.getSession();
        session.beginTransaction();

        String hql = "from Student where password=:pwd and username = :name";

        Query query = session.createQuery(hql);

        query.setParameter("pwd", "666999");
        query.setParameter("name", "zcx");

        List<Student> studentList = query.list();

        for (Student stu : studentList) {
            System.out.println(stu);
        }
        session.getTransaction().commit();
    }

    @Test
    public void findUnique() {
        Session session = FactoryUtil.getSession();
        session.beginTransaction();

        String hql = "from Student where password=:pwd";

        Query query = session.createQuery(hql);

        query.setParameter("pwd","071028");
        Student student = (Student) query.uniqueResult();
        System.out.println(student);

        session.getTransaction().commit();

    }

    @Test
    public void findByname(){
        Session session = FactoryUtil.getSession();
        session.beginTransaction();

        String hql = "select id,username from Student";

        Query query = session.createQuery(hql);

        List<Object[]> result = query.list();

        for(Object[] objects : result ){
            System.out.println(objects[0]+"->"+objects[1]);
        }


        /*List<String> stringList = query.list();
        for(String name : stringList){
            System.out.println(name);
        }*/

        session.getTransaction().commit();
    }

    @Test
    public void testCount(){
        Session session = FactoryUtil.getSession();
        session.beginTransaction();

        String hql = "select count(*),max(id) from Student";
        Query query = session.createQuery(hql);

        Object[]  result= (Object[]) query.uniqueResult();

        System.out.println("count->"+result[0]+"max id ->"+result[1]);
        session.getTransaction().commit();
    }

    @Test
    public void testLimit(){
        Session session = FactoryUtil.getSession();
        session.beginTransaction();

        String hql = "from Student order by id desc";

        Query query = session.createQuery(hql);

        query.setFirstResult(3);
        query.setMaxResults(3);

        List<Student> studentList = query.list();

        for (Student stu : studentList) {
            System.out.println(stu);
        }
        session.getTransaction().commit();
    }
}
