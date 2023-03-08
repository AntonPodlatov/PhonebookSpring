package ru.academit.podlatov.phonebookspring.service;

import org.springframework.stereotype.Service;
import ru.academit.podlatov.phonebookspring.model.Contact;
import ru.academit.podlatov.phonebookspring.service.workbookcreator.XlsxTableWriter;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class XlsxService {
    private final XlsxTableWriter fileCreator;
    private final ContactService contactService;

    public XlsxService(ContactService contactService, XlsxTableWriter fileCreator) {
        this.fileCreator = fileCreator;
        this.contactService = contactService;
    }

    public byte[] getAllContactsXlsxByteArray() throws Exception {
        boolean isRowNumerationNeeded = true;
        List<Contact> contacts = contactService.getAllByTerm(null);
        return getXlsxByteArray(contacts, isRowNumerationNeeded);
    }

    private byte[] getXlsxByteArray(List<Contact> contacts, boolean isRowNumerationNeeded) throws Exception {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
        ) {
            fileCreator.writeToStream(
                    contacts,
                    outputStream,
                    isRowNumerationNeeded
            );
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}