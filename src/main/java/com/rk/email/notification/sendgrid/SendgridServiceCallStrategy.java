package com.rk.email.notification.sendgrid;

import com.rk.email.config.AppConfiguration;
import com.rk.email.dto.EmailDto;
import com.rk.email.exception.BadRequestException;
import com.rk.email.exception.SendEmailException;

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
