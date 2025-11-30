package ru.arkhipov.service;

import ru.arkhipov.entity.Subject;

import java.util.List;

public interface SubjectService {

    List<Subject> getAllSubjects();

    Subject getSubject(int id);

    Subject saveSubject(Subject subject);

    boolean deleteSubject(int id);
}
