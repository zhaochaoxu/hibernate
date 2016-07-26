package com.chaoxu;

import com.chaoxu.pojo.Student;
import com.chaoxu.util.FactoryUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.junit.Test;

import java.util.List;

/**
 * Created by dell on 2016/7/26.
 */
public class criteriaTestCase {


    @Test
    public void testFindAll() {
        Session session = FactoryUtil.getSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Student.class);

        List<Student> studentList = criteria.list();

        for (Student stu : studentList) {
            System.out.println(stu);
        }

        session.getTransaction().commit();
    }

    @Test
    public void testFindByname() {
        Session session = FactoryUtil.getSession();
        session.beginTransaction();

        Criteria criteria = session.createCriteria(Student.class);

       /* criteria.add(Restrictions.eq("password","666999"));
        criteria.add(Restrictions.eq("username","zcx"));*/

        //模糊查询 LIKE
//        criteria.add(Restrictions.like("username","x", MatchMode.ANYWHERE));
//        List<Student> studentList =criteria.list();


        /*//or 查询
        Disjunction disjunction = Restrictions.disjunction();

        disjunction.add(Restrictions.eq("username", "zcx"));
        disjunction.add(Restrictions.eq("password","666999"));
        criteria.add(disjunction);*/

//      criteria.add(Restrictions.or(Restrictions.eq("username", "陈丽婷"),
//                                   Restrictions.eq("password","666999")));

        //in
        criteria.add(Restrictions.in("username", new Object[]{"陈丽婷", "牛丽娟"}));
        List<Student> studentList = criteria.list();
        for (Student stu : studentList) {
            System.out.println(stu);
        }

        session.getTransaction().commit();

    }

    @Test  //分页和排序
    public void testByPage() {
        Session session = FactoryUtil.getSession();
        session.beginTransaction();

        Criteria criteria = session.createCriteria(Student.class);

        criteria.addOrder(Order.desc("id"));
        criteria.setFirstResult(0);
        criteria.setMaxResults(6);

        List<Student> studentList = criteria.list();

        for (Student stu : studentList) {
            System.out.println(stu);
        }


        session.getTransaction().commit();
    }

    @Test
    public void findCount() {
        Session session = FactoryUtil.getSession();
        session.beginTransaction();

        Criteria criteria = session.createCriteria(Student.class);

        criteria.setProjection(Projections.rowCount());
        Long count = (Long) criteria.uniqueResult();
        System.out.println(count);

        /*ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.rowCount());
        projectionList.add(Projections.max("id"));

        criteria.setProjection(projectionList);

        List<Object[]> result = criteria.list();

        for(Object[] objects:result){
            System.out.println(objects[0]);
        }*/

        session.getTransaction().commit();
    }
}
