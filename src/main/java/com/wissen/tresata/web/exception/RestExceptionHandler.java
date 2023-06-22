package com.wissen.tresata.web.exception;

import com.wissen.tresata.web.model.exceptionResponse.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handler for Exception.
     *
     * @param ex
     * @return Response
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> globalExceptionHandler(Exception ex) {
        log.error("Exception: {}", ex.toString());

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setDescription(ex.toString());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_GATEWAY);
    }
}
