package ru.academit.podlatov.phonebookspring.scedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.academit.podlatov.phonebookspring.model.Contact;
import ru.academit.podlatov.phonebookspring.service.ContactService;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class RandomContactRemoveScheduler {
    private final ContactService service;
    private final static Logger log = LoggerFactory
            .getLogger(RandomContactRemoveScheduler.class);

    public RandomContactRemoveScheduler(ContactService service) {
        this.service = service;
    }

    @Scheduled(fixedRate = 60000)
    public void removeRandomContact() {
        List<Contact> contacts = service.getAllByTerm(null);
        int contactsCount = contacts.size();

        if (contactsCount == 0) {
            log.info("already zero, nothing to remove.");
            return;
        }

        int randomContactIndex = ThreadLocalRandom.current()
                .nextInt(0, contactsCount);

        Contact removed = contacts.remove(randomContactIndex);
        log.info("removed: " + removed);
    }
}