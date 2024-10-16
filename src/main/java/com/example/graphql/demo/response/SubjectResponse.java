package com.example.graphql.demo.response;

import com.example.graphql.demo.entity.Subject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectResponse {

    private Long id;

    private String subjectName;

    private Double marksObtained;

    public SubjectResponse (Subject subject) {
        this.id = subject.getId();
        this.subjectName = subject.getSubjectName();
        this.marksObtained = subject.getMarksObtained();
    }
}