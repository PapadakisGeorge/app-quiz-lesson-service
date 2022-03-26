package com.example.lesson.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.GenerationType;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Lesson {

    @Id
    @SequenceGenerator(
            name = "lesson_id_sequence",
            sequenceName = "lesson_id_sequence"
    )
    @GeneratedValue(
            strategy =  GenerationType.SEQUENCE,
            generator = "lesson_id_sequence"
    )
    private Integer id;
    private String lessonName;
}
