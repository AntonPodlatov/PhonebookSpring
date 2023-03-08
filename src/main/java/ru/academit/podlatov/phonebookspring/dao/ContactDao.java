package ru.academit.podlatov.phonebookspring.dao;

import org.springframework.stereotype.Repository;
import ru.academit.podlatov.phonebookspring.model.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

@Repository
public class ContactDao {
    private final List<Contact> contacts = new ArrayList<>();
    private final AtomicInteger idSequence = new AtomicInteger(0);

    public ContactDao() {
        contacts.addAll(List.of(
                new Contact(addAndGetId(), "Massimo", "Magrini", "23412312321"),
                new Contact(addAndGetId(), "Marcos", "Ortega", "21223412321"),
                new Contact(addAndGetId(), "Louis", "Vazquez", "22346795434")
        ));
    }

    private int addAndGetId() {
        return idSequence.addAndGet(1);
    }

    public Contact add(Contact contact) {
        contact.setId(addAndGetId());
        contacts.add(contact);
        return contact;
    }

    public List<Contact> getAll() {
        return contacts;
    }

    public List<Contact> getAllByTerm(String term) {
        if (term == null) {
            return contacts;
        }

        Predicate<Contact> contactAnyFieldContainsFilter = contact ->
                contact.getFirstName().toLowerCase().contains(term.toLowerCase()) ||
                        contact.getLastName().toLowerCase().contains(term.toLowerCase()) ||
                        contact.getPhone().toLowerCase().contains(term.toLowerCase());

        return contacts.stream().filter(contactAnyFieldContainsFilter).toList();
    }

    public List<Integer> delete(List<Integer> ids) {
        List<Integer> removedContactsIds = new ArrayList<>();

        contacts.removeIf(contact -> {
            if (ids.contains(contact.getId())) {
                removedContactsIds.add(contact.getId());
                return true;
            }
            return false;
        });

        return removedContactsIds;
    }
}

