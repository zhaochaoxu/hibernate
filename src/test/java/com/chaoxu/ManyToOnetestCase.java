package com.chaoxu;

import com.chaoxu.pojo.Dept;
import com.chaoxu.pojo.Employee;
import com.chaoxu.pojo.Person;
import com.chaoxu.util.FactoryUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;

import java.util.List;

/**
 * Created by dell on 2016/7/26.
 */
public class ManyToOnetestCase {

/*
     Session session = FactoryUtil.getSession();
     session.beginTransaction();


     session.getTransaction().commit();
*/

    @Test
    public void testSaveOneTomany() {

        Session session = FactoryUtil.getSession();
        session.beginTransaction();

        Person person = (Person) session.get(Person.class,16);
        //session.clear();
        System.out.println(person);

        person.setName("赵玉静");
        session.update(person);

//        session.merge(person);

        /*Dept dept = new Dept();
        dept.setDeptname("Java开发部");

        Employee employee = new Employee();
        employee.setEmpname("赵七");
        employee.setDept(dept);

        Employee employee1 = new Employee();
        employee1.setEmpname("王八");
        employee1.setDept(dept);

        session.save(dept);
        session.save(employee);
        session.save(employee1);*/


        session.getTransaction().commit();

       /* person.setName("陈丽婷");

        Session session1 = FactoryUtil.getSession();
        session1.beginTransaction();

        session1.update(person);

        session1.flush();

        System.out.println("=====================");
        System.out.println("=====================");
        System.out.println("=====================");
        System.out.println("=====================");
        System.out.println("=====================");
        System.out.println("=====================");
        System.out.println("=====================");

        session1.getTransaction().commit();*/



    }

    @Test
    public void testFindEmployee() {
        Session session = FactoryUtil.getSession();
        session.beginTransaction();

        Criteria criteria = session.createCriteria(Employee.class);

        List<Employee> employeeList = criteria.list();
        for (Employee employee : employeeList) {
            System.out.println(employee.getDept().getDeptname());
        }


        session.getTransaction().commit();
    }

    @Test
    public void testFindDept() {

        Session session = FactoryUtil.getSession();
        session.beginTransaction();

        Criteria criteria = session.createCriteria(Dept.class);
        List<Dept> deptList = criteria.list();
        for (Dept dept : deptList) {
            System.out.println(dept.getEmployeeSet());
        }

        session.getTransaction().commit();
    }


    @Test
    public void testDel() {

        Session session = FactoryUtil.getSession();
        session.beginTransaction();

        Dept dept = (Dept) session.load(Dept.class, 17);
        session.delete(dept);

        session.getTransaction().commit();

    }


}
