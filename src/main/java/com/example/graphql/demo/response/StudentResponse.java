package com.example.graphql.demo.response;

import java.util.List;


import com.example.graphql.demo.entity.Student;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentResponse {

    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String street;

    private String city;

    private String fullName;

    //this student field is just for resolving the response do-not expose this to query file for public use
    private Student student;

    private List<SubjectResponse> learningSubjects;

    public StudentResponse (Student student) {
        this.student = student;
        this.id = student.getId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.email = student.getEmail();

        this.street = student.getAddress().getStreet();
        this.city = student.getAddress().getCity();

//  This student learning subjects were getting populated eagerly,
//  regardless of being requested or not.
//        Ideally this is not the right approach .
//        we should move this to QueryResolver class and make it to lazy load.
//        It benefits performance by reducing load of populating these fields.

//        Todo: // comment this piece and move it to QueryResolver.class
//        if (student.getLearningSubjects() != null) {
//            learningSubjects = new ArrayList<>();
//            for (Subject subject: student.getLearningSubjects()) {
//                learningSubjects.add(new SubjectResponse(subject));
//        }
    }

}
