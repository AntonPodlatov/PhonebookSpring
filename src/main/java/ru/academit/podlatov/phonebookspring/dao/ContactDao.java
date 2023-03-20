package ru.academit.podlatov.phonebookspring.dao;

import ru.academit.podlatov.phonebookspring.model.Contact;

import java.util.List;

public interface ContactDao extends GenericDao<Contact, Long> {
    List<Contact> getByFilter(String filter);
}