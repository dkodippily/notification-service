package com.rk.email.notification.sendgrid;

import com.rk.email.dto.EmailDto;
import com.rk.email.exception.BadRequestException;
import com.rk.email.exception.SendEmailException;

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
