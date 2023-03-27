package ru.academit.podlatov.phonebookspring.dao.contactdao;

import ru.academit.podlatov.phonebookspring.dao.GenericDao;
import ru.academit.podlatov.phonebookspring.model.contact.Contact;

import java.util.List;
import java.util.Set;

public interface ContactDao extends GenericDao<Contact, Long> {
    List<Contact> getByFilter(String filter);
    List<Contact> getByPhone(String filter);
    Set<Long> deleteByIds(Set<Long> ids);
}