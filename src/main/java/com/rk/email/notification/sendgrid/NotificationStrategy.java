package com.rk.email.notification.sendgrid;

import com.rk.email.dto.EmailDto;
import com.rk.email.exception.BadRequestException;
import com.rk.email.exception.SendEmailException;

/**
 * @author Dayan Kodippily
 */

public interface NotificationStrategy {
    void call(EmailDto emailDto) throws SendEmailException, BadRequestException;
}
