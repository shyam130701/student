package com.example.student.controller;


import com.example.student.model.Student;
import com.example.student.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc(addFilters = false)

class StudentControllerTest {

    @MockBean
    StudentService studentService;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc  mockMvc;




    @Test
    void addingStudentController() throws Exception {
        Student student=new Student(1,"Shyam","shyam@gmail.com","MECH");

       when(studentService.addStudent(student)).thenReturn(student);

       mockMvc.perform(post("/add")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(student))

       ).andExpect(status().isOk());

    }

    @Test

    void fetchAllStudentController() throws Exception {
        List<Student> students=new ArrayList<>();
        students.add(new Student(1,"Shyam","shyam@gmail.com","MECH"));
        students.add(new Student(2,"Sundar","sundar@gmail.com","ECE"));

        when(studentService.getAllStudent()).thenReturn(students);

         mockMvc.perform(get("/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Shyam"));

    }

    @Test

    void gettingByIdController() throws Exception {
        Student student=new Student(1,"Shyam","shyam@gmail.com","MECH");

        given(studentService.gettingById(1)).willReturn(Optional.of(student));

          mockMvc.perform(get("/getById/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Shyam"));

         
    }

    @Test

    void deletingByIdController() throws Exception {
        given(studentService.deletingById(1)).willReturn("Student deleted with id "+1);

        mockMvc.perform(delete("/deleteById/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MvcResult::getResponse);



    }

    @Test
    void deletingAllStudentController() throws Exception {

        when(studentService.deletingAll()).thenReturn("Deleted All");

        mockMvc.perform(delete("/deleteAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MvcResult::getResponse);



    }

    @Test
    void updateStudentController() throws Exception{

        Student student1=new Student(1,"Sundar","sundar@gmail.com","ECE");
        when(studentService.updatingById(1,"Sundar","sundar@gmail.com","ECE")).thenReturn(student1);

        mockMvc.perform(put("/update/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MvcResult::getResponse);



    }

}