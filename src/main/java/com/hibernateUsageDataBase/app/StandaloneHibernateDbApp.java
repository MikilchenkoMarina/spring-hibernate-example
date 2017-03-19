package com.hibernateUsageDataBase.app;


import com.entity.Reminder;
import com.entity.User;
import com.hibernateUsageDataBase.HibernateConfigs;
import com.hibernateUsageDataBase.dao.ReminderDao;
import com.hibernateUsageDataBase.dao.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mmikilchenko on 19.03.2017.
 */
public class StandaloneHibernateDbApp {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(HibernateConfigs.class);
        UserDao userDao = (UserDao) context.getBean("hibernateUserDao");
        ReminderDao reminderDao = (ReminderDao) context.getBean("hibernateReminderDao");

        List<Reminder> remList = new ArrayList<>();


        Reminder reminderOne = new Reminder("As our case is new, we must think and act anew. ", "Abraham Lincoln Quotes", null);
        Reminder reminderTwo = new Reminder("Important principles may, and must, be inflexible. ", "First Theme", null);
        remList.addAll(Arrays.asList(reminderOne, reminderTwo));

        reminderDao.addReminder(reminderOne);
        reminderDao.addReminder(reminderTwo);

        User testUser = new User(/*1,*/ "Marina", "Mikilchenko", "mikimar777", "pass", remList);
        userDao.addUser(testUser);

        updateRemembers(reminderDao, remList, testUser);


        printRemindersByUserId(reminderDao, testUser.getUserId());

        userDao.deleteUserById(testUser.getUserId());

        printAllExistedUsersList(userDao);

    }

    public static void printAllExistedUsersList(UserDao userDao) {
        for (User user : userDao.getAllUsers()) {
            System.out.println(user.toString());
        }
    }

    public static void printRemindersByUserId(ReminderDao reminderDao, int userId) {
        for (Reminder userReminder : reminderDao.showRemindersByUserId(userId)) {
            System.out.println(" THEME :  " + userReminder.getImageLink() + " Reminder text : " + userReminder.getText());
        }
    }

    public static void printUser(UserDao userDao, int userId) {
        System.out.println(" User with id 1 : " + userDao.getUserById(1).toString());
    }

    public static void updateRemembers(ReminderDao reminderDao, List<Reminder> remList, User testUser) {
        for (Reminder reminder : remList) {
            reminder.setUser(testUser);
            reminderDao.updateReminder(reminder);
        }
    }

}
