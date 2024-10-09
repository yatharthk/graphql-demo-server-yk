package com.example.graphql.demo;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.graphql.demo.request.StudentRequestInput;
import com.example.graphql.demo.response.StudentResponse;
import com.example.graphql.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


//Note mutation type gets @Service annotation whereas Query got @Component annotation
@Service
public class Mutation implements GraphQLMutationResolver {

    @Autowired
    private StudentService studentService;

    //here it will accept the input and service returns a student object
//    which can be converted to StudentResponse object and can be responded back.
    public StudentResponse createStudent(StudentRequestInput studentRequestInput){
       return new StudentResponse(studentService.createStudent(studentRequestInput));
    }
}
