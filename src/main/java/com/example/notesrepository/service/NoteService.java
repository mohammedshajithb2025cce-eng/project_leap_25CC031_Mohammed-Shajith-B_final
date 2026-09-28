package com.example.notesrepository.service;

import com.example.notesrepository.entity.Note;
import com.example.notesrepository.entity.Student;
import com.example.notesrepository.entity.Subject;
import com.example.notesrepository.repository.NoteRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    // Get all notes
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    // Get note by ID
    public Note getNoteById(Long id) {

        return noteRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Note not found with id: " + id));
    }

    // Upload note
    public Note uploadNote(
            String title,
            String unit,
            Student student,
            Subject subject,
            MultipartFile file) {

        if (title == null || title.isBlank()) {
            throw new RuntimeException(
                    "Title cannot be empty");
        }

        if (unit == null || unit.isBlank()) {
            throw new RuntimeException(
                    "Unit cannot be empty");
        }

        if (file == null || file.isEmpty()) {
            throw new RuntimeException(
                    "Please select a file");
        }

        try {

            Path uploadPath = Paths.get(uploadDir)
                    .toAbsolutePath()
                    .normalize();

            Files.createDirectories(uploadPath);

            String originalFileName =
                    file.getOriginalFilename();

            if (originalFileName == null ||
                    originalFileName.isBlank()) {

                throw new RuntimeException(
                        "Invalid file name");
            }

            String storedFileName =
                    UUID.randomUUID()
                            + "_"
                            + originalFileName;

            Path filePath =
                    uploadPath.resolve(storedFileName);

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            Note note = new Note();

            note.setTitle(title);
            note.setUnit(unit);
            note.setFileName(originalFileName);
            note.setFileType(file.getContentType());
            note.setFilePath(filePath.toString());
            note.setUploader(student);
            note.setSubject(subject);

            return noteRepository.save(note);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Could not store the uploaded file");
        }
    }

    // Download note
    public Resource downloadNote(Long id) {

        Note note = getNoteById(id);

        try {

            Path filePath =
                    Paths.get(note.getFilePath());

            Resource resource =
                    new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                throw new RuntimeException(
                        "File not found");
            }

            return resource;

        } catch (IOException e) {

            throw new RuntimeException(
                    "Could not read the file");
        }
    }

    // Update note
    public Note updateNote(
            Long id,
            Note updatedNote) {

        Note existingNote =
                getNoteById(id);

        existingNote.setTitle(
                updatedNote.getTitle());

        existingNote.setUnit(
                updatedNote.getUnit());

        if (updatedNote.getSubject() != null) {

            existingNote.setSubject(
                    updatedNote.getSubject());
        }

        if (updatedNote.getUploader() != null) {

            existingNote.setUploader(
                    updatedNote.getUploader());
        }

        return noteRepository.save(existingNote);
    }

    // Delete note
    public void deleteNote(Long id) {

        Note note = getNoteById(id);

        noteRepository.delete(note);
    }

    // Search by subject
    public List<Note> searchBySubject(
            Subject subject) {

        return noteRepository.findBySubject(subject);
    }

    // Search by unit
    public List<Note> searchByUnit(
            String unit) {

        return noteRepository.findByUnit(unit);
    }

    // Search by subject and unit
    public List<Note> searchBySubjectAndUnit(
            Subject subject,
            String unit) {

        return noteRepository.findBySubjectAndUnit(
                subject,
                unit);
    }
}