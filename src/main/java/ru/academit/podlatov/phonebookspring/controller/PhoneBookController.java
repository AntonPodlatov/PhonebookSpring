package ru.academit.podlatov.phonebookspring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.academit.podlatov.phonebookspring.converter.contact.ContactToDtoConverter;
import ru.academit.podlatov.phonebookspring.converter.contact.DtoToContactConverter;
import ru.academit.podlatov.phonebookspring.dto.ContactDto;
import ru.academit.podlatov.phonebookspring.model.DeleteResponse;
import ru.academit.podlatov.phonebookspring.service.ContactService;
import ru.academit.podlatov.phonebookspring.service.exception.deletecontact.DeleteContactException;
import ru.academit.podlatov.phonebookspring.service.exception.newcontactvalidation.NewContactValidationException;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/phonebook/rest/api/v1")
public class PhoneBookController {
    private static final Logger logger = LoggerFactory.getLogger(PhoneBookController.class);

    private final ContactService contactService;
    private final ContactToDtoConverter contactToDtoConverter;
    private final DtoToContactConverter dtoToContactConverter;

    public PhoneBookController(ContactService contactService,
                               ContactToDtoConverter contactToDtoConverter,
                               DtoToContactConverter dtoToContactConverter) {
        this.contactToDtoConverter = contactToDtoConverter;
        this.dtoToContactConverter = dtoToContactConverter;
        this.contactService = contactService;
    }

    @GetMapping("contacts")
    public List<ContactDto> getAllContacts(@RequestParam(required = false) String filter) {
        log(filter);
        return contactToDtoConverter.convert(contactService.getAllContactsByTerm(filter));
    }

    @GetMapping("contacts/{id}")
    public ContactDto getContactWithCalls(@PathVariable Long id) {
        log(id);
        return contactToDtoConverter.convert(contactService.getContactWithCalls(id));
    }

    @PostMapping("contacts")
    public ContactDto addOneContact(@RequestBody ContactDto contactDto) throws NewContactValidationException {
        log(contactDto);
        var contactFromReq = dtoToContactConverter.convert(contactDto);
        var addedContact = contactService.addContact(contactFromReq);
        return contactToDtoConverter.convert(addedContact);
    }

    @DeleteMapping("contacts")
    public DeleteResponse removeContactsList(@RequestParam Set<Long> ids) throws DeleteContactException {
        log(ids);
        return contactService.deleteContactsByIds(ids);
    }

    private void log(Object methodArg) {
        if (methodArg == null) {
            logger.info("Called method " + getCalledMethodName() + " with no arg");
        } else {
            String argString = methodArg.toString().isBlank() ?
                    "'empty string'" : methodArg.toString();
            logger.info("Called method " + getCalledMethodName() + " with arg: " + argString);
        }
    }

    private String getCalledMethodName() {
        return Thread.currentThread()
                .getStackTrace()[3]
                .getMethodName();
    }
}