package ru.academit.podlatov.phonebookspring.converter.call;

import org.springframework.stereotype.Service;
import ru.academit.podlatov.phonebookspring.dto.CallDto;
import ru.academit.podlatov.phonebookspring.model.call.Call;

import java.util.List;

@Service
public class DtoToCallConverterImpl implements DtoToCallConverter {

    @Override
    public Call convert(CallDto callDto) {
        var call = new Call();
        call.setId(call.getId());
        call.setCallStartTime(callDto.getStart());
        call.setCallEndTime(callDto.getEnd());
        call.setDirection(callDto.getDirection());
        return call;
    }

    @Override
    public List<Call> convert(List<CallDto> callDtos) {
        return callDtos.stream().map(this::convert).toList();
    }
}