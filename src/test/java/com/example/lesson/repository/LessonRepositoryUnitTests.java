package com.example.lesson.repository;

import com.example.lesson.dto.LessonCreationRequest;
import com.example.lesson.model.Lesson;
import com.example.lesson.service.LessonService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LessonRepositoryUnitTests {

    @Autowired
    private LessonRepository lessonRepositoryTest;

    @AfterEach
    void tearDown(){
        lessonRepositoryTest.deleteAll();
    }

    @Test
    @DisplayName("Should be able to add a lesson and check that it exists")
    void itShouldCheckFindLessonsByTitleWhenLessonExists(){
        // given I create a lessons
        LessonService lessonService = new LessonService(lessonRepositoryTest);
        LessonCreationRequest lessonCreationRequest = new LessonCreationRequest("Mock Lesson Title");
        lessonService.createLesson(lessonCreationRequest);

        // when I retrieve the lessons with the given name
        List<Lesson> retrievedLessons = lessonRepositoryTest.findLessonByTitle("Mock Lesson Title");

        // then I should retrieve 1 lesson
        assertThat(retrievedLessons.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Should be able to add 2 lessons with the same name and get both of them")
    void itShouldCheckFindLessonsByTitleWhenMultipleLessonsExist(){
        // given I create 2 lessons with the same name
        LessonService lessonService = new LessonService(lessonRepositoryTest);
        LessonCreationRequest lessonCreationRequest1 = new LessonCreationRequest("Mock Lesson Title");
        lessonService.createLesson(lessonCreationRequest1);
        LessonCreationRequest lessonCreationRequest2 = new LessonCreationRequest("Mock Lesson Title");
        lessonService.createLesson(lessonCreationRequest2);

        // when I retrieve the lessons with the given name
        List<Lesson> retrievedLessons = lessonRepositoryTest.findLessonByTitle("Mock Lesson Title");

        // then I should retrieve 2 lessons
        assertThat(retrievedLessons.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Should get an empty list when trying to get a non existing lesson by title")
    void itShouldCheckFindLessonsByTitleWhenLessonDoesNotExist(){
        // given I create a lesson
        LessonService lessonService = new LessonService(lessonRepositoryTest);
        LessonCreationRequest lessonCreationRequest = new LessonCreationRequest("Mock Lesson Title");
        lessonService.createLesson(lessonCreationRequest);

        // when I retrieve the lessons with a different name
        List<Lesson> retrievedLessons = lessonRepositoryTest.findLessonByTitle("Mock Lesson Title Does Not Exist");

        // then I should retrieve 0 lessons
        assertThat(retrievedLessons).isEmpty();
    }
}