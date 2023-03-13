package com.raken.email.service;

import com.raken.email.config.AppConfiguration;
import com.raken.email.dto.EmailDto;
import com.raken.email.notification.sendgrid.SendEmail;
import com.raken.email.notification.sendgrid.SendgridServiceCallStrategy;
import com.raken.email.exception.BadRequestException;
import com.raken.email.exception.SendEmailException;
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
