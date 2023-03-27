package ru.academit.podlatov.phonebookspring.dao;

import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.Set;

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
    void delete(Set<PK> ids);
}