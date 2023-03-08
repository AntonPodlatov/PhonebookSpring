package ru.academit.podlatov.phonebookspring.service.exception.deletecontact;

public class DeleteContactMessages {
    public final static String
            NO_ID_LIST = "Нужно передать список id контактов для удаления.",
            ID_LIST_IS_EMPTY = "Переданный список id контактов для удаления пуст.",
            NO_SUCH_CONTACTS = "Не удалено ничего. Таких контактов уже нет.",
            INCOMPLETE_REMOVAL = "Удалены не все контакты. Некоторых из списка нe уже было.";
}