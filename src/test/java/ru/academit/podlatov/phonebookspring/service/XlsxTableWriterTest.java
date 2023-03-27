package ru.academit.podlatov.phonebookspring.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.academit.podlatov.phonebookspring.dto.ContactDto;
import ru.academit.podlatov.phonebookspring.model.contact.Contact;
import ru.academit.podlatov.phonebookspring.service.xlsxtablewriter.XlsxTableWriter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

@SpringBootTest
@RunWith(SpringRunner.class)
public class XlsxTableWriterTest {

    @Autowired
    private XlsxTableWriter writer;

    @Test
    public void thrownIllegalArgumentExceptionTest() {
        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> writer.writeToStream(
                            new ArrayList<ContactDto>(),
                            new ByteArrayOutputStream(),
                            true));
    }
}