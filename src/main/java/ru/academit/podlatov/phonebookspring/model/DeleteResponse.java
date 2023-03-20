package ru.academit.podlatov.phonebookspring.model;

import java.util.List;

public class DeleteResponse {
    private String message;
    private List<Long> removedContactsIds;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Long> getRemovedContactsIds() {
        return removedContactsIds;
    }

    public void setRemovedContactsIds(List<Long> removedContactsIds) {
        this.removedContactsIds = removedContactsIds;
    }
}