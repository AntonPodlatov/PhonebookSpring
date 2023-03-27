package ru.academit.podlatov.phonebookspring.model.call;

import jakarta.persistence.*;
import ru.academit.podlatov.phonebookspring.model.contact.Contact;

import java.sql.Timestamp;

@Entity
@Table(name = "calls")
public class Call {
    @Id
    @Column
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="contact_id", nullable=false)
    private Contact contact;

    @Column
    private Timestamp callStartTime;

    @Column
    private Timestamp callEndTime;

    @Column
    private CallDirection direction;

    @Column
    private boolean isDeleted;

    public Call(Contact contact, Timestamp callStartTime, Timestamp callEndTime, CallDirection direction) {
        this.contact = contact;
        this.callStartTime = callStartTime;
        this.callEndTime = callEndTime;
        this.direction = direction;
    }

    public Call() {

    }

    public Timestamp getCallStartTime() {
        return callStartTime;
    }

    public void setCallStartTime(Timestamp start) {
        this.callStartTime = start;
    }

    public Timestamp getCallEndTime() {
        return callEndTime;
    }

    public void setCallEndTime(Timestamp end) {
        this.callEndTime = end;
    }

    public CallDirection getDirection() {
        return direction;
    }

    public void setDirection(CallDirection direction) {
        this.direction = direction;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Contact getContactId() {
        return contact;
    }

    public void setContactId(Contact contact) {
        this.contact = contact;
    }

    public int getCallDurationInSeconds() {
        long milliseconds = callEndTime.getTime() - callStartTime.getTime();
        return (int) milliseconds / 1000;
    }

    @Override
    public String toString() {
        return "Call{id=%d, callStartTime=%s, callEndTime=%s, direction=%s}"
                .formatted(id, callStartTime, callEndTime, direction);
    }
}
