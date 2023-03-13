package com.rk.email.notification.sendgrid;

import com.rk.email.config.AppConfiguration;
import com.rk.email.constant.ApplicationConstants;
import com.rk.email.dto.EmailDto;
import com.rk.email.notification.sendgrid.util.EmailTracer;
import com.rk.email.notification.sendgrid.util.ExceptionTranslator;
import com.rk.email.exception.BadRequestException;
import com.rk.email.exception.SendEmailException;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.IOException;

import static com.rk.email.constant.ApplicationConstants.NOTIFICATION_SERVICE_API_ENDPOINT;

/**
 * @author Dayan Kodippily
 */

public class SendgridNotificationService implements NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendgridNotificationService.class);

    private AppConfiguration appConfiguration;

    public SendgridNotificationService(AppConfiguration appConfiguration) {
        this.appConfiguration = appConfiguration;
    }

    @Override
    public void sendNotification(EmailDto emailDto) throws SendEmailException, BadRequestException {

        LOGGER.debug("SendgridNotificationService - sendNotification() {} ", emailDto.getToAddresses());

        if (appConfiguration.isFilterEmailsByDomain()) {
            LOGGER.debug("FilterEmailsByDomain {} ", appConfiguration.isFilterEmailsByDomain());
            EmailTracer.filterAndTrace(emailDto, appConfiguration.getFilterDomain());
        }

        Mail mail = personalizeEmail(emailDto);
        SendGrid sendGrid = new SendGrid(appConfiguration.getApiKey());
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint(NOTIFICATION_SERVICE_API_ENDPOINT);
        Response response;
        try {
            request.setBody(mail.build());
            response = sendGrid.api(request);
        } catch (IOException e) {
            String errMsg = "Error calling the notification service : " + e.getMessage();
            LOGGER.error(errMsg);
            throw new SendEmailException("Error calling the notification service.");
        }

        if (response.getStatusCode() != HttpStatus.SC_ACCEPTED) {
            LOGGER.error(response.getBody());
            String errMsg = ExceptionTranslator.translate(response);
            throw new BadRequestException(errMsg);
        }

    }

    private Mail personalizeEmail(EmailDto emailDto) {

        LOGGER.debug("SendgridNotificationService - personalizeEmail()");
        Mail mail = new Mail();
        Personalization personalization = new Personalization();

        Email from = new Email(appConfiguration.getFromAddress()); // read from config
        mail.setFrom(from);
        mail.setSubject(emailDto.getSubject());

        emailDto.getToAddresses().forEach(email -> personalization.addTo(new Email(email)));
        if (!CollectionUtils.isEmpty(emailDto.getCcAddresses())) {
            emailDto.getCcAddresses().forEach(email -> personalization.addCc(new Email(email)));
        }
        if (!CollectionUtils.isEmpty(emailDto.getBcAddresses())) {
            emailDto.getBcAddresses().forEach(email -> personalization.addBcc(new Email(email)));
        }

        mail.addPersonalization(personalization);
        Content content = new Content(ApplicationConstants.SENDGRID_EMAIL_CONTENT_TYPE, emailDto.getContent());
        mail.addContent(content);

        return mail;
    }
}
