package com.raken.email.controller;

import com.raken.email.constant.ApplicationConstants;
import com.raken.email.dto.EmailDto;
import com.raken.email.exception.BadRequestException;
import com.raken.email.exception.SendEmailException;
import com.raken.email.service.EmailService;
import com.raken.email.service.EnrichService;
import com.raken.email.util.InputValidator;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.raken.email.constant.ApplicationConstants.EMAIL_SERVICE_ENDPOINT;

/**
 * @author Dayan Kodippily
 */
@OpenAPIDefinition(info = @Info(title = "Email Notification API", version = "1.0"))
@RestController
public class EmailNotificationController extends NotificationController{

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailNotificationController.class);

    @Autowired
    EmailService emailService;

    @Autowired
    EnrichService enrichService;


    @PostMapping(path = EMAIL_SERVICE_ENDPOINT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendEmail(@RequestBody() EmailDto input,
                                       @RequestParam(value = ApplicationConstants.ENRICH, required = false) boolean enrich) throws SendEmailException, BadRequestException {

        InputValidator.validate(input);

        if (enrich) {
            decorate(input);
        }

        LOGGER.debug("EmailNotificationController - sendEmail() - {}", MDC.get(ApplicationConstants.CORRELATION_ID));
        emailService.send(input);

        /**
         * The url is build assuming that emails are pushed to a message Queue
         * Request correlation ID is added through a business specific HTTP header MAIL_QUEUE_ID
         */
        URI path = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(MDC.get(ApplicationConstants.CORRELATION_ID))
                .toUri();

        return ResponseEntity.accepted().header("MAIL_QUEUE_ID", path.getPath()).body("Email is queued.");
    }

    private void decorate(EmailDto emailDto) {
        String quoteOfTheDay = enrichService.getQuoteOfTheDay();
        if (StringUtils.isNotBlank(quoteOfTheDay)) {
            StringBuilder builder = new StringBuilder(emailDto.getContent());
            builder.append(ApplicationConstants.HTML_LINE_BREAK).append(ApplicationConstants.HTML_LINE_BREAK).append(
                    ApplicationConstants.ENRICH_HEADING).append(ApplicationConstants.HTML_LINE_BREAK).append(quoteOfTheDay);
            emailDto.setContent(builder.toString());
        }
    }
}
