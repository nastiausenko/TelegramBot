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

        scheduler.deleteJob(JobKey.jobKey("myJob", group));
        scheduler.deleteJob(JobKey.jobKey("myJob1", group));
        scheduler.deleteJob(JobKey.jobKey("myJob2", group));
        scheduler.deleteJob(JobKey.jobKey("myJob3", group));
        scheduler.deleteJob(JobKey.jobKey("myJob4", group));
        scheduler.deleteJob(JobKey.jobKey("myJob5", group));
        scheduler.deleteJob(JobKey.jobKey("myJob6", group));
        scheduler.deleteJob(JobKey.jobKey("myJob7", group));
        scheduler.deleteJob(JobKey.jobKey("myJob8", group));
        scheduler.deleteJob(JobKey.jobKey("myJob9", group));


        JobDetail jobDetail1 = JobBuilder.newJob(NotificationSchedule.class)
                .withIdentity("myJob", group)
                .build();
        JobDetail jobDetail2 = JobBuilder.newJob(NotificationSchedule.class)
                .withIdentity("myJob1", group)
                .build();
        JobDetail jobDetail3 = JobBuilder.newJob(NotificationSchedule.class)
                .withIdentity("myJob2", group)
                .build();
        JobDetail jobDetail4 = JobBuilder.newJob(NotificationSchedule.class)
                .withIdentity("myJob3", group)
                .build();
        JobDetail jobDetail5 = JobBuilder.newJob(NotificationSchedule.class)
                .withIdentity("myJob4", group)
                .build();
        JobDetail jobDetail6 = JobBuilder.newJob(NotificationSchedule.class)
                .withIdentity("myJob5", group)
                .build();
        JobDetail jobDetail7 = JobBuilder.newJob(NotificationSchedule.class)
                .withIdentity("myJob6", group)
                .build();
        JobDetail jobDetail8 = JobBuilder.newJob(NotificationSchedule.class)
                .withIdentity("myJob7", group)
                .build();
        JobDetail jobDetail9 = JobBuilder.newJob(NotificationSchedule.class)
                .withIdentity("myJob8", group)
                .build();
        JobDetail jobDetail10 = JobBuilder.newJob(NotificationSchedule.class)
                .withIdentity("myJob9", group)
                .build();


        Trigger trigger1 = TriggerBuilder.newTrigger()
                .withIdentity("trigger", group)
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(notificationHour, 0))
                .build();
        scheduler.scheduleJob(jobDetail1, trigger1);


        Trigger trigger2 = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", group)
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(notificationHour, 0))
                .build();
        scheduler.scheduleJob(jobDetail2, trigger2);


        Trigger trigger3 = TriggerBuilder.newTrigger()
                .withIdentity("trigger2", group)
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(notificationHour, 0))
                .build();
        scheduler.scheduleJob(jobDetail3, trigger3);


        Trigger trigger4 = TriggerBuilder.newTrigger()
                .withIdentity("trigger3", group)
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(notificationHour, 0))
                .build();
        scheduler.scheduleJob(jobDetail4, trigger4);


        Trigger trigger5 = TriggerBuilder.newTrigger()
                .withIdentity("trigger4", group)
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(notificationHour, 0))
                .build();
        scheduler.scheduleJob(jobDetail5, trigger5);


        Trigger trigger6 = TriggerBuilder.newTrigger()
                .withIdentity("trigger5", group)
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(notificationHour, 0))
                .build();
        scheduler.scheduleJob(jobDetail6, trigger6);


        Trigger trigger7 = TriggerBuilder.newTrigger()
                .withIdentity("trigger6", group)
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(notificationHour, 0))
                .build();
        scheduler.scheduleJob(jobDetail7, trigger7);


        Trigger trigger8 = TriggerBuilder.newTrigger()
                .withIdentity("trigger7", group)
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(notificationHour, 0))
                .build();
        scheduler.scheduleJob(jobDetail8, trigger8);


        Trigger trigger9 = TriggerBuilder.newTrigger()
                .withIdentity("trigger8", group)
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(notificationHour, 0))
                .build();
        scheduler.scheduleJob(jobDetail9, trigger9);

        Trigger trigger10 = TriggerBuilder.newTrigger()
                .withIdentity("trigger9", group)
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(notificationHour, 0))
                .build();
        scheduler.scheduleJob(jobDetail10, trigger10);

        scheduler.start();
    }

    public void Stop() throws SchedulerException {
        scheduler.clear();
    }
}
