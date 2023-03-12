package ru.academit.podlatov.phonebookspring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.academit.podlatov.phonebookspring.service.XlsxService;

@RestController
@RequestMapping("/phonebook/rest/api/v1/xlsx")
public class XlsxController {
    private final Logger logger = LoggerFactory.getLogger(XlsxController.class);
    private final XlsxService xlsxService;

    public XlsxController(XlsxService xlsxService) {
        this.xlsxService = xlsxService;
    }

    @GetMapping(
            value = "contacts",
            produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    )
    public @ResponseBody byte[] downloadAllContacts() {
        logger.info("Called method downloadAllContacts");
        return xlsxService.getAllContactsXlsxByteArray();
    }
}