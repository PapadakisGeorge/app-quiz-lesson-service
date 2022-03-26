package com.example.lesson.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LessonNotFoundException extends Exception {
    public LessonNotFoundException(String errorMessage, Integer id){
        super(errorMessage);
    }

    public LessonNotFoundException(String errorMessage, String lessonName){
        super(errorMessage);
    }
}
