package com.zhigarevich.student;

import com.zhigarevich.student.entity.Faculty;
import com.zhigarevich.student.entity.Student;
import com.zhigarevich.student.factory.StudentFactory;
import com.zhigarevich.student.service.StudentService;
import com.zhigarevich.student.service.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {
    private List<Student> students;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        students = StudentFactory.createRandomStudents(30);
        studentService = new StudentServiceImpl();
    }

    @Test
    void testGetStudentsByFaculty() {
        Faculty facultyToTest = students.get(0).getFaculty();
        List<Student> actual = studentService.getStudentsByFaculty(students, facultyToTest);

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
        for (Student s : actual) {
            assertEquals(facultyToTest, s.getFaculty());
        }
    }

    @Test
    void testGetStudentsByFacultyAndCourse() {
        Faculty facultyToTest = students.get(0).getFaculty();
        int courseToTest = students.get(0).getCourse();
        List<Student> actual = studentService.getStudentsByFacultyAndCourse(students, facultyToTest, courseToTest);

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
        for (Student s : actual) {
            assertEquals(facultyToTest, s.getFaculty());
            assertEquals(courseToTest, s.getCourse());
        }
    }

    @Test
    void testGetStudentsBornAfter() {
        int yearToTest = 2000;
        List<Student> actual = studentService.getStudentsBornAfter(students, yearToTest);

        assertNotNull(actual);
        for (Student s : actual) {
            assertTrue(s.getDateOfBirth().after(createDate(yearToTest, 1, 1)));
        }
    }

    @Test
    void testGetStudentsByFacultyAndCourseNoResults() {
        Faculty facultyToTest = students.get(0).getFaculty();
        List<Student> actual = studentService.getStudentsByFacultyAndCourse(students, facultyToTest, 99);

        assertNotNull(actual);
        assertTrue(actual.isEmpty(), "Expected no students for an invalid course.");
    }

    @Test
    void testGetStudentsBornAfterEdgeCase() {
        int yearToTest = Calendar.getInstance().get(Calendar.YEAR);
        List<Student> actual = studentService.getStudentsBornAfter(students, yearToTest);

        assertNotNull(actual);
        assertTrue(actual.isEmpty(), "Expected no students born this year or later.");
    }

    @Test
    void testGetAllStudents() {
        assertNotNull(students);
        assertFalse(students.isEmpty(), "Students list should not be empty.");
        assertEquals(30, students.size(), "Expected 30 students.");
    }

    private Date createDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        return calendar.getTime();
    }
}