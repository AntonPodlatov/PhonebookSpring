package ru.academit.podlatov.phonebookspring.model;

import java.util.List;

public class Contact implements ConvertableToXlsxRow {
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean important;

    public Contact(int id, String firstName, String lastName, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public Contact(String firstName, String lastName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public Contact() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public List<String> getFieldsValues() {
        return List.of(String.valueOf(id), firstName, lastName, phone, important ? "Да" : "Нет");
    }

    public  List<String> getFieldsNames() {
        return List.of("Id", "Имя", "Фамилия", "Телефон", "Важный");
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", important=" + important +
                '}';
    }
}