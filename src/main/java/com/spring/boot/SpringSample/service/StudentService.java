package com.spring.boot.SpringSample.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.SpringSample.domain.request.CreateStudentRequest;
import com.spring.boot.SpringSample.domain.response.StudentResponse;
import com.spring.boot.SpringSample.model.Student;
import com.spring.boot.SpringSample.repository.BaseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudentService {

    private final BaseRepository repo;
    private final ModelMapper mapper;
    private final ObjectMapper objectMapper;

    public StudentResponse createStudent(CreateStudentRequest request) {
        Student Student = mapper.map(request, Student.class);
        Student = repo.save(Student);
        return mapper.map(Student, StudentResponse.class);
    }
}
