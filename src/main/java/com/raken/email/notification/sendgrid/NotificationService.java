package com.raken.email.notification.sendgrid;

import com.raken.email.dto.EmailDto;
import com.raken.email.exception.BadRequestException;
import com.raken.email.exception.SendEmailException;

/**
 * @author Dayan Kodippily
 */

public interface NotificationService {
    void sendNotification(EmailDto emailDto) throws SendEmailException, BadRequestException;
}
