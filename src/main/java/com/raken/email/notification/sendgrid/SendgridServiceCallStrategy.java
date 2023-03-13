package com.raken.email.notification.sendgrid;

import com.raken.email.config.AppConfiguration;
import com.raken.email.dto.EmailDto;
import com.raken.email.exception.BadRequestException;
import com.raken.email.exception.SendEmailException;

/**
 * @author Dayan Kodippily
 */


public class SendgridServiceCallStrategy implements NotificationStrategy {
    private NotificationService notificationService;
    private AppConfiguration appConfiguration;

    public SendgridServiceCallStrategy(AppConfiguration appConfiguration) {
        this.notificationService = new SendgridNotificationService(appConfiguration);
        this.appConfiguration = appConfiguration;
    }

    @Override
    public void call(EmailDto emailDto) throws SendEmailException, BadRequestException {
        notificationService.sendNotification(emailDto);
    }
}
