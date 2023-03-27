package ru.academit.podlatov.phonebookspring.model;

import ru.academit.podlatov.phonebookspring.dto.CallDto;
import ru.academit.podlatov.phonebookspring.model.contact.Contact;

import java.util.List;

public class CallsResponse {
    private Contact contact;
    private List<CallDto> calls;

    public CallsResponse(Contact contact, List<CallDto> calls) {
        this.contact = contact;
        this.calls = calls;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<CallDto> getCalls() {
        return calls;
    }

    public void setCalls(List<CallDto> calls) {
        this.calls = calls;
    }
}
