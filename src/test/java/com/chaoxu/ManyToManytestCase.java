package com.chaoxu;

import com.chaoxu.pojo.Studen;
import com.chaoxu.pojo.Teacher;
import com.chaoxu.util.FactoryUtil;
import org.hibernate.Session;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by dell on 2016/7/28.
 */
public class ManyToManytestCase {

/*
        Session session = FactoryUtil.getSession();
        session.beginTransaction();


        session.getTransaction().commit();
*/

    @Test
    public void testSave() {
        Session session = FactoryUtil.getSession();
        session.beginTransaction();

        Teacher teacher  = new Teacher();
        teacher.setTeaname("T1");

        Studen studen1 = new Studen("S1");
        Studen studen2 = new Studen("S2");

        Set<Studen> studenSet = new HashSet<>();
        studenSet.add(studen1);
        studenSet.add(studen2);


        teacher.setStudenSet(studenSet);

        session.save(teacher);
        session.save(studen1);
        session.save(studen2);

        session.getTransaction().commit();
    }

    @Test
    public void testFind(){
        Session session = FactoryUtil.getSession();
        session.beginTransaction();

        Teacher teacher = (Teacher) session.get(Teacher.class,15);
        System.out.println(teacher.getTeaname());

        Set<Studen> studenSet = teacher.getStudenSet();

        for(Studen studen: studenSet){
            System.out.println(studen.getId()+" "+studen.getStuname());
        }

        session.getTransaction().commit();
    }

}
