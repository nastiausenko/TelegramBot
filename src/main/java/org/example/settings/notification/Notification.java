package org.example.settings.notification;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class Notification {
    private Scheduler scheduler;

    public Notification() throws SchedulerException {
        scheduler = StdSchedulerFactory.getDefaultScheduler();
    }

    public void Start(int notificationHour) throws SchedulerException {
        String group = "group1";
        JobDetail jobDetail = JobBuilder.newJob(NotificationSchedule.class)
                .withIdentity("myJob", group)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger", group)
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(notificationHour, 44))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }

    public void Stop() throws SchedulerException {
        scheduler.clear();
    }
}
