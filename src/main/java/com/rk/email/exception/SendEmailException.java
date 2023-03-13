package com.rk.email.exception;

/**
 * @author Dayan Kodippily - 11/03/23
 */

public class SendEmailException extends Exception {

    private static final long serialVersionUID = 1L;

    public SendEmailException() {
        super();
    }

    public SendEmailException(final String message) {
        super(message);
    }
}
