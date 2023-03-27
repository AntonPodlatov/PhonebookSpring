package ru.academit.podlatov.phonebookspring.converter.call;

import ru.academit.podlatov.phonebookspring.converter.GenericConverter;
import ru.academit.podlatov.phonebookspring.dto.CallDto;
import ru.academit.podlatov.phonebookspring.model.call.Call;

public interface CallToDtoConverter extends GenericConverter<Call, CallDto> {
}