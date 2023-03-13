package com.raken.email.notification.sendgrid.util;

import com.sendgrid.Response;

/**
 * @author Dayan Kodippily
 */

/**
 * This class will translate the actual API errors and return End-user friendly messages.
 *
 */

public class ExceptionTranslator {

    private ExceptionTranslator(){}
    public static final String API_ERROR = "API call error.";
    private static final String SENDER_NOT_REGISTERED = "The from address does not match a verified Sender Identity.";

    private static final String NON_UNIQUE_ADDRESSES = "Each email address in the personalization block should be unique between to, cc, and bcc.";

    public static String translate(Response response){
        if(response.getBody().contains(SENDER_NOT_REGISTERED)) {
           return  SENDER_NOT_REGISTERED;
        }else if(response.getBody().contains(NON_UNIQUE_ADDRESSES)){
           return NON_UNIQUE_ADDRESSES;
        }
        return API_ERROR;
    }
}