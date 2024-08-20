package com.javarush.dao;

import com.javarush.domain.Rental;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class RentalDAO extends GenericDAO<Rental> {
    public RentalDAO(SessionFactory sessionFactory) {
        super(Rental.class, sessionFactory);
    }

    public Rental getNotReturnedRental() {
        Query<Rental> query = getCurrentSession().createQuery("from Rental r where r.returnDate is null", Rental.class);
        query.setMaxResults(1);
        Rental singleResult = query.getSingleResult();
        return singleResult;

    }
}
