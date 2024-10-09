package com.example.graphql.demo;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.graphql.demo.response.StudentResponse;
import com.example.graphql.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//Note : Application will not start if we haven't defined the function or field name incorrectly.

@Component
public class Query implements GraphQLQueryResolver {

    @Autowired
    private StudentService studentService;

    //    Note: this function name should match with the name we provide in query.graphql(s) file.
    public String query() {
        return "First query";
    }

    public String secondQuery() {
        return "SecondQuery";
    }

    public String fullName(String firstName, String lastName) {
        return firstName + " " + lastName;
    }

    public String fullNameUsingRequest(SampleRequest sampleRequest) {
        return sampleRequest.getFirstName() + " " + sampleRequest.getLastName();
    }

    //here there are 2 options to satisfy name for query
//    1. Either we can use student as it is student()              or
//    2. we can provide getter name for the same. ie. getStudent()  these both will be read fine
    public StudentResponse student(long id) {
        return new StudentResponse(studentService.findById(id));
    }


    //basic flow -> query is hit, id field gets passed on,
    // graphql class searches for the same function or its getter in this query.class file
    // if found accepts the parameter, it fetches details from repo returns back to same servic
    // then it goes and checks in GraphqlProvider impls if any if found
    // it reads the className and searches for any matching getters in the class
    // 1. if not found returns null
    // 2. if found it reads the function do the operation gets the result
    // 3. maps the field back to our query.class and then returns the result.
}
