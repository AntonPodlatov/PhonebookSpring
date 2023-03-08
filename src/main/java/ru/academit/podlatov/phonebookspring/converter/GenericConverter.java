package ru.academit.podlatov.phonebookspring.converter;

import java.util.List;

public interface GenericConverter<Source, Dest> {
    Dest convert(Source source);

    List<Dest> convert(List<Source> sources);
}
