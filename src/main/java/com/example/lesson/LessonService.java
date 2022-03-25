package com.example.lesson;

import org.springframework.stereotype.Service;


@Service
public record LessonService(LessonRepository lessonRepository) {
    public void createLesson(LessonCreationRequest lessonCreationRequest) {
        Lesson lesson = Lesson.builder()
                .lessonName(lessonCreationRequest.lessonName())
                .build();
        lessonRepository.save(lesson);
    }

}
