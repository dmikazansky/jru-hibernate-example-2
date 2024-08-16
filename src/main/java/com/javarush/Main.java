package com.javarush;

import com.javarush.domain.Actor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Actor actor = new Actor();
            actor.setFirstName("John");
            actor.setLastName("Doe");
            session.persist(actor);
            session.getTransaction().commit();
        }
    }
}

