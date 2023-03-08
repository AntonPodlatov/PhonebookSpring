package ru.academit.podlatov.phonebookspring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.academit.podlatov.phonebookspring.service.XlsxService;

@RestController
@RequestMapping("/phoneBook/rest/api/v1/xlsx")
public class XlsxController {
    private final XlsxService xlsxService;

    public XlsxController(XlsxService xlsxService) {
        this.xlsxService = xlsxService;
    }

    @GetMapping(
            value = "contacts",
            produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    )
    public @ResponseBody byte[] downloadAllContacts() throws Exception {
        return xlsxService.getAllContactsXlsxByteArray();
    }
}