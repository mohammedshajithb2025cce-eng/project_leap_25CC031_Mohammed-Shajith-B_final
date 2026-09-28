package com.example.notesrepository.repository;

import com.example.notesrepository.entity.Note;
import com.example.notesrepository.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findBySubject(Subject subject);

    List<Note> findByUnit(String unit);

    List<Note> findBySubjectAndUnit(Subject subject, String unit);
}