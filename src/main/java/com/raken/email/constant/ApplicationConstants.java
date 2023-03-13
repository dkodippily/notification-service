package com.raken.email.constant;

/**
 * @author Dayan Kodippily
 */
public class ApplicationConstants {

    private ApplicationConstants(){}

    public static final String BASE_API_URL = "/api/v1";
    public static final String EMAIL_SERVICE_ENDPOINT = "/emails";
    public static final String ENRICH = "enrich";
    public static final String CORRELATION_ID = "X-Correlation-ID";

    public static final String NOTIFICATION_SERVICE_API_ENDPOINT = "mail/send";
    public static final String SENDGRID_EMAIL_CONTENT_TYPE = "text/html";
    public static final String HTML_LINE_BREAK = "<br/>";
    public static final String ENRICH_HEADING = "Quote Of the Day";
}
