package com.example.student.service;

import com.example.student.exception.DataBaseIsEmptyException;
import com.example.student.exception.EmailAlreadyTakenException;
import com.example.student.exception.StudentNotFoundException;
import com.example.student.model.Student;
import com.example.student.repository.StudentRepository;



import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.assertj.core.api.Assertions;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;


    private StudentService studentService;



    @BeforeEach
    void setUp() {

        studentService=new StudentService(studentRepository);

    }

    @Test

    void canAddStudent() throws EmailAlreadyTakenException {

        //given
        Student s=new Student(
                1,
                "Shyam",
                "shyam@gmail.com",
                "ECE"
        );

        studentService.addStudent(s);

        
        ArgumentCaptor<Student> studentArgumentCaptor=ArgumentCaptor.forClass(Student.class);

        Mockito.verify(studentRepository).save(studentArgumentCaptor.capture());

        Assertions.assertThat(studentArgumentCaptor.getValue()).isSameAs(s);




    }


    @Test
    void shouldThrowEmailException()
    {
        Student s=new Student(
                1,
                "Shyam",
                "shyam@gmail.com",
                "ECE"
        );




        given(studentRepository.findStudentByEmail(s.getEmail())).willReturn(Optional.of(s));

        Assertions.assertThatThrownBy(()->studentService.addStudent(s))
                .isInstanceOf(EmailAlreadyTakenException.class)
                .hasMessage("Email "+s.getEmail()+" is already taken");

        verify(studentRepository,never()).save(any());

    }


    @Test
    void getAllStudent() {

        //when
        studentService.getAllStudent();
        //then
        Mockito.verify(studentRepository).findAll();

    }

    @Test

    void getById() throws StudentNotFoundException {
        Student s=new Student(
                1,
                "Shyam",
                "shyam@gmail.com",
                "ECE"
        );


//        when(studentRepository.findById(1)).thenReturn(Optional.of(s));
        given(studentRepository.findById(1)).willReturn(Optional.of(s));
        Optional<Student> m=studentService.gettingById(1);


        Assertions.assertThat(Optional.of(s)).isEqualTo(m);

       //then
        Mockito.verify(studentRepository).findById(1);



    }

    @Test
    void shouldThrowStudentNotFoundException()
    {
        Student s=new Student(
                1,
                "Shyam",
                "shyam@gmail.com",
                "ECE"
        );



//        when(studentRepository.findById(1)).thenReturn(Optional.empty());
        given(studentRepository.findById(1)).willReturn(Optional.empty());



        Assertions.assertThatThrownBy(()->studentService.gettingById(1))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessage("Student with "+s.getId()+" is not found");

//        verify(studentRepository,never()).findById(1);

    }

    @Test

    void deletingByIdTest() throws StudentNotFoundException {

        Student s=new Student(
                1,
                "Shyam",
                "shyam@gmail.com",
                "ECE"
        );


//        when(studentRepository.findById(1)).thenReturn(Optional.of(s));
        given(studentRepository.findById(1)).willReturn(Optional.of(s));

        Optional<Student> m=studentService.gettingById(1);


        Assertions.assertThat(Optional.of(s)).isEqualTo(m);

        studentService.deletingById(1);

        verify(studentRepository).deleteById(any());
    }

    @Test
    void shouldNotDeleteThrowsException(){

        Student s=new Student(
                1,
                "Shyam",
                "shyam@gmail.com",
                "ECE"
        );



//        when(studentRepository.findById(1)).thenReturn(Optional.empty());
        given(studentRepository.findById(1)).willReturn(Optional.empty());



        Assertions.assertThatThrownBy(()->studentService.deletingById(1))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessage("Student with "+s.getId()+" is not found");

        verify(studentRepository,never()).deleteById(any());

    }


    @Test
    void updatingStudentById() throws StudentNotFoundException, EmailAlreadyTakenException {

        Student s=new Student(
                1,
                "Shyam",
                "shyam@gmail.com",
                "ECE"
        );
        Student s1=new Student(
                1,
                "Sundar",
                "shyam@gmail.com",
                "ECE"
        );

        String name="Sundar";

        given(studentRepository.findById(1)).willReturn(Optional.of(s));


        Student student=studentService.updatingById(s.getId(),name,s.getEmail(),s.getDepartment());

        System.out.println(student);
        Assertions.assertThat(s1).isEqualTo(student);
    }

    @Test
    void updateStudentWillThrowStudentException() {

//       when(studentRepository.findById(1)).thenReturn(Optional.empty());
        given(studentRepository.findById(1)).willReturn(Optional.empty());



        Assertions.assertThatThrownBy(()->studentService.updatingById(1,"Shyam","shyam@gmail.com","ECE"))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessage("Student with id "+1+" is not found");
    }

    @Test

    void updateStudentWillThrowEmailException() {
        Student s=new Student(
                1,
                "Shyam",
                "shyam@gmail.com",
                "ECE"
        );

        given(studentRepository.findById(1)).willReturn(Optional.of(s));


        given(studentRepository.findStudentByEmail(s.getEmail())).willReturn(Optional.of(s));

        assertThatThrownBy(() ->studentService.updatingById(1,"Sundar","shyam@gmail.com","MECH"))
                .isInstanceOf(EmailAlreadyTakenException.class)
                .hasMessage("Email already taken");


//        verify(studentRepository,never()).save(any());
    }
    @Test
    void deletingAllStudent() throws DataBaseIsEmptyException {

        List<Student> m=new ArrayList<>();
        m.add(new Student(1,"Shyam","shyam@gmail.com","ECE"));
        m.add(new Student(2,"Shyam1","shyam1@gmail.com","MECH"));
        given(studentRepository.findAll()).willReturn(m);

        studentService.deletingAll();
        verify(studentRepository).deleteAll();
    }

    @Test
    void deletingAllWillThrowDataEmptyException()
    {
        List<Student> l=new ArrayList<>();
        given(studentRepository.findAll()).willReturn(l);

        Assertions.assertThatThrownBy(()->studentService.deletingAll())
                .isInstanceOf(DataBaseIsEmptyException.class)
                .hasMessage("Data Base is Empty!!!");

    }
}