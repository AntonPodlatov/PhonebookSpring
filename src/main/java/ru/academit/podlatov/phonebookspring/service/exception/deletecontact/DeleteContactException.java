package ru.academit.podlatov.phonebookspring.service.exception.deletecontact;

public class DeleteContactException extends RuntimeException {
    public DeleteContactException(String message) {
        super(message);
    }
}
