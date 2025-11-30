package ru.arkhipov.dao;

import ru.arkhipov.entity.Subject;

import java.util.List;

public interface SubjectDAO {

    List<Subject> getAllSubjects();

    Subject getSubject(int id);

    Subject saveSubject(Subject subject);

    boolean deleteSubject(int id);
}
