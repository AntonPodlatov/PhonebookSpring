package ru.academit.podlatov.phonebookspring.service;

import org.springframework.stereotype.Service;
import ru.academit.podlatov.phonebookspring.dao.calldao.CallDaoImpl;
import ru.academit.podlatov.phonebookspring.dao.contactdao.ContactDaoImpl;
import ru.academit.podlatov.phonebookspring.model.DeleteResponse;
import ru.academit.podlatov.phonebookspring.model.call.Call;
import ru.academit.podlatov.phonebookspring.model.call.CallDirection;
import ru.academit.podlatov.phonebookspring.model.contact.Contact;
import ru.academit.podlatov.phonebookspring.service.exception.deletecontact.DeleteContactException;
import ru.academit.podlatov.phonebookspring.service.exception.deletecontact.DeleteContactMessages;
import ru.academit.podlatov.phonebookspring.service.exception.newcontactvalidation.NewContactValidationException;
import ru.academit.podlatov.phonebookspring.service.exception.newcontactvalidation.NewContactValidationMessages;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class ContactService {
    private final ContactDaoImpl contactDao;
    private final CallDaoImpl callDao;

    public ContactService(ContactDaoImpl contactDao, CallDaoImpl callDao) {
        this.contactDao = contactDao;
        this.callDao = callDao;
    }

    public Contact addContact(Contact contact) {
        validateContact(contact);
        return contactDao.add(contact);
    }

    public Call addCall(Call call) {
        validateCall(call);
        return callDao.add(call);
    }

    public Contact getContactWithCalls(Long id) {
        return contactDao.getContactWithCalls(id);
    }

    public List<Contact> getAllContactsByTerm(String filter) {
        return contactDao.getByFilter(filter);
    }

    private boolean isExistContactWithPhone(String phone) {
        return contactDao.getByPhone(phone).size() > 0;
    }

    public DeleteResponse deleteContactsByIds(Set<Long> ids) {
        if (ids == null) {
            throw new DeleteContactException(DeleteContactMessages.NO_ID_LIST);
        }

        if (ids.size() == 0) {
            throw new DeleteContactException(DeleteContactMessages.ID_LIST_IS_EMPTY);
        }

        DeleteResponse response = new DeleteResponse();
        Set<Long> removedContactsIds = contactDao.deleteByIds(ids);
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

    private void validateCall(Call call) {
        if (call.getCallStartTime().after(call.getCallEndTime())) {
            throw new RuntimeException("Начало звонка не может быть позже его завершения.");
        }
    }
}