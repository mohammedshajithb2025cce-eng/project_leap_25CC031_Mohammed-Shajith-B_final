package com.example.notesrepository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Unit cannot be empty")
    @Column(nullable = false)
    private String unit;

    private String fileName;

    private String fileType;

    private String filePath;

    @NotNull(message = "Uploader is required")
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student uploader;

    @NotNull(message = "Subject is required")
    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    public Note() {
    }

    public Note(String title, String unit, String fileName,
                String fileType, String filePath,
                Student uploader, Subject subject) {

        this.title = title;
        this.unit = unit;
        this.fileName = fileName;
        this.fileType = fileType;
        this.filePath = filePath;
        this.uploader = uploader;
        this.subject = subject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Student getUploader() {
        return uploader;
    }

    public void setUploader(Student uploader) {
        this.uploader = uploader;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}