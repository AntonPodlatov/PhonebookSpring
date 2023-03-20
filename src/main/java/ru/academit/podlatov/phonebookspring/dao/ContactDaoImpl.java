package ru.academit.podlatov.phonebookspring.dao;

import org.springframework.stereotype.Repository;
import ru.academit.podlatov.phonebookspring.model.Contact;

import java.util.List;

@Repository
public class ContactDaoImpl extends GenericDaoImpl<Contact, Long> implements ContactDao {

    public ContactDaoImpl() {
        super(Contact.class);
    }

    public Contact add(Contact contact) {
        super.create(contact);
        return contact;
    }

    @Override
    public List<Contact> getByFilter(String filter) {
        return super.getByFilter(filter);
    }
}
