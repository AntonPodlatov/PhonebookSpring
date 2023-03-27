package ru.academit.podlatov.phonebookspring.model;

import java.util.Set;

public class DeleteResponse {
    private String message;
    private Set<Long> removedContactsIds;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Set<Long> getRemovedContactsIds() {
        return removedContactsIds;
    }

    public void setRemovedContactsIds(Set<Long> removedContactsIds) {
        this.removedContactsIds = removedContactsIds;
    }
}