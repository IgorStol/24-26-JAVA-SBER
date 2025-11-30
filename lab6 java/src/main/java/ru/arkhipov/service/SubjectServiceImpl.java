package ru.arkhipov.service;

import org.springframework.stereotype.Service;
import ru.arkhipov.dao.SubjectDAO;
import ru.arkhipov.entity.Subject;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectDAO subjectDAO;

    public SubjectServiceImpl(SubjectDAO subjectDAO) {
        this.subjectDAO = subjectDAO;
    }

    @Override
    public List<Subject> getAllSubjects() {
        return subjectDAO.getAllSubjects();
    }

    @Override
    public Subject getSubject(int id) {
        return subjectDAO.getSubject(id);
    }

    @Override
    public Subject saveSubject(Subject subject) {
        return subjectDAO.saveSubject(subject);
    }

    @Override
    public boolean deleteSubject(int id) {
        return subjectDAO.deleteSubject(id);
    }
}
