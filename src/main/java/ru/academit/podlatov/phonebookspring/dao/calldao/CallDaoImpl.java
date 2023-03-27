package ru.academit.podlatov.phonebookspring.dao.calldao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.academit.podlatov.phonebookspring.dao.GenericDaoImpl;
import ru.academit.podlatov.phonebookspring.model.call.Call;

import java.util.List;

@Repository
public class CallDaoImpl extends GenericDaoImpl<Call, Long> implements CallDao {

    public CallDaoImpl() {
        super(Call.class);
    }

    @Transactional
    public Call add(Call call) {
        super.create(call);
        return call;
    }

    @Override
    @Transactional
    public List<Call> getByContactId(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Call> criteriaQuery = criteriaBuilder.createQuery(Call.class);
        Root<Call> root = criteriaQuery.from(Call.class);

        criteriaQuery.where(criteriaBuilder.equal(root.get("contact"), id));

        return entityManager.createQuery(criteriaQuery.select(root))
                .getResultList();
    }
}