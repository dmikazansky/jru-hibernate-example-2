package com.javarush.dao;

import jakarta.persistence.LockModeType;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public abstract class GenericDAO<T> {
    private final Class<T> clazz;
    private SessionFactory sessionFactory;

    public GenericDAO(Class<T> clazz, SessionFactory sessionFactory) {
        this.clazz = clazz;
        this.sessionFactory = sessionFactory;
    }

    public T getById(int id) {
        return (T) getCurrentSession().get(clazz, id);
    }

    public List<T> getItems(int start, int count) {
        Query q = getCurrentSession().createQuery("from " + clazz.getName(), clazz);
        q.setLockMode(LockModeType.OPTIMISTIC);
        q.setFirstResult(start);
        q.setMaxResults(count);
        return (List<T>) q.getResultList();
    }


    public List<T> findAll() {
        
        return getCurrentSession().createQuery("from " + clazz.getName()).list();

    }

    public T save(T entity) {
        getCurrentSession().saveOrUpdate(entity);
        return entity;
    }

    public void delete(T entity) {
        getCurrentSession().delete(entity);
    }

    public T update(T entity) {
        getCurrentSession().merge(entity);
        return entity;
    }

    public void deleteById(int id) {
        T entity = getById(id);
        delete(entity);
    }

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
