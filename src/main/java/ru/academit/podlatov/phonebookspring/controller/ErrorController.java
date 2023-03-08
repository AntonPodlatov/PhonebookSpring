package ru.academit.podlatov.phonebookspring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.academit.podlatov.phonebookspring.model.ErrorInfo;
import ru.academit.podlatov.phonebookspring.service.exception.newcontactvalidation.NewContactValidationException;

import java.util.List;


@RestControllerAdvice
public class ErrorController {
    public static final Logger log = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorInfo processException(Exception e) {
        String message = e.getMessage();
        log.error(message);
        return new ErrorInfo(e.getMessage());
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorInfo processException(HttpMediaTypeNotSupportedException e) {
        String message = e.getMessage();
        log.error(message);
        return new ErrorInfo("Bad request: " + message + ".");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorInfo processException(HttpMessageNotReadableException e) {
        String message = e.getMessage();
        log.error(message);
        message = message.substring(0, message.indexOf(":"));
        return new ErrorInfo("Bad request: " + message + ".");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorInfo processException(NewContactValidationException e) {
        List<String> messages = e.getMessages();
        log.error(String.valueOf(messages));
        return new ErrorInfo(messages);
    }
}