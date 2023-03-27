package ru.academit.podlatov.phonebookspring.scedule;


import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.academit.podlatov.phonebookspring.model.call.Call;
import ru.academit.podlatov.phonebookspring.model.call.CallDirection;
import ru.academit.podlatov.phonebookspring.model.contact.Contact;
import ru.academit.podlatov.phonebookspring.service.ContactService;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class ContactCreatorOnStartup {

    private final ContactService contactService;

    public ContactCreatorOnStartup(ContactService contactService) {
        this.contactService = contactService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createContacts() {
        Contact contact1 = new Contact("Massimo", "Magrini", "943895739345");
        Contact contact2 = new Contact("Boris", "Nikolayev", "90012301231");
        Contact contact3 = new Contact("Marcos", "Ortega", "90012301341");

        contact1 = contactService.addContact(contact1);
        contact2 = contactService.addContact(contact2);
                   contactService.addContact(contact3);

        contactService.addCall(new Call(contact1,
                new Timestamp(new Date().getTime()),
                new Timestamp(new Date().toInstant().plus(7, ChronoUnit.MINUTES).toEpochMilli()),
                CallDirection.INCOMING));

        contactService.addCall(new Call(contact1,
                new Timestamp(new Date().getTime()),
                new Timestamp(new Date().toInstant().plus(21, ChronoUnit.MINUTES).toEpochMilli()),
                CallDirection.OUTGOING));

        contactService.addCall(new Call(contact1,
                new Timestamp(new Date().getTime()),
                new Timestamp(new Date().toInstant().plus(32, ChronoUnit.MINUTES).toEpochMilli()),
                CallDirection.INCOMING));

        contactService.addCall(new Call(contact2,
                new Timestamp(new Date().getTime()),
                new Timestamp(new Date().toInstant().plus(7, ChronoUnit.MINUTES).toEpochMilli()),
                CallDirection.OUTGOING));
    }
}