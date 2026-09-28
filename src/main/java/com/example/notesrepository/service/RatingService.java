package com.example.notesrepository.service;

import com.example.notesrepository.entity.Note;
import com.example.notesrepository.entity.Rating;
import com.example.notesrepository.entity.Student;
import com.example.notesrepository.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public Rating addRating(Rating rating) {

        if (rating.getValue() == null ||
                rating.getValue() < 1 ||
                rating.getValue() > 5) {

            throw new RuntimeException(
                    "Rating must be between 1 and 5");
        }

        Note note = rating.getNote();
        Student student = rating.getStudent();

        boolean alreadyRated =
                ratingRepository.existsByNoteAndStudent(note, student);

        if (alreadyRated) {
            throw new RuntimeException(
                    "This student has already rated this note");
        }

        return ratingRepository.save(rating);
    }

    public List<Rating> getRatingsByNote(Note note) {
        return ratingRepository.findByNote(note);
    }

    public double getAverageRating(Note note) {

        List<Rating> ratings = ratingRepository.findByNote(note);

        if (ratings.isEmpty()) {
            return 0.0;
        }

        int total = 0;

        for (Rating rating : ratings) {
            total += rating.getValue();
        }

        return (double) total / ratings.size();
    }
}