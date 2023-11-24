package com.example.student.controller;

import com.example.student.exception.DataBaseIsEmptyException;
import com.example.student.exception.EmailAlreadyTakenException;
import com.example.student.exception.StudentNotFoundException;
import com.example.student.model.Student;
import com.example.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {


    @Autowired
    private StudentService studentService;



    @PostMapping("/add")
    public Student addingStudent(@RequestBody Student student) throws EmailAlreadyTakenException {
        return  studentService.addStudent(student);
    }
    @GetMapping("/all")
    public List<Student> fetchAllStudent()
    {
        return studentService.getAllStudent();
    }

    @GetMapping("getById/{id}")
    public Optional<Student> gettingById(@PathVariable int id) throws StudentNotFoundException {
       return studentService.gettingById(id);
    }



    @PutMapping("/update/{id}")
    public Student updateStudentById(@PathVariable int id,@RequestParam(required = false) String name,
                                     @RequestParam(required = false) String email,
                                     @RequestParam(required = false) String dept) throws StudentNotFoundException, EmailAlreadyTakenException
    {
        return studentService.updatingById(id,name,email,dept);
    }

    @DeleteMapping("deleteById/{id}")
    public String deletingById(@PathVariable int id) throws StudentNotFoundException {
        return studentService.deletingById(id);
    }

    @DeleteMapping("deleteAll")
    public String deletingAllStudent() throws DataBaseIsEmptyException {
        return studentService.deletingAll();
    }

}
