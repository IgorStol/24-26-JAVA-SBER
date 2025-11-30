package ru.arkhipov.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.arkhipov.entity.Subject;

import java.util.List;

@Repository
@Transactional
public class SubjectDAOImpl implements SubjectDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Subject> getAllSubjects() {
        return entityManager.createQuery("from Subject", Subject.class)
                .getResultList();
    }

    @Override
    public Subject getSubject(int id) {
        return entityManager.find(Subject.class, id);
    }

    @Override
    public Subject saveSubject(Subject subject) {
        if (subject.getId() == 0) {
            entityManager.persist(subject);
            return subject;
        } else {
            return entityManager.merge(subject);
        }
    }

    @Override
    public boolean deleteSubject(int id) {
        Subject subject = getSubject(id);
        if (subject != null) {
            entityManager.remove(subject);
            return true;
        }
        return false;
    }
}
