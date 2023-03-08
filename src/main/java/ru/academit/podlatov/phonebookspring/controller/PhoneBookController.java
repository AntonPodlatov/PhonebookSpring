package ru.academit.podlatov.phonebookspring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.academit.podlatov.phonebookspring.converter.ContactToDtoConverter;
import ru.academit.podlatov.phonebookspring.converter.DtoToContactConverter;
import ru.academit.podlatov.phonebookspring.dto.ContactDto;
import ru.academit.podlatov.phonebookspring.model.DeleteResponse;
import ru.academit.podlatov.phonebookspring.service.ContactService;
import ru.academit.podlatov.phonebookspring.service.exception.deletecontact.DeleteContactException;
import ru.academit.podlatov.phonebookspring.service.exception.newcontactvalidation.NewContactValidationException;

import java.util.List;


@RestController
@RequestMapping("/phoneBook/rest/api/v1")
public class PhoneBookController {
    private static final Logger logger = LoggerFactory.getLogger(PhoneBookController.class);

    private final ContactService contactService;
    private final ContactToDtoConverter toDtoConverter;
    private final DtoToContactConverter toContactConverter;

    public PhoneBookController(ContactService contactService,
                               ContactToDtoConverter toDtoConverter,
                               DtoToContactConverter toContactConverter) {
        this.contactService = contactService;
        this.toDtoConverter = toDtoConverter;
        this.toContactConverter = toContactConverter;
    }

    @GetMapping("contacts")
    public List<ContactDto> getAllContacts(@RequestParam(required = false) String filter) {
        logger.info("called method getAllContacts");
        return toDtoConverter.convert(contactService.getAllByTerm(filter));
    }

    @PostMapping("contacts")
    public ContactDto addOneContact(@RequestBody ContactDto contactDto) throws NewContactValidationException {
        logger.info("called method addOneContact");
        var contactFromReq = toContactConverter.convert(contactDto);
        var addedContact = contactService.addContact(contactFromReq);
        return toDtoConverter.convert(addedContact);
    }

    @DeleteMapping("contacts")
    public DeleteResponse removeContactsList(@RequestParam List<Integer> ids) throws DeleteContactException {
        logger.info("called method removeContactsList");
        System.out.println(ids);
        return contactService.deleteByIds(ids);
    }
}