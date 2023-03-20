package ru.academit.podlatov.phonebookspring.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Transactional
public class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

    @PersistenceContext
    protected EntityManager entityManager;

    protected Class<T> type;

    public GenericDaoImpl(Class<T> type) {
        this.type = type;
    }

    @Override
    @Transactional
    public T getById(PK id) {
        return entityManager.find(type, id);
    }

    @Override
    @Transactional
    public void create(T t) {
        entityManager.persist(t);
    }

    @Override
    @Transactional
    public void delete(T t) {
        entityManager.remove(t);
    }

    @Override
    @Transactional
    public void update(T t) {
        entityManager.merge(t);
    }

    @Override
    @Transactional
    public List<T> getByFilter(String filter) {
        TypedQuery<T> query;
        String name = type.getName().substring(type.getName().lastIndexOf('.'));

        String qs = "from Contact c where c.isDeleted = false";
        if (filter == null) {
            query = entityManager.createQuery(qs, type);
        } else {
            query = entityManager
                    .createQuery(qs + "and c.phone like :filter or c.firstName like :filter or c.lastName like :filter", type)
                    .setParameter("filter", "%" + filter + "%");
        }

        return query.getResultList();
    }

    @Override
    @Transactional
    public List<PK> deleteByIds(List<PK> ids) {
        if (ids == null) {
            throw new IllegalArgumentException("ids is null");
        }
        Query query = entityManager
                .createNativeQuery("UPDATE Contact c set isDeleted = true where c.id in :ids returning id")
                .setParameter("ids", ids);

        return (List<PK>) query.getResultList();
    }

    @Override
    public List<T> getByPhone(String filter) {
        if (filter == null) {
            throw new IllegalArgumentException("Нужна строка-фильтр для номера телефона");
        }
        var qs = "SELECT c FROM Contact c WHERE c.isDeleted = false and c.phone = :filter";
        return entityManager.createQuery(qs, type)
                .setParameter("filter", filter)
                .getResultList();
    }
}