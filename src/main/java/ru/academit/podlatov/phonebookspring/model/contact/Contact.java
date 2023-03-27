package ru.academit.podlatov.phonebookspring.model.contact;

import jakarta.persistence.*;
import ru.academit.podlatov.phonebookspring.model.call.Call;

import java.util.Set;

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

    @OneToMany(
            mappedBy = "contact",
            cascade = CascadeType.ALL
    )
    private Set<Call> calls;

    @Column
    private boolean isDeleted;

    public Contact(Long id, String firstName, String lastName, String phone, boolean important) {
        this.id = id;
        this.important = important;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public Contact(String firstName, String lastName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public Contact(Long id) {
        this.id = id;
    }

    public Contact() {
    }

    public Set<Call> getCalls() {
        return calls;
    }

    public void setCalls(Set<Call> calls) {
        this.calls = calls;
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