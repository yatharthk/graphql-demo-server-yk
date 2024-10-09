package com.example.graphql.demo.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.example.graphql.demo.entity.Subject;
import com.example.graphql.demo.enums.SubjectFilter;
import com.example.graphql.demo.response.StudentResponse;
import com.example.graphql.demo.response.SubjectResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


//this below line indicates that the resolver is being used to resolve the graphql response for StudentResponse.class
//For any method provided here the graphql will automatically check here
// it will read the class to which resolver is provided for (from GraphqlResolver(class mentioned here)
//check for the method and field provided from the class and returns the response from it.

//GraphQLResolver is an interface which accepts any class


@Service
public class StudentResponseResolver implements GraphQLResolver<StudentResponse> {

    //this function name mustMatch with getterName for field

//    Initially
                //public List<SubjectResponse> getLearningSubjects(StudentResponse studentResponse)
//    this line only had the response which was automatically being passed on since the response type we had already defined
    public List<SubjectResponse> getLearningSubjects(StudentResponse studentResponse) {
        List<SubjectResponse> learningSubjects = new ArrayList<>();

        if (studentResponse.getStudent().getLearningSubjects() != null) {

            //since in studentResponse class which doesn't have
            // List<Subjects> learningSubjects we need a student object inside the StudentResponse to map these fields.
            for (Subject subject : studentResponse.getStudent().getLearningSubjects()) {
                learningSubjects.add(new SubjectResponse(subject));
            }
        }
        return learningSubjects;
    }

//    ->> Now it has another filter parameter  which will accept input from user and filtering will be done based on it.
    public List<SubjectResponse> getLearningSubjects(StudentResponse studentResponse, SubjectFilter subjectFilter) {
        List<SubjectResponse> learningSubjects = new ArrayList<>();

        if (studentResponse.getStudent().getLearningSubjects() != null) {

            //since in studentResponse class which doesn't have
            // List<Subjects> learningSubjects we need a student object inside the StudentResponse to map these fields.
            for (Subject subject : studentResponse.getStudent().getLearningSubjects()) {
//                filtering is done here. if name matches then only add to list.


                //if the filer =All then regardles of particular subject add it to the list.
                //simulates report card scenario where all subject details for a student is needed.
                if(subjectFilter.name().equals("All")){
                    learningSubjects.add(new SubjectResponse(subject));
                }
                // the other cases where a particular subject is needed then this condition will populate records
                else if(subjectFilter.name().equals(subject.getSubjectName()))
                    learningSubjects.add(new SubjectResponse(subject));
            }
        }
        return learningSubjects;
    }

    public String getFullName(StudentResponse studentResponse) {
        return studentResponse.getFirstName() + " " + studentResponse.getLastName();
    }


    public List<SubjectResponse> getLearningSubjects(StudentResponse studentResponse, List<SubjectFilter> subjectFilterList) {
        List<SubjectResponse> learningSubjects = new ArrayList<>();

        if (subjectFilterList.stream().anyMatch(x -> x.name().equals("All"))) {
            studentResponse.getStudent().getLearningSubjects().forEach(y -> {
                learningSubjects.add(new SubjectResponse(y));
            });
        } else {
            subjectFilterList = subjectFilterList.stream().filter(x->!x.name().equals("All")).collect(Collectors.toList());
            subjectFilterList.forEach(y -> {
                studentResponse.getStudent().getLearningSubjects().forEach(x -> {
                    if (x.getSubjectName().equals(y.name())) {
                        learningSubjects.add(new SubjectResponse(x));
                    }
                });
            });
        }
        return learningSubjects;
    }
}
