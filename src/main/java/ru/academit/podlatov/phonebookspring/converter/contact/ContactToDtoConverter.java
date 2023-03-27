package ru.academit.podlatov.phonebookspring.converter.contact;

import ru.academit.podlatov.phonebookspring.converter.GenericConverter;
import ru.academit.podlatov.phonebookspring.dto.ContactDto;
import ru.academit.podlatov.phonebookspring.model.contact.Contact;

public interface ContactToDtoConverter extends GenericConverter<Contact, ContactDto> {
}