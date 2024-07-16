package com.itciwen.service;

import com.itciwen.dao.Student;
import com.itciwen.dao.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student getStudentById(long id) {
        return studentRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
