package com.raken.email.notification.sendgrid;

import com.raken.email.dto.EmailDto;
import com.raken.email.exception.BadRequestException;
import com.raken.email.exception.SendEmailException;

/**
 * @author Dayan Kodippily
 */

public interface NotificationStrategy {
    void call(EmailDto emailDto) throws SendEmailException, BadRequestException;
}
