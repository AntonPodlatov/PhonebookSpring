package ru.academit.podlatov.phonebookspring.converter.impl;

import org.springframework.stereotype.Service;
import ru.academit.podlatov.phonebookspring.converter.ContactToDtoConverter;
import ru.academit.podlatov.phonebookspring.dto.ContactDto;
import ru.academit.podlatov.phonebookspring.model.Contact;

import java.util.List;

@Service
public class ContactToDtoConverterImpl implements ContactToDtoConverter {
    @Override
    public ContactDto convert(Contact contact) {
        return new ContactDto(
                        contact.getId(),
                        contact.getFirstName(),
                        contact.getLastName(),
                        contact.getPhone());
    }

    @Override
    public List<ContactDto> convert(List<Contact> contacts) {
        return contacts.stream().map(this::convert).toList();
    }
}