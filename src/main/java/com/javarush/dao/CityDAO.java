package com.javarush.dao;

import com.javarush.domain.Address;
import com.javarush.domain.City;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class CityDAO extends GenericDAO<City> {
    public CityDAO(SessionFactory sessionFactory) {
        super(City.class, sessionFactory);
    }

    public City getByName(String s) {
        List<City> name = getCurrentSession().createQuery("select c from City c where c.city = :name", City.class).setParameter("name", s).list();
        return name.isEmpty() ? null : name.get(0);
    }
}

