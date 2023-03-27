package ru.academit.podlatov.phonebookspring.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Set;

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
    public void delete(Set<PK> ids) {
        if (ids == null) {
            throw new IllegalArgumentException("ids is null");
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<T> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(type);
        Root<T> root = criteriaUpdate.from(type);

        criteriaUpdate.where(root.get("id").in(ids))
                .set(root.get("isDeleted"), true);

        entityManager.createQuery(criteriaUpdate)
                .executeUpdate();
    }
}