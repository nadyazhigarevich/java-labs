package com.zhigarevich.student;

import com.zhigarevich.student.entity.Faculty;
import com.zhigarevich.student.entity.Student;
import com.zhigarevich.student.factory.StudentFactory;
import com.zhigarevich.student.service.StudentService;
import com.zhigarevich.student.service.StudentServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class StudentServiceImplTest {
    private static final int STUDENT_COUNT = 30;
    private List<Student> students;
    private StudentService studentService;

    @Before
    public void setUp() {
        studentService = new StudentServiceImpl();
        students = StudentFactory.createRandomStudents(STUDENT_COUNT);
        assertNotNull("Students list should not be null", students);
        assertEquals("Expected number of students must match", STUDENT_COUNT, students.size());
    }

    @After
    public void tearDown() {
        students = null;
        studentService = null;
    }

    @Test
    public void testGetStudentsByFaculty() {
        Faculty facultyToTest = students.get(0).getFaculty();
        List<Student> actualStudents = studentService.findStudentsByFaculty(students, facultyToTest);

        assertNotNull(actualStudents);
        assertFalse(actualStudents.isEmpty());
        for (Student student : actualStudents) {
            assertEquals(facultyToTest, student.getFaculty());
        }
    }

    @Test
    public void testGetStudentsByFacultyAndCourse() {
        Faculty facultyToTest = students.get(0).getFaculty();
        int courseToTest = students.get(0).getCourse();
        List<Student> actualStudents = studentService.findStudentsByFacultyAndCourse(students, facultyToTest, courseToTest);

        assertNotNull(actualStudents);
        assertFalse(actualStudents.isEmpty());
        for (Student student : actualStudents) {
            assertEquals(facultyToTest, student.getFaculty());
            assertEquals(courseToTest, student.getCourse());
        }
    }

    @Test
    public void testGetStudentsBornAfter() {
        int yearToTest = 2000;
        List<Student> actualStudents = studentService.findStudentsBornAfter(students, yearToTest);

        assertNotNull(actualStudents);
        for (Student student : actualStudents) {
            assertTrue(student.getDateOfBirth().isAfter(LocalDate.of(yearToTest, 1, 1)));
        }
    }

    @Test
    public void testGetStudentsByFacultyAndCourseNoResults() {
        Faculty facultyToTest = students.get(0).getFaculty();
        List<Student> actualStudents = studentService.findStudentsByFacultyAndCourse(students, facultyToTest, 99);

        assertNotNull(actualStudents);
        assertTrue(actualStudents.isEmpty());
    }

    @Test
    public void testGetStudentsBornAfterEdgeCase() {
        int currentYear = LocalDate.now().getYear();
        List<Student> actualStudents = studentService.findStudentsBornAfter(students, currentYear);

        assertNotNull(actualStudents);
        assertTrue(actualStudents.isEmpty());
    }

    @Test
    public void testGetAllStudents() {
        assertNotNull(students);
        assertFalse(students.isEmpty());
        assertEquals(STUDENT_COUNT, students.size());
    }
}