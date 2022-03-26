package com.example.lesson.controller;

import com.example.lesson.dto.LessonCreationRequest;
import com.example.lesson.exceptions.LessonNotFoundException;
import com.example.lesson.model.Lesson;
import com.example.lesson.service.LessonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/v1/lessons")
public record LessonController(LessonService lessonService) {

    @PostMapping
    public void createLesson(@RequestBody LessonCreationRequest lessonCreationRequest) {
        log.info("new lesson was inserted {}", lessonCreationRequest);
        lessonService.createLesson(lessonCreationRequest);
    }

    @GetMapping("/all")
    List<Lesson> getAllLessons() {
        return lessonService.getLessons();
    }

    @GetMapping("/{id}")
    Lesson getLessonById(@PathVariable Integer id) throws LessonNotFoundException {
        return lessonService.getLessonById(id);
    }

    @GetMapping
    List<Lesson> getLessonsByName(@RequestParam(value="name") String lessonName) throws LessonNotFoundException {
        return lessonService.getLessonsByName(lessonName);
    }
}
