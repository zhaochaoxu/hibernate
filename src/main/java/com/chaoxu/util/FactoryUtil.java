package com.chaoxu.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by dell on 2016/7/25.
 */
public class FactoryUtil {

    private static SessionFactory sessionFactory = BuildSessionFactory();

    private static SessionFactory BuildSessionFactory() {
        Configuration configuration = new Configuration().configure();
        ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(registry);
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    public static Session getSession(){
        return getSessionFactory().getCurrentSession();
    }
}
