package com.example.student.service;


import com.example.student.exception.DataBaseIsEmptyException;
import com.example.student.exception.EmailAlreadyTakenException;
import com.example.student.exception.StudentNotFoundException;
import com.example.student.model.Student;
import com.example.student.repository.StudentRepository;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;




    public Student addStudent(Student student) throws  EmailAlreadyTakenException {
        Optional<Student> optional=studentRepository.findStudentByEmail(student.getEmail());
        if(optional.isPresent())
        {
            throw new EmailAlreadyTakenException("Email "+student.getEmail()+" is already taken");
        }
        return studentRepository.save(student);

    }

    public List<Student> getAllStudent()
    {
        return studentRepository.findAll();
    }

    public Optional<Student> gettingById(int id) throws StudentNotFoundException
    {
        Optional<Student> s=studentRepository.findById(id);
        if(s.isEmpty())
        {
            throw new StudentNotFoundException("Student with "+id+" is not found");
        }
        return s;
    }


    public Student updatingById(int id, String name,String email,String dept) throws StudentNotFoundException, EmailAlreadyTakenException {
       Student student= studentRepository
                .findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student with id " + id + " is not found")
                );


       if(name!=null
               && name.length()>0)
       {
           student.setName(name);
       }

       if(email!=null && email.length()>0)
       {
           Optional<Student> studentOptional=studentRepository.findStudentByEmail(email);
           if(studentOptional.isPresent())
           {
               throw new EmailAlreadyTakenException("Email already taken");
           }
           student.setEmail(email);
       }

        if(dept!=null
                && dept.length()>0)
        {
            student.setDepartment(dept);
        }
        studentRepository.save(student);
       return student;
    }
    public String deletingById(int id) throws StudentNotFoundException {
        Optional<Student> s=studentRepository.findById(id);
        if(s.isEmpty())
        {
            throw new StudentNotFoundException("Student with "+id+" is not found");
        }
        studentRepository.deleteById(id);
        return "Student deleted with id "+id;
    }

    public String deletingAll() throws DataBaseIsEmptyException {
        List<Student> students=studentRepository.findAll();
        if(students.isEmpty())
        {
            throw new DataBaseIsEmptyException("Data Base is Empty!!!");
        }
        studentRepository.deleteAll();
        return "Deleted All";
    }


}
