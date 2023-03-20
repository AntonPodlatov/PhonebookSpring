package ru.academit.podlatov.phonebookspring.service;

import org.springframework.stereotype.Service;
import ru.academit.podlatov.phonebookspring.dao.ContactDaoImpl;
import ru.academit.podlatov.phonebookspring.model.Contact;
import ru.academit.podlatov.phonebookspring.model.DeleteResponse;
import ru.academit.podlatov.phonebookspring.service.exception.deletecontact.DeleteContactException;
import ru.academit.podlatov.phonebookspring.service.exception.deletecontact.DeleteContactMessages;
import ru.academit.podlatov.phonebookspring.service.exception.newcontactvalidation.NewContactValidationException;
import ru.academit.podlatov.phonebookspring.service.exception.newcontactvalidation.NewContactValidationMessages;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {
    private final ContactDaoImpl contactDao;

    public ContactService(ContactDaoImpl contactDao) {
        this.contactDao = contactDao;
    }

    public Contact addContact(Contact contact) {
        validateContact(contact);
        return contactDao.add(contact);
    }

    public List<Contact> getAllByTerm(String filter) {
        return contactDao.getByFilter(filter);
    }

    private boolean isExistContactWithPhone(String phone) {
        return contactDao.getByFilter(null).stream()
                .anyMatch(contact -> contact.getPhone().equals(phone));
    }

    public DeleteResponse deleteByIds(List<Long> ids) {
        if (ids == null) {
            throw new DeleteContactException(DeleteContactMessages.NO_ID_LIST);
        }

        if (ids.size() == 0) {
            throw new DeleteContactException(DeleteContactMessages.ID_LIST_IS_EMPTY);
        }

        DeleteResponse response = new DeleteResponse();
        List<Long> removedContactsIds = contactDao.deleteByIds(ids);
        if (removedContactsIds.size() == 0) {
            throw new DeleteContactException(DeleteContactMessages.NO_SUCH_CONTACTS);
        }

        if (removedContactsIds.size() != ids.size()) {
            response.setMessage(DeleteContactMessages.INCOMPLETE_REMOVAL);
        }

        response.setRemovedContactsIds(removedContactsIds);
        return response;
    }

    private void validateContact(Contact contact) {
        List<String> validationMessages = new ArrayList<>();

        if (contact.getFirstName() == null || contact.getFirstName().isEmpty()) {
            validationMessages.add(NewContactValidationMessages.NO_FIRST_NAME);
        }

        if (contact.getLastName() == null || contact.getLastName().isEmpty()) {
            validationMessages.add(NewContactValidationMessages.NO_LAST_NAME);
        }

        if (contact.getPhone() == null || contact.getPhone().isEmpty()) {
            validationMessages.add(NewContactValidationMessages.NO_PHONE);
        }

        if (isExistContactWithPhone(contact.getPhone())) {
            validationMessages.add(NewContactValidationMessages.PHONE_MUST_NOT_DUPLICATES);
        }

        if (validationMessages.size() > 0) {
            throw new NewContactValidationException("New contact validation error.", validationMessages);
        }
    }
}