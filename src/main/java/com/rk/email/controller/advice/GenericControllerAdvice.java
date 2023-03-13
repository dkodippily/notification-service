package com.rk.email.controller.advice;

/**
 * @author Dayan Kodippily
 */

import com.rk.email.dto.ErrorResponseDto;
import com.rk.email.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GenericControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericControllerAdvice.class);
    public static final String ERROR_API_CALL = "API call Error - {}";

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<?> badRequest(BadRequestException e) {
        LOGGER.error(ERROR_API_CALL, e.getMessage());

        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.setErrorMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> internalServerError(Exception e) {
        LOGGER.error(ERROR_API_CALL, e.getMessage());

        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.setErrorMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
