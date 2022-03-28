package com.example.lesson.service;

import com.example.lesson.dto.LessonCreationRequest;
import com.example.lesson.exceptions.LessonNotFoundException;
import com.example.lesson.model.Lesson;
import com.example.lesson.repository.LessonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

    @Mock
    private LessonRepository lessonRepositoryTest;
    private LessonService lessonServiceTest;

    @BeforeEach
    void setup(){
        lessonServiceTest = new LessonService(lessonRepositoryTest);
    }

    @Test
    void canCreateLesson() {
        //when I try to create the lesson
        LessonCreationRequest lessonCreationRequest = new LessonCreationRequest("Mock Lesson Title");
        lessonServiceTest.createLesson(lessonCreationRequest);

        //then the lesson is created
        Lesson lesson = Lesson
                .builder()
                .lessonTitle(lessonCreationRequest.lessonTitle())
                .build();

        ArgumentCaptor<Lesson> lessonArgumentCaptor = ArgumentCaptor.forClass(Lesson.class);

        verify(lessonRepositoryTest).save(lessonArgumentCaptor.capture());

        Lesson capturedLesson = lessonArgumentCaptor.getValue();

        assertThat(capturedLesson).isEqualTo(lesson);
    }

    @Test
    void canGetAllLessons() {
        //when I get all lessons
        lessonServiceTest.getAllLessons();

        //then I verify that all the lessons where retrieved
        verify(lessonRepositoryTest).findAll();
    }

    @Test
    void canGetLessonsById() throws LessonNotFoundException {
        //given a lesson with a given title exists
        LessonCreationRequest lessonCreationRequest = new LessonCreationRequest("Mock Lesson Title");
        Lesson lesson = Lesson
                .builder()
                .lessonTitle(lessonCreationRequest.lessonTitle())
                .build();

        when(lessonRepositoryTest.findById(lesson.getId()))
                .thenReturn(Optional.of(lesson));

        //when I try to get a lesson that exists by id
        assertThat(lessonServiceTest.getLessonById(lesson.getId())).isEqualTo(lesson);
    }

    @Test
    void getLessonsByIdException() {
        //given a lesson with a given title does not exist
        LessonCreationRequest lessonCreationRequest = new LessonCreationRequest("Mock Lesson Title");
        Lesson lesson = Lesson
                .builder()
                .lessonTitle(lessonCreationRequest.lessonTitle())
                .build();

        //when I try to get a lesson that exists by id
        //then I get the correct exception
        assertThatThrownBy(() -> lessonServiceTest.getLessonById(lesson.getId()))
                .isInstanceOf(LessonNotFoundException.class)
                .hasMessageContaining("Lesson with id %d was not found.", lesson.getId());
    }

    @Test
    void canGetLessonsByTitle() throws LessonNotFoundException {
        //given a lesson with a given title exists
        LessonCreationRequest lessonCreationRequest = new LessonCreationRequest("Mock Lesson Title");
        Lesson lesson = Lesson
                .builder()
                .lessonTitle(lessonCreationRequest.lessonTitle())
                .build();

        List<Lesson> retrievedLessons = List.of(lesson);
        when(lessonRepositoryTest.findLessonByTitle(lessonCreationRequest.lessonTitle()))
                .thenReturn(retrievedLessons);

        //when I try to get a lesson that exists by id
        assertThat(lessonServiceTest.getLessonsByTitle("Mock Lesson Title").size()).isEqualTo(1);
    }

    @Test
    void getLessonsByTitleException() {
        //given a lesson with a given title does not exist
        LessonCreationRequest lessonCreationRequest = new LessonCreationRequest("Mock Lesson Title");
        Lesson lesson = Lesson
                .builder()
                .lessonTitle(lessonCreationRequest.lessonTitle())
                .build();

        List<Lesson> retrievedLessons = Collections.emptyList();
        when(lessonRepositoryTest.findLessonByTitle(lessonCreationRequest.lessonTitle()))
                .thenReturn(retrievedLessons);

        //when I try to get a lesson that exists by id
        //then I get the correct exception
        assertThatThrownBy(() -> lessonServiceTest.getLessonsByTitle("Mock Lesson Title"))
                .isInstanceOf(LessonNotFoundException.class)
                .hasMessageContaining("Lesson with name %s was not found.", lesson.getLessonTitle());
    }
}