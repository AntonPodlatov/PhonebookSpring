package ru.academit.podlatov.phonebookspring.service;

import org.springframework.stereotype.Service;
import ru.academit.podlatov.phonebookspring.model.Contact;
import ru.academit.podlatov.phonebookspring.service.xlsxtablewriter.XlsxTableWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class XlsxService {
    private final XlsxTableWriter fileCreator;
    private final ContactService contactService;

    public XlsxService(ContactService contactService, XlsxTableWriter fileCreator) {
        this.fileCreator = fileCreator;
        this.contactService = contactService;
    }

    public byte[] getAllContactsXlsxByteArray() {
        boolean isRowNumerationNeeded = true;
        List<Contact> contacts = contactService.getAllByTerm(null);
        return getXlsxByteArray(contacts, isRowNumerationNeeded);
    }

    private byte[] getXlsxByteArray(List<Contact> contacts, boolean isRowNumerationNeeded)  {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            fileCreator.writeToStream(
                    contacts,
                    outputStream,
                    isRowNumerationNeeded
            );
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}