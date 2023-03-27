package ru.academit.podlatov.phonebookspring.dao.contactdao;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import ru.academit.podlatov.phonebookspring.dao.GenericDaoImpl;
import ru.academit.podlatov.phonebookspring.model.contact.Contact;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;


@Repository
public class ContactDaoImpl extends GenericDaoImpl<Contact, Long> implements ContactDao {

    public ContactDaoImpl() {
        super(Contact.class);
    }

    @Transactional
    public Contact add(Contact contact) {
        contact.setDeleted(false);
        super.create(contact);
        return contact;
    }

    @Override
    @Transactional
    public List<Contact> getByFilter(String filter) {
        if (filter == null) {
            filter = "";
        }

        String selectContactsWithoutCalls = """
                SELECT NEW ru.academit.podlatov.phonebookspring.model.contact.Contact(
                   contact.id,
                   contact.firstName,
                   contact.lastName,
                   contact.phone,
                   contact.important
                )
                FROM Contact contact WHERE
                   contact.isDeleted = false
                AND(
                   lower(contact.phone) like :filter or
                   lower(contact.lastName) like :filter or
                   lower(contact.firstName) like :filter)
                """;

        return entityManager.createQuery(selectContactsWithoutCalls, Contact.class).setParameter("filter", "%" + filter.toLowerCase() + "%").getResultList();
    }

    @Override
    @Transactional
    public List<Contact> getByPhone(String filter) {
        if (filter == null || filter.isEmpty()) {
            throw new IllegalArgumentException("Нужна строка-фильтр для номера телефона");
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Contact> criteriaQuery = criteriaBuilder.createQuery(Contact.class);
        Root<Contact> root = criteriaQuery.from(Contact.class);

        Predicate[] predicates = new Predicate[]{criteriaBuilder.like(root.get("phone"), "%" + filter + "%"), criteriaBuilder.equal(root.get("isDeleted"), false)};
        criteriaQuery.where(criteriaBuilder.and(predicates));

        return entityManager.createQuery(criteriaQuery.select(root)).getResultList();
    }

    @Override
    @Transactional
    public Set<Long> deleteByIds(Set<Long> ids) {
        if (ids == null) {
            throw new IllegalArgumentException("ids is null");
        }

        List<Contact> contactsForDelete = entityManager.createQuery("""
                SELECT NEW ru.academit.podlatov.phonebookspring.model.contact.Contact(contact.id)
                FROM Contact contact
                WHERE contact.isDeleted = false AND contact.id IN (:ids)""", Contact.class)
                .setParameter("ids", ids)
                .getResultList();

        List<Long> contactsForDeleteIds = contactsForDelete.stream().map(Contact::getId).toList();

        entityManager.createQuery("""
                UPDATE Contact contact
                SET isDeleted = true
                WHERE contact.isDeleted = false AND contact.id IN (:ids)""")
                .setParameter("ids", contactsForDeleteIds)
                .executeUpdate();

        entityManager.createQuery("""
                UPDATE Call call
                SET isDeleted = true
                WHERE call.isDeleted = false AND call.contact.id IN (:ids)""")
                .setParameter("ids", contactsForDeleteIds)
                .executeUpdate();

        return new TreeSet<>(contactsForDeleteIds);
    }

    @Transactional
    public Contact getContactWithCalls(Long contactId) {
        Query query = entityManager.createQuery("""
                SELECT contact FROM Contact contact
                LEFT JOIN FETCH contact.calls
                WHERE contact.id =:contactId AND contact.isDeleted = false
                """).setParameter("contactId", contactId);
        return (Contact) query.getSingleResult();
    }
}