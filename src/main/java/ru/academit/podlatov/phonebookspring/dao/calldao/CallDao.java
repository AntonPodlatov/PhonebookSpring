package ru.academit.podlatov.phonebookspring.dao.calldao;

import ru.academit.podlatov.phonebookspring.dao.GenericDao;
import ru.academit.podlatov.phonebookspring.model.call.Call;

import java.util.List;

public interface CallDao extends GenericDao<Call, Long> {
    List<Call> getByContactId(Long id);
}