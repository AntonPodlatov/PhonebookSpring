package ru.academit.podlatov.phonebookspring.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class XlsxServiceTest {

    @Autowired
    private XlsxService xlsxService;

    @Test
    public void getAllContactsXlsxByteArrayDoesNotThrowException() {
        Assertions.assertDoesNotThrow(xlsxService::getAllContactsXlsxByteArray);
    }
}