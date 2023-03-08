package ru.academit.podlatov.phonebookspring.service.exception.newcontactvalidation;

public class NewContactValidationMessages {
    public final static String
            NO_FIRST_NAME = "Поле Имя должно быть заполнено.",
            NO_LAST_NAME = "Поле Фамилия должно быть заполнено.",
            NO_PHONE = "Поле Телефон должно быть заполнено.",
            PHONE_MUST_NOT_DUPLICATES = "Номер телефона не должен дублировать другие номера в телефонной книге.";
}