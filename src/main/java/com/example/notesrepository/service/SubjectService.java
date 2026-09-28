package com.example.notesrepository.service;

import com.example.notesrepository.entity.Subject;
import com.example.notesrepository.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Subject getSubjectById(Long id) {
        Optional<Subject> subject = subjectRepository.findById(id);

        if (subject.isEmpty()) {
            throw new RuntimeException("Subject not found with id: " + id);
        }

        return subject.get();
    }

    public Subject getSubjectByName(String name) {
        Optional<Subject> subject = subjectRepository.findByName(name);

        if (subject.isEmpty()) {
            throw new RuntimeException("Subject not found: " + name);
        }

        return subject.get();
    }

    public Subject saveSubject(Subject subject) {
        return subjectRepository.save(subject);
    }
}