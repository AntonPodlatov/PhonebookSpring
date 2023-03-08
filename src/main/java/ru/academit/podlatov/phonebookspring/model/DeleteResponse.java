package ru.academit.podlatov.phonebookspring.model;

import java.util.List;

public class DeleteResponse {
    private String message;
    private List<Integer> removedContactsIds;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Integer> getRemovedContactsIds() {
        return removedContactsIds;
    }

    public void setRemovedContactsIds(List<Integer> removedContactsIds) {
        this.removedContactsIds = removedContactsIds;
    }
}