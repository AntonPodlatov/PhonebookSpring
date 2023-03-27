package ru.academit.podlatov.phonebookspring.converter.contact;

import org.springframework.stereotype.Service;
import ru.academit.podlatov.phonebookspring.dto.ContactDto;
import ru.academit.podlatov.phonebookspring.model.contact.Contact;

import java.util.List;

@Service
public class DtoToContactConverterImpl implements DtoToContactConverter {
    @Override
    public Contact convert(ContactDto contactDto) {
        return new Contact(
                contactDto.getFirstName(),
                contactDto.getLastName(),
                contactDto.getPhone());
    }

    @Override
    public List<Contact> convert(List<ContactDto> contactDtos) {
        return contactDtos.stream().map(this::convert).toList();
    }
}