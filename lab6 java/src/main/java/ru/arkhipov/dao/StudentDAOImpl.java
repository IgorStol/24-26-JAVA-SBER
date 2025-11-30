package ru.arkhipov.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.arkhipov.entity.Student;

import java.util.List;

@Repository
@Transactional
public class StudentDAOImpl implements StudentDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Student> getAllStudents() {
        return entityManager.createQuery("from Student", Student.class)
                .getResultList();
    }

    @Override
    public Student getStudent(int id) {
        return entityManager.find(Student.class, id);
    }

    @Override
    public Student saveStudent(Student student) {
        if (student.getId() == 0) {
            entityManager.persist(student);
            return student;
        } else {
            return entityManager.merge(student);
        }
    }

    @Override
    public boolean deleteStudent(int id) {
        Student student = getStudent(id);
        if (student != null) {
            entityManager.remove(student);
            return true;
        }
        return false;
    }
}
