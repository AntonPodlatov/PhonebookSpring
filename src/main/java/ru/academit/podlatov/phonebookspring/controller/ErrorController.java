package ru.academit.podlatov.phonebookspring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.academit.podlatov.phonebookspring.model.ErrorInfo;
import ru.academit.podlatov.phonebookspring.service.exception.deletecontact.DeleteContactException;
import ru.academit.podlatov.phonebookspring.service.exception.newcontactvalidation.NewContactValidationException;

import java.util.List;

@ControllerAdvice
public class ErrorController {
    public static final Logger log = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorInfo> processException(HttpMessageNotReadableException e) {
        String message = e.getMessage();
        log.error(message);
        message = message.substring(0, message.indexOf(":"));
        return ResponseEntity
                .internalServerError()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorInfo("Bad request: " + message + "."));
    }

    @ExceptionHandler(NewContactValidationException.class)
    public ResponseEntity<ErrorInfo> processException(NewContactValidationException e) {
        List<String> messages = e.getMessages();
        log.error(messages.toString());
        return ResponseEntity
                .internalServerError()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorInfo(messages));
    }

    @ExceptionHandler(DeleteContactException.class)
    public ResponseEntity<ErrorInfo> processException(DeleteContactException e) {
        String message = e.getMessage();
        log.error(message);
        return ResponseEntity
                .internalServerError()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorInfo(message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> processException(Exception e) {
        String message = e.getMessage();
        log.error(message);
        return ResponseEntity
                .internalServerError()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorInfo(message));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorInfo> processException(EmptyResultDataAccessException e){
        String message = e.getMessage();
        log.error(message);
        return ResponseEntity
                .internalServerError()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorInfo("Нет элемента с таким Id"));
    }
}