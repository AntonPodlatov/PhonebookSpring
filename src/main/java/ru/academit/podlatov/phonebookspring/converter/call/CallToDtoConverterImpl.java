package ru.academit.podlatov.phonebookspring.converter.call;

import org.springframework.stereotype.Service;
import ru.academit.podlatov.phonebookspring.dto.CallDto;
import ru.academit.podlatov.phonebookspring.model.call.Call;

import java.util.List;

@Service
public class CallToDtoConverterImpl implements CallToDtoConverter {
    @Override
    public CallDto convert(Call call) {
        return new CallDto(
                call.getId(),
                call.getCallStartTime(),
                call.getCallEndTime(),
                call.getCallDurationInSeconds(),
                call.getDirection());
    }

    @Override
    public List<CallDto> convert(List<Call> calls) {
        return calls.stream().map(this::convert)
                .toList();
    }
}