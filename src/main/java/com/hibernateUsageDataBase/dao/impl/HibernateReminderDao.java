package com.hibernateUsageDataBase.dao.impl;

import com.entity.Reminder;
import com.hibernateUsageDataBase.dao.ReminderDao;
import org.hibernate.*;
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
@Repository("hibernateReminderDao")
public class HibernateReminderDao implements ReminderDao {

    private SessionFactory sessionFactory;

    @Autowired
    public HibernateReminderDao(SessionFactory sessionFactory) {
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
    public void addReminder(Reminder reminder) {
        Session session = currentSession();
        session.save(reminder);
    }

    @Override
    public Reminder getReminderById(int reminderId) {
        Session session = currentSession();
        return (Reminder) session.get(Reminder.class, reminderId);
    }

    @Override
    public List<Reminder> showRemindersByUserId(int userId) {
        Session session = currentSession();
        Query query = session.createQuery("from Reminder where user_id = :userId ");
        query.setParameter("userId", userId);
        return query.list();
    }

    @Override
    public List<Reminder> showRemindersByTheme(int themeId) {
        return null;
    }

    @Override
    public void deleteReminderById(int reminderId) {
        deleteById(Reminder.class, reminderId);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ObjectNotFoundException.class, ConstraintViolationException.class})
    public void updateReminder(Reminder reminder) {
        currentSession().saveOrUpdate(reminder);
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
