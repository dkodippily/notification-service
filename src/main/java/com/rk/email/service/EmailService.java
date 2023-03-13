package com.rk.email.service;

import com.rk.email.dto.EmailDto;
import com.rk.email.exception.BadRequestException;
import com.rk.email.exception.SendEmailException;

/**
 * @author Dayan Kodippily
 */

public interface EmailService {

    void send(EmailDto emailDto) throws SendEmailException, BadRequestException;
}
