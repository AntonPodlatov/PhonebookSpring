package ru.academit.podlatov.phonebookspring.service;

import org.springframework.stereotype.Service;
import ru.academit.podlatov.phonebookspring.dao.ContactDao;
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
    private final ContactDao contactDao;

    public ContactService(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    private boolean isExistContactWithPhone(String phone) {
        return contactDao.getAll().stream()
                .anyMatch(contact -> contact.getPhone().equals(phone));
    }

    private void validateContact(Contact contact) throws NewContactValidationException {
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
            throw new NewContactValidationException("NewContactValidation error", validationMessages);
        }
    }

    public Contact addContact(Contact contact) throws NewContactValidationException {
        validateContact(contact);
        return contactDao.add(contact);
    }

    public List<Contact> getAllByTerm(String term) {
        return contactDao.getAllByTerm(term);
    }

    public DeleteResponse deleteByIds(List<Integer> ids) throws DeleteContactException {
        if (ids == null) {
            throw new DeleteContactException(DeleteContactMessages.NO_ID_LIST);
        }

        if (ids.size() == 0) {
            throw new DeleteContactException(DeleteContactMessages.ID_LIST_IS_EMPTY);
        }

        DeleteResponse response = new DeleteResponse();
        List<Integer> removedContactsIds = contactDao.delete(ids);
        if (removedContactsIds.size() == 0) {
            throw new DeleteContactException(DeleteContactMessages.NO_SUCH_CONTACTS);
        }

        if (removedContactsIds.size() != ids.size()) {
            response.setMessage(DeleteContactMessages.INCOMPLETE_REMOVAL);
        }

        response.setRemovedContactsIds(removedContactsIds);
        return response;
    }
}
