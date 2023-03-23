package com.example.student.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StudentTest {

    @Test
    void testCanEqual() {
        assertFalse((new Student()).canEqual("Other"));
    }


    @Test
    void testCanEqual2() {
        Student student = new Student();
        assertTrue(student.canEqual(new Student()));
    }


    @Test
    void testConstructor() {
        Student actualStudent = new Student();
        actualStudent.setDepartment("Department");
        actualStudent.setEmail("jane.doe@example.org");
        actualStudent.setId(1);
        actualStudent.setName("Name");
        String actualToStringResult = actualStudent.toString();
        assertEquals("Department", actualStudent.getDepartment());
        assertEquals("jane.doe@example.org", actualStudent.getEmail());
        assertEquals(1, actualStudent.getId().intValue());
        assertEquals("Name", actualStudent.getName());
        assertEquals("Student(id=1, name=Name, email=jane.doe@example.org, department=Department)", actualToStringResult);
    }




    @Test
    void testEquals() {
        assertNotEquals(new Student(), null);
        assertNotEquals(new Student(), "Different type to Student");
    }

    @Test
    void testEquals2() {
        Student student = new Student();
        assertEquals(student, student);
        int expectedHashCodeResult = student.hashCode();
        assertEquals(expectedHashCodeResult, student.hashCode());
    }


    @Test
    void testEquals3() {
        Student student = new Student();
        Student student1 = new Student();
        assertEquals(student, student1);
        int expectedHashCodeResult = student.hashCode();
        assertEquals(expectedHashCodeResult, student1.hashCode());
    }


    @Test
    void testEquals4() {
        Student student = new Student(1, "Name", "jane.doe@example.org", "Department");
        assertNotEquals(student, new Student());
    }


    @Test
    void testEquals5() {
        Student student = new Student();
        assertNotEquals(student, new Student(1, "Name", "jane.doe@example.org", "Department"));
    }


    @Test
    void testEquals6() {
        Student student = new Student();
        student.setName("Name");
        assertNotEquals(student, new Student());
    }


    @Test
    void testEquals7() {
        Student student = new Student();
        student.setEmail("jane.doe@example.org");
        assertNotEquals(student, new Student());
    }


    @Test
    void testEquals8() {
        Student student = new Student();
        student.setDepartment("Department");
        assertNotEquals(student, new Student());
    }


    @Test
    void testEquals9() {
        Student student = new Student(1, "Name", "jane.doe@example.org", "Department");
        Student student1 = new Student(1, "Name", "jane.doe@example.org", "Department");

        assertEquals(student, student1);
        int expectedHashCodeResult = student.hashCode();
        assertEquals(expectedHashCodeResult, student1.hashCode());
    }


    @Test
    void testEquals10() {
        Student student = new Student();

        Student student1 = new Student();
        student1.setName("Name");
        assertNotEquals(student, student1);
    }


    @Test
    void testEquals11() {
        Student student = new Student();

        Student student1 = new Student();
        student1.setEmail("jane.doe@example.org");
        assertNotEquals(student, student1);
    }

    @Test
    void testEquals12() {
        Student student = new Student();

        Student student1 = new Student();
        student1.setDepartment("Department");
        assertNotEquals(student, student1);
    }
}

