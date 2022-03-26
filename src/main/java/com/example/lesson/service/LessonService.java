package com.example.lesson.service;

import com.example.lesson.exceptions.LessonNotFoundException;
import com.example.lesson.model.Lesson;
import com.example.lesson.dto.LessonCreationRequest;
import com.example.lesson.repository.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public record LessonService(LessonRepository lessonRepository) {
    public void createLesson(LessonCreationRequest lessonCreationRequest) {
        Lesson lesson = Lesson.builder()
                .lessonName(lessonCreationRequest.lessonName())
                .build();
        lessonRepository.save(lesson);
    }

    public List<Lesson> getLessons(){
        return lessonRepository.findAll();
    }

    public Lesson getLessonById(Integer id) throws LessonNotFoundException {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new LessonNotFoundException("Lesson with id {} was not found.", id ));
    }

    public List<Lesson> getLessonsByName(String lessonName) throws LessonNotFoundException {
        List<Lesson> lessonsList = lessonRepository.findLessonByLessonName(lessonName);
        if (lessonsList.isEmpty()){
            throw new LessonNotFoundException("Lesson with name {} was not found.", lessonName );
        }
        return lessonsList;
    }
}
