package com.example.notesrepository.service;

import com.example.notesrepository.entity.Student;
import com.example.notesrepository.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        Optional<Student> student = studentRepository.findById(id);

        if (student.isEmpty()) {
            throw new RuntimeException("Student not found with id: " + id);
        }

        return student.get();
    }

    public Student getStudentByEmail(String email) {
        Optional<Student> student = studentRepository.findByEmail(email);

        if (student.isEmpty()) {
            throw new RuntimeException("Student not found with email: " + email);
        }

        return student.get();
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }
}