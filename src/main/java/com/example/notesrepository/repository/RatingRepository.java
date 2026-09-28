package com.example.notesrepository.repository;

import com.example.notesrepository.entity.Note;
import com.example.notesrepository.entity.Rating;
import com.example.notesrepository.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    boolean existsByNoteAndStudent(Note note, Student student);

    List<Rating> findByNote(Note note);
}