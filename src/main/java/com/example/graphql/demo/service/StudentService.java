package com.example.graphql.demo.service;

import com.example.graphql.demo.entity.Address;
import com.example.graphql.demo.entity.Student;
import com.example.graphql.demo.entity.Subject;
import com.example.graphql.demo.repository.AddressRepository;
import com.example.graphql.demo.repository.StudentRepository;
import com.example.graphql.demo.repository.SubjectRepository;
import com.example.graphql.demo.request.CreateSubjectRequest;
import com.example.graphql.demo.request.StudentRequestInput;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Student findById(Long id){
        return studentRepository.findById(id).get();
    }


    public Student createStudent(StudentRequestInput studentRequestInput){
        //constructor is created in to extract firstName lastName and email
        Student student = new Student(studentRequestInput);

        //accept address details and save it in db to generate id
        Address address = new Address();
        address.setStreet(studentRequestInput.getStreet());
        address.setCity(studentRequestInput.getCity());

        address = addressRepository.save(address);

        student.setAddress(address);
        student = studentRepository.save(student);

        List<Subject> subjectList = new ArrayList<>();
        if(studentRequestInput.getSubjectsLearning()!=null){
            for(CreateSubjectRequest subjectRequest:studentRequestInput.getSubjectsLearning()){
                Subject subject = new Subject();
                subject.setSubjectName(subjectRequest.getSubjectName());
                subject.setMarksObtained(subjectRequest.getMarksObtained());
                subject.setStudent(student);
                subjectList.add(subject);
            }
        }
        //save all these objects in subject table and then add it in student
        subjectRepository.saveAll(subjectList);

        student.setLearningSubjects(subjectList);
        return student;
    }
}
