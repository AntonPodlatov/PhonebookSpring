package ru.academit.podlatov.phonebookspring.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.academit.podlatov.phonebookspring.model.Contact;
import ru.academit.podlatov.phonebookspring.model.DeleteResponse;
import ru.academit.podlatov.phonebookspring.service.exception.deletecontact.DeleteContactException;
import ru.academit.podlatov.phonebookspring.service.exception.deletecontact.DeleteContactMessages;
import ru.academit.podlatov.phonebookspring.service.exception.newcontactvalidation.NewContactValidationException;
import ru.academit.podlatov.phonebookspring.service.exception.newcontactvalidation.NewContactValidationMessages;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
class ContactServiceTest {
    private final ContactService service;

    @Autowired
    ContactServiceTest(ContactService service) {
        this.service = service;
    }

    @Test
    public void getAllByTerm() {
        List<Contact> contacts = service.getAllByTerm(null);
        Assertions.assertEquals(3, contacts.size());
    }

    @Test
    public void addContact() throws NewContactValidationException {
        String firstName = "FirstName", lastName = "LastName", phone = "3824672843";

        Contact contact = service.addContact(new Contact(firstName, lastName, phone));

        Assertions.assertEquals(contact.getId(), service.getAllByTerm(null).size());
        Assertions.assertEquals(contact.getFirstName(), firstName);
        Assertions.assertEquals(contact.getLastName(), lastName);
        Assertions.assertEquals(contact.getPhone(), phone);
    }

    @Test
    public void deleteByIds() throws DeleteContactException {
        List<Contact> contacts = service.getAllByTerm(null);

        Contact firstContact = contacts.get(0);
        DeleteResponse response = service.deleteByIds(List.of(firstContact.getId()));

        Assertions.assertEquals(response.getRemovedContactsIds().get(0), firstContact.getId());
        Assertions.assertNull(response.getMessage());
    }

    @Test()
    public void deleteExceptionThrownWhenArgIsNull() {
        DeleteContactException thrown = Assertions
                .assertThrows(
                        DeleteContactException.class,
                        () -> service.deleteByIds(null));

        Assertions.assertEquals(DeleteContactMessages.NO_ID_LIST, thrown.getMessage());
    }

    @Test()
    public void deleteExceptionThrownWhenArgListIsEmpty() {
        DeleteContactException thrown = Assertions
                .assertThrows(
                        DeleteContactException.class,
                        () -> service.deleteByIds(new ArrayList<>()));

        Assertions.assertEquals(DeleteContactMessages.ID_LIST_IS_EMPTY, thrown.getMessage());
    }

    @Test()
    public void addContactExceptionThrownWhenFirstNameIsNull() {
        Contact contact = new Contact();
        NewContactValidationException thrown = Assertions
                .assertThrows(
                        NewContactValidationException.class,
                        () -> service.addContact(contact));

        Assertions.assertTrue(
                thrown.getMessages().containsAll(List.of(
                        NewContactValidationMessages.NO_FIRST_NAME,
                        NewContactValidationMessages.NO_LAST_NAME,
                        NewContactValidationMessages.NO_PHONE)));
    }
}