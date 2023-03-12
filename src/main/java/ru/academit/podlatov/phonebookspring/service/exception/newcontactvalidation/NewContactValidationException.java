package ru.academit.podlatov.phonebookspring.service.exception.newcontactvalidation;

import java.util.List;

public class NewContactValidationException extends RuntimeException {
    private final List<String> validationMessages;

    public NewContactValidationException(String message, List<String> validationMessages) {
        super(message);
        this.validationMessages = validationMessages;
    }

    public List<String> getMessages() {
        return validationMessages;
    }
}