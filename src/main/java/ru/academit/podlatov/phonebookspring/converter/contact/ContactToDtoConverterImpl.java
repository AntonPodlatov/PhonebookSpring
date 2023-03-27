package ru.academit.podlatov.phonebookspring.converter.contact;

import org.springframework.stereotype.Service;
import ru.academit.podlatov.phonebookspring.converter.call.CallToDtoConverter;
import ru.academit.podlatov.phonebookspring.dto.ContactDto;
import ru.academit.podlatov.phonebookspring.model.contact.Contact;

import java.util.HashSet;
import java.util.List;

@Service
public class ContactToDtoConverterImpl implements ContactToDtoConverter {

    final CallToDtoConverter callToDtoConverter;

    public ContactToDtoConverterImpl(CallToDtoConverter callToDtoConverter) {
        this.callToDtoConverter = callToDtoConverter;
    }

    @Override
    public ContactDto convert(Contact contact) {
        ContactDto contactDto = new ContactDto(
                contact.getId(),
                contact.getFirstName(),
                contact.getLastName(),
                contact.getPhone(),
                contact.isImportant());

        if (contact.getCalls() != null) {
            var calls = contact.getCalls().stream().toList();
            contactDto.setCalls(new HashSet<>(callToDtoConverter.convert(calls)));
        }

        return contactDto;
    }

    @Override
    public List<ContactDto> convert(List<Contact> contacts) {
        return contacts.stream().map(this::convert).toList();
    }
}