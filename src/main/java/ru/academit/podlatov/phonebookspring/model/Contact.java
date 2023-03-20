package ru.academit.podlatov.phonebookspring.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "contact")
public class Contact {
    @Id
    @Column
    @GeneratedValue
    private Long id;

    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String phone;
    @Column
    private boolean important;

    @Column
    private boolean isDeleted;

    public Contact(String firstName, String lastName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public Contact() {
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public boolean isImportant() {
        return important;
    }

    @Override
    public String toString() {
        return "Contact{id=%d, firstName='%s', lastName='%s', phone='%s, important=%s}"
                .formatted(id, firstName, lastName, phone, important);
    }
}