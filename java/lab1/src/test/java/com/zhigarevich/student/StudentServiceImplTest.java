package com.zhigarevich.student;

import com.zhigarevich.student.entity.Faculty;
import com.zhigarevich.student.entity.Student;
import com.zhigarevich.student.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {

    private StudentServiceImpl studentService;
    private List<Student> students;

    @BeforeEach
    void setUp() {
        studentService = new StudentServiceImpl();

        Student student1 = new Student(1, "Doe", "John", "Smith", LocalDate.of(2002, 5, 15), null, "1234567890", Faculty.MMF, 1, 101);
        Student student2 = new Student(2, "Smith", "Jane", "Doe", LocalDate.of(2003, 8, 20), null, "0987654321", Faculty.MMF, 2, 102);
        Student student3 = new Student(3, "Johnson", "Alice", "Smith", LocalDate.of(2001, 3, 10), null, "5678901234", Faculty.FPMI, 1, 201);

        students = Arrays.asList(student1, student2, student3);
    }

    @Test
    void testFindStudentsByFaculty() {
        List<Student> expected = Arrays.asList(students.get(0), students.get(1));
        List<Student> actual = studentService.findStudentsByFaculty(students, Faculty.MMF);

        assertEquals(expected.size(), actual.size());
        assertTrue(actual.stream().allMatch(student -> student.getFaculty() == Faculty.MMF));
    }

    @Test
    void testFindStudentsByFacultyAndCourse() {
        List<Student> expected = Collections.singletonList(students.get(0));
        List<Student> actual = studentService.findStudentsByFacultyAndCourse(students, Faculty.MMF, 1);

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getFirstName(), actual.get(0).getFirstName());
        assertEquals(expected.get(0).getLastName(), actual.get(0).getLastName());
        assertEquals(expected.get(0).getFaculty(), actual.get(0).getFaculty());
        assertEquals(expected.get(0).getCourse(), actual.get(0).getCourse());
    }

    @Test
    void testFindStudentsBornAfter() {
        List<Student> expected = Arrays.asList(students.get(0), students.get(1));
        List<Student> actual = studentService.findStudentsBornAfter(students, 2002);

        assertEquals(expected.size(), actual.size());
        assertTrue(actual.stream().allMatch(student -> student.getDateOfBirth().isAfter(LocalDate.of(2002, 1, 1))));
    }

    @Test
    void testFindStudentsByFaculty_NoStudents() {
        List<Student> expected = Collections.emptyList();
        List<Student> actual = studentService.findStudentsByFaculty(students, Faculty.FPO);

        assertEquals(expected, actual);
    }

    @Test
    void testFindStudentsByFacultyAndCourse_NoStudents() {
        List<Student> expected = Collections.emptyList();
        List<Student> actual = studentService.findStudentsByFacultyAndCourse(students, Faculty.MMF, 3);

        assertEquals(expected, actual);
    }

    @Test
    void testFindStudentsBornAfter_NoStudents() {
        List<Student> expected = Collections.emptyList();
        List<Student> actual = studentService.findStudentsBornAfter(students, 2005);

        assertEquals(expected, actual);
    }

    @Test
    void testFindStudentsByFaculty_EmptyList() {
        List<Student> expected = Collections.emptyList();
        List<Student> actual = studentService.findStudentsByFaculty(Collections.emptyList(), Faculty.MMF);

        assertEquals(expected, actual);
    }

    @Test
    void testFindStudentsByFacultyAndCourse_EmptyList() {
        List<Student> expected = Collections.emptyList();
        List<Student> actual = studentService.findStudentsByFacultyAndCourse(Collections.emptyList(), Faculty.MMF, 1);

        assertEquals(expected, actual);
    }
}