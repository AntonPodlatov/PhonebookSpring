package ru.academit.podlatov.phonebookspring.service;

import org.springframework.stereotype.Service;
import ru.academit.podlatov.phonebookspring.converter.impl.ContactToDtoConverterImpl;
import ru.academit.podlatov.phonebookspring.dto.ContactDto;
import ru.academit.podlatov.phonebookspring.model.ConvertableToXlsxRow;
import ru.academit.podlatov.phonebookspring.service.xlsxtablewriter.XlsxTableWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class XlsxService {
    private final XlsxTableWriter fileCreator;
    private final ContactService contactService;
    private final ContactToDtoConverterImpl converter;

    public XlsxService(XlsxTableWriter fileCreator,
                       ContactService contactService,
                       ContactToDtoConverterImpl converter) {
        this.contactService = contactService;
        this.fileCreator = fileCreator;
        this.converter = converter;
    }

    public byte[] getAllContactsXlsxByteArray() {
        boolean isRowNumerationNeeded = true;

        List<ContactDto> convertableContacts = converter
                .convert(contactService.getAllByTerm(null));

        return getXlsxByteArray(
                convertableContacts,
                isRowNumerationNeeded);
    }

    private byte[] getXlsxByteArray(List<? extends ConvertableToXlsxRow> convertableContacts,
                                    boolean isRowNumerationNeeded
    ) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            fileCreator.writeToStream(
                    convertableContacts,
                    outputStream,
                    isRowNumerationNeeded
            );
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}