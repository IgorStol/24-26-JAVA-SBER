package ru.arkhipov.dao;

import ru.arkhipov.entity.Student;

import java.util.List;

public interface StudentDAO {

    List<Student> getAllStudents();

    Student getStudent(int id);

    Student saveStudent(Student student);

    boolean deleteStudent(int id);
}
