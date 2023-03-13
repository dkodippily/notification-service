package com.rk.email.service;

import com.rk.email.config.AppConfiguration;
import com.rk.email.dto.EmailDto;
import com.rk.email.notification.sendgrid.SendEmail;
import com.rk.email.notification.sendgrid.SendgridServiceCallStrategy;
import com.rk.email.exception.BadRequestException;
import com.rk.email.exception.SendEmailException;
import org.springframework.stereotype.Service;

/**
 * @author Dayan Kodippily
 */

@Service
public class EmailServiceImpl implements EmailService{



    private AppConfiguration appConfiguration;

    public EmailServiceImpl(AppConfiguration appConfiguration) {
        this.appConfiguration = appConfiguration;
    }

    @Override
    public void send(EmailDto emailDto) throws SendEmailException, BadRequestException {
        SendgridServiceCallStrategy sendgridServiceCallStrategy = new SendgridServiceCallStrategy(appConfiguration);
        SendEmail sendEmail = new SendEmail(sendgridServiceCallStrategy);
        sendEmail.sendEmail(emailDto);
    }
}
