package org.example.settings.notification;

import lombok.SneakyThrows;
import org.example.information.CurrencyInfo;
import org.example.settings.data.User;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.LocalTime;

public class NotificationSchedule implements Job {
    @SneakyThrows
    public void execute(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        CurrencyInfo currencyInfo = (CurrencyInfo) jobDataMap.get("currencyInfo");
        SendMessage message = (SendMessage) jobDataMap.get("message");
        Long chatId = (Long) jobDataMap.get("chatId");
        User user = (User) jobDataMap.get("user");

        LocalTime currentTime = LocalTime.now();
        int currentHour = currentTime.getHour();

        int userNotificationHour;
        if (user != null) {
            userNotificationHour = user.getTime();
        } else {
            return;
        }

        if (currentHour == userNotificationHour) {
            currencyInfo.getCurrencyRate(message, chatId);
        }

        System.out.println("Hello");
    }
}
