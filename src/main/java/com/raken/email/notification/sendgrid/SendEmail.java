package com.raken.email.notification.sendgrid;

import com.raken.email.dto.EmailDto;
import com.raken.email.exception.BadRequestException;
import com.raken.email.exception.SendEmailException;

/**
 * @author Dayan Kodippily
 */


public class SendEmail {
    private NotificationStrategy notificationStrategy;

    public SendEmail(NotificationStrategy notificationStrategy) {
        this.notificationStrategy = notificationStrategy;
    }

    public void sendEmail(EmailDto emailDto) throws SendEmailException, BadRequestException {
        notificationStrategy.call(emailDto);
    }
}
