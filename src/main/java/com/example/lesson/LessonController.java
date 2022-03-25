package com.example.lesson;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/lessons")
public record LessonController(LessonService lessonService) {

    @PostMapping
    public void createLesson(@RequestBody LessonCreationRequest lessonCreationRequest) {
        log.info("new lesson was inserted {}", lessonCreationRequest);
        lessonService.createLesson(lessonCreationRequest);
    }
}
