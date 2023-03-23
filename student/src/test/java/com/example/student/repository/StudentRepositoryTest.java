package com.example.student.repository;

import com.example.student.model.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;


@DataJpaTest
class StudentRepositoryTest {


    @Autowired
    private  StudentRepository studentRepository;

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    void findStudentEmailExists() {

        //given
        Student s=new Student(
                1,
                "Shyam",
                "shyam@gamil.com",
                "ECE"
        );

        //store to test db
        studentRepository.save(s);

        //when
        Optional<Student> optional=studentRepository.findStudentByEmail(s.getEmail());

        //then
        Assertions.assertThat(optional).contains(s);
    }

    @Test
    void findStudentEmailNotExists()
    {
        Student s=new Student(
                1,
                "Shyam",
                "shyam@gamil.com",
                "ECE"
        );



        Optional<Student> optional=studentRepository.findStudentByEmail(s.getEmail());


        Assertions.assertThat(optional).isEmpty();
    }

}