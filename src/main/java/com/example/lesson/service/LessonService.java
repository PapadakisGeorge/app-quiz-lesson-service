package com.example.lesson.service;

import com.example.lesson.exceptions.LessonNotFoundException;
import com.example.lesson.model.Lesson;
import com.example.lesson.dto.LessonCreationRequest;
import com.example.lesson.repository.LessonRepository;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;

@Service
public record LessonService(LessonRepository lessonRepository) {

    public void createLesson(@Valid LessonCreationRequest lessonCreationRequest) {
        Lesson lesson = Lesson.builder()
                .lessonTitle(lessonCreationRequest.lessonTitle())
                .build();
        lessonRepository.save(lesson);
    }

    public List<Lesson> getLessons() {
        return lessonRepository.findAll();
    }

    public Lesson getLessonById(Integer id) throws LessonNotFoundException {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new LessonNotFoundException("Lesson with id {} was not found.", id));
    }

    public List<Lesson> getLessonsByTitle(String lessonTitle) throws LessonNotFoundException {
        List<Lesson> lessonsList = lessonRepository.findLessonBylessonTitle(lessonTitle);
        if (lessonsList.isEmpty()) {
            throw new LessonNotFoundException("Lesson with name {} was not found.", lessonTitle);
        }
        return lessonsList;
    }
}