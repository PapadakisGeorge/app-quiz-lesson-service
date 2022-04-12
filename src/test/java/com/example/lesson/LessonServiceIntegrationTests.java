package com.example.lesson;
import com.example.lesson.dto.LessonCreationRequest;
import com.example.lesson.model.Lesson;
import com.example.lesson.service.LessonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
public class LessonServiceIntegrationTests {

    @Autowired
    private LessonService lessonService;

    // Use the postgres version you are using on production.
    // If you remove static then each test method will use a new container.
    @Container
    private static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest");

    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", postgreSQLContainer::getDriverClassName);
    }

    @Test
    void addAndGetLessons() {

        // When I get all names then there is nothing there!
        assertThat(lessonService.getAllLessons()).hasSize(0);

        //When I add a lesson
        LessonCreationRequest lessonCreationRequest = new LessonCreationRequest("Mock Lesson Title");
        lessonService.createLesson(lessonCreationRequest);

        // Then there should be only one lesson
        assertThat(lessonService.getAllLessons()).hasSize(1);

        // And the lesson title should be the correct one
        assertThat(lessonService.getAllLessons().get(0).getLessonTitle()).isEqualTo("Mock Lesson Title");
    }
}
