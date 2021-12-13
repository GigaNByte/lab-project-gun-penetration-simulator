package com.giga;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

// https://refactoring.guru/pl/design-patterns/singleton/java/example#example-2
public class HibernateConnection {

    private static SessionFactory sessionFactory;

    private HibernateConnection() {
    }

    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration()
                    .configure().buildSessionFactory();
        }
        return sessionFactory;
    }

}
