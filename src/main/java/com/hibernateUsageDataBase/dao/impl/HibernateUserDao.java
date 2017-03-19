package com.hibernateUsageDataBase.dao.impl;


import com.entity.User;
import com.hibernateUsageDataBase.dao.UserDao;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mmikilchenko on 19.03.2017.
 */
@Repository("hibernateUserDao")

public class HibernateUserDao implements UserDao {
    private SessionFactory sessionFactory;

    @Autowired
    public HibernateUserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session currentSession() {
        try {
            return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            return sessionFactory.openSession();
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ObjectNotFoundException.class, ConstraintViolationException.class})
    public void addUser(User user) {
        Session session = currentSession();
        //session.beginTransaction();
        session.save(user);
        //session.getTransaction().commit();
    }

    @Override
    public User getUserById(int id) {
        Session session = currentSession();
        return (User) session.get(User.class, id);
    }

    @Override
    public boolean deleteUserById(int id) {
        return deleteById(User.class, id);
    }

    //@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ObjectNotFoundException.class, ConstraintViolationException.class})
    @Override
    public List<User> getAllUsers() {
        Session session = currentSession();
        return session.createQuery("from User").list();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ObjectNotFoundException.class, ConstraintViolationException.class})
    private boolean deleteById(Class<?> type, Serializable id) {
        Session session = currentSession();
        Object persistentInstance = session.load(type, id);
        if (persistentInstance != null) {

            session.delete(persistentInstance);
            session.saveOrUpdate(persistentInstance);
            return true;
        }
        return false;
    }
}
