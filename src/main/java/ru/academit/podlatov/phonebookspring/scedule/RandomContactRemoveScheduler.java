package ru.academit.podlatov.phonebookspring.scedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.academit.podlatov.phonebookspring.model.contact.Contact;
import ru.academit.podlatov.phonebookspring.model.DeleteResponse;
import ru.academit.podlatov.phonebookspring.service.ContactService;

import java.util.Collections;
import java.util.List;
import java.util.Random;

//@Component
public class RandomContactRemoveScheduler {
    private final static Logger log = LoggerFactory.getLogger(RandomContactRemoveScheduler.class);

    private final ContactService service;

    public RandomContactRemoveScheduler(ContactService service) {
        this.service = service;
    }

//@Scheduled(fixedRate = 60000)
    public void removeRandomContact() {
        List<Contact> contacts = service.getAllContactsByTerm(null);

        if (contacts.size() == 0) {
            log.info("already zero, nothing to remove.");
            return;
        }

        Random rand = new Random();

        int randomIndex = rand.nextInt(contacts.size());
        Contact randomContact = contacts.get(randomIndex);
        DeleteResponse deleteResponse = service.deleteContactsByIds(Collections.singleton(randomContact.getId()));

        log.info("removed: contact{%s}".formatted(deleteResponse.getRemovedContactsIds()));
    }
}