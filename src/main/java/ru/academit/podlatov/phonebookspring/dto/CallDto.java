package ru.academit.podlatov.phonebookspring.dto;

import ru.academit.podlatov.phonebookspring.model.call.CallDirection;

import java.sql.Timestamp;

public class CallDto {
    private Long id;
    private Timestamp start;
    private Timestamp end;
    private CallDirection direction;
    private int durationInSeconds;

    public CallDto(Long id, Timestamp start, Timestamp end, int durationInSeconds, CallDirection direction) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.direction = direction;
        this.durationInSeconds = durationInSeconds;
    }

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public CallDirection getDirection() {
        return direction;
    }

    public void setDirection(CallDirection direction) {
        this.direction = direction;
    }
}
