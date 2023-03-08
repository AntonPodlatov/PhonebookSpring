package ru.academit.podlatov.phonebookspring.converter;

import ru.academit.podlatov.phonebookspring.dto.ContactDto;
import ru.academit.podlatov.phonebookspring.model.Contact;

public interface ContactToDtoConverter extends GenericConverter<Contact, ContactDto> {
}