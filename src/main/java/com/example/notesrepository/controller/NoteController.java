package com.example.notesrepository.controller;

import com.example.notesrepository.entity.Note;
import com.example.notesrepository.entity.Student;
import com.example.notesrepository.entity.Subject;
import com.example.notesrepository.service.NoteService;
import com.example.notesrepository.service.StudentService;
import com.example.notesrepository.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;
    private final SubjectService subjectService;
    private final StudentService studentService;

    public NoteController(
            NoteService noteService,
            SubjectService subjectService,
            StudentService studentService) {

        this.noteService = noteService;
        this.subjectService = subjectService;
        this.studentService = studentService;
    }

    @GetMapping
    public List<Note> getAllNotes() {
        return noteService.getAllNotes();
    }

    @GetMapping("/search")
    public List<Note> searchNotes(
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) String unit) {

        if (subject != null && unit != null) {

            Subject subjectObject =
                    subjectService.getSubjectByName(subject);

            return noteService.searchBySubjectAndUnit(
                    subjectObject, unit);
        }

        if (subject != null) {

            Subject subjectObject =
                    subjectService.getSubjectByName(subject);

            return noteService.searchBySubject(subjectObject);
        }

        if (unit != null) {
            return noteService.searchByUnit(unit);
        }

        return noteService.getAllNotes();
    }
    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadNote(
            @PathVariable Long id) {

        Note note = noteService.getNoteById(id);

        Resource resource =
                noteService.downloadNote(id);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                note.getFileName() + "\""
                )
                .header(
                        HttpHeaders.CONTENT_TYPE,
                        note.getFileType()
                )
                .body(resource);
    }

    @GetMapping("/{id}")
    public Note getNoteById(@PathVariable Long id) {
        return noteService.getNoteById(id);
    }

    @PostMapping
    public Note uploadNote(
            @RequestParam String title,
            @RequestParam String unit,
            @RequestParam Long studentId,
            @RequestParam Long subjectId,
            @RequestParam MultipartFile file) {

        Student student =
                studentService.getStudentById(studentId);

        Subject subject =
                subjectService.getSubjectById(subjectId);

        return noteService.uploadNote(
                title,
                unit,
                student,
                subject,
                file
        );
    }

    @PutMapping("/{id}")
    public Note updateNote(
            @PathVariable Long id,
            @Valid @RequestBody Note note) {

        return noteService.updateNote(id, note);
    }

    @DeleteMapping("/{id}")
    public String deleteNote(@PathVariable Long id) {

        noteService.deleteNote(id);

        return "Note deleted successfully";
    }

}