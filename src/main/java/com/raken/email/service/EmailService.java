package com.raken.email.service;

import com.raken.email.dto.EmailDto;
import com.raken.email.exception.BadRequestException;
import com.raken.email.exception.SendEmailException;

/**
 * @author Dayan Kodippily
 */

public interface EmailService {

    void send(EmailDto emailDto) throws SendEmailException, BadRequestException;
}
