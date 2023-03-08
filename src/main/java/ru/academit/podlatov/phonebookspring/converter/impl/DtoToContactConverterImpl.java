package ru.academit.podlatov.phonebookspring.converter.impl;

import org.springframework.stereotype.Service;
import ru.academit.podlatov.phonebookspring.converter.DtoToContactConverter;
import ru.academit.podlatov.phonebookspring.dto.ContactDto;
import ru.academit.podlatov.phonebookspring.model.Contact;

import java.util.List;

@Service
public class DtoToContactConverterImpl implements DtoToContactConverter {
    @Override
    public Contact convert(ContactDto contactDto) {
        return new Contact(
                        contactDto.getId(),
                        contactDto.getFirstName(),
                        contactDto.getLastName(),
                        contactDto.getPhone());
    }

    @Override
    public List<Contact> convert(List<ContactDto> contactDtos) {
        return contactDtos.stream().map(this::convert).toList();
    }
}