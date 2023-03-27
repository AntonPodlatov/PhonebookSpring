package ru.academit.podlatov.phonebookspring.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import ru.academit.podlatov.phonebookspring.model.contact.ConvertableToXlsxRow;

import java.util.List;
import java.util.Set;

@JsonInclude(Include.NON_NULL)
public class ContactDto implements ConvertableToXlsxRow {
    private long id;
    private final String firstName;
    private final String lastName;
    private final String phone;
    private final boolean important;
    private Set<CallDto> calls;

    public ContactDto(long id, String firstName, String lastName, String phone, boolean important) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.important = important;
    }

    public boolean isImportant() {
        return important;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    @JsonIgnore
    public List<String> getFieldsValues() {
        return List.of(String.valueOf(id), firstName, lastName, phone, important ? "Да" : "Нет");
    }

    @Override
    @JsonIgnore
    public List<String> getFieldsNames() {
        return List.of("Id", "Имя", "Фамилия", "Телефон", "Важный");
    }

    @Override
    public String toString() {
        return "Contact{id=%d, firstName='%s', lastName='%s', phone='%s, important=%s}"
                .formatted(id, firstName, lastName, phone, important);
    }

    public Set<CallDto> getCalls() {
        return calls;
    }

    public void setCalls(Set<CallDto> calls) {
        this.calls = calls;
    }
}