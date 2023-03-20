package ru.academit.podlatov.phonebookspring.dao;

import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, PK extends Serializable> {
    @Transactional
    void create(T t);

    @Transactional
    T getById(PK id);

    @Transactional
    void update(T t);

    @Transactional
    void delete(T t);

    @Transactional
    List<T> getByFilter(String filter);

    @Transactional
    List<PK> deleteByIds(List<PK> ids);

    @Transactional
    List<T> getByPhone(String filter);
}