package ru.academit.podlatov.phonebookspring.model.contact;

import java.util.List;

public interface ConvertableToXlsxRow {
    List<String> getFieldsValues();
    List<String> getFieldsNames();
}
