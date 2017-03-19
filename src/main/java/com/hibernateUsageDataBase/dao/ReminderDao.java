package com.hibernateUsageDataBase.dao;

import com.entity.Reminder;
import java.util.List;

/**
 * Created by mmikilchenko on 19.03.2017.
 */
public interface ReminderDao {
    public void addReminder(Reminder reminder);

    public Reminder getReminderById(int reminderId);

    public List<Reminder> showRemindersByUserId(int userId);

    public List<Reminder> showRemindersByTheme(int themeId);

    public void deleteReminderById(int reminderId);

    public void updateReminder(Reminder reminder);

}
