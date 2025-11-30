package ru.arkhipov.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.arkhipov.entity.Student;
import ru.arkhipov.entity.Subject;
import ru.arkhipov.service.StudentService;
import ru.arkhipov.service.SubjectService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MyController {

    private final StudentService studentService;
    private final SubjectService subjectService;

    public MyController(StudentService studentService, SubjectService subjectService) {
        this.studentService = studentService;
        this.subjectService = subjectService;
    }

    // ----------------- Students -----------------

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<?> getStudent(@PathVariable int id) {
        Student student = studentService.getStudent(id);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Студент с id=" + id + " не найден");
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping("/students")
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        student.setId(0);
        Student saved = studentService.saveStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Студент успешно создан с id=" + saved.getId());
    }

    @PutMapping("/students")
    public ResponseEntity<String> updateStudent(@RequestBody Student student) {
        int id = student.getId();
        if (id == 0 || studentService.getStudent(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Невозможно изменить. Студент с id=" + id + " не найден");
        }
        studentService.saveStudent(student);
        return ResponseEntity.ok("Студент с id=" + id + " успешно изменен");
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {
        boolean deleted = studentService.deleteStudent(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Невозможно удалить. Студент с id=" + id + " не найден");
        }
        return ResponseEntity.ok("Студент с id=" + id + " успешно удален");
    }

    // ----------------- Subjects -----------------

    @GetMapping("/subjects")
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/subjects/{id}")
    public ResponseEntity<?> getSubject(@PathVariable int id) {
        Subject subject = subjectService.getSubject(id);
        if (subject == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Дисциплина с id=" + id + " не найдена");
        }
        return ResponseEntity.ok(subject);
    }

    @PostMapping("/subjects")
    public ResponseEntity<String> addSubject(@RequestBody Subject subject) {
        subject.setId(0);
        Subject saved = subjectService.saveSubject(subject);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Дисциплина успешно создана с id=" + saved.getId());
    }

    @PutMapping("/subjects")
    public ResponseEntity<String> updateSubject(@RequestBody Subject subject) {
        int id = subject.getId();
        if (id == 0 || subjectService.getSubject(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Невозможно изменить. Дисциплина с id=" + id + " не найдена");
        }
        subjectService.saveSubject(subject);
        return ResponseEntity.ok("Дисциплина с id=" + id + " успешно изменена");
    }

    @DeleteMapping("/subjects/{id}")
    public ResponseEntity<String> deleteSubject(@PathVariable int id) {
        boolean deleted = subjectService.deleteSubject(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Невозможно удалить. Дисциплина с id=" + id + " не найдена");
        }
        return ResponseEntity.ok("Дисциплина с id=" + id + " успешно удалена");
    }
}
