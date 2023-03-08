package ru.academit.podlatov.phonebookspring.model;

import java.util.ArrayList;
import java.util.List;

public class ErrorInfo {
    private List<String> messages = new ArrayList<>();

    public ErrorInfo(String message) {
        messages.add(message);
    }

    public ErrorInfo(List<String> messages) {
        this.messages = messages;
    }
}
