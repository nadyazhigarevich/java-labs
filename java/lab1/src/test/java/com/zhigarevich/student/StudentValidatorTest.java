package com.zhigarevich.student;

import com.zhigarevich.student.entity.Address;
import com.zhigarevich.student.entity.Faculty;
import com.zhigarevich.student.entity.Student;
import com.zhigarevich.student.validator.StudentValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

@RunWith(Parameterized.class)
public class StudentValidatorTest {

    private Student student;
    private boolean expectedValidity;

    public StudentValidatorTest(Student student, boolean expectedValidity) {
        this.student = student;
        this.expectedValidity = expectedValidity;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {createStudent("John", "Doe", "Ivanovich", 2000, 1, 1, "+1234567890", Faculty.FPMI, 2, 1), true},
                {createStudent("", "Doe", "Ivanovich", 2000, 1, 1, "+1234567890", Faculty.FPMI, 2, 1), false},
                {createStudent("Alice", "", "Petrovich", 2000, 1, 1, "+1234567890", Faculty.FPMI, 2, 1), false},
                {createStudent("Bob", "Johnson", "", 2000, 1, 1, "+1234567890", Faculty.FPMI, 2, 1), false},
                {createStudent("Charlie", "Brown", "Sidorovich", 2026, 1, 1, "+1234567890", Faculty.FPMI, 2, 1), false},
                {createStudent("Diana", "Green", "Nikolayevich", 2026, 1, 1, "+1234567890", Faculty.FPMI, 2, 1), false},
                {createStudent("Eve", "White", "Dmitrievich", 2000, 1, 1, "123456789", Faculty.FPMI, 2, 1), false},
                {createStudent("Frank", "Black", "Petrovich", 2000, 1, 1, "+1234567890", Faculty.FPMI, 0, 1), false},
                {createStudent("Grace", "Blue", "Sidorovich", 2000, 1, 1, "+1234567890", Faculty.FPMI, 2, 11), false},
                {createStudent("H@rry", "Potter", "Ivanovich", 2000, 1, 1, "+1234567890", Faculty.FPMI, 2, 1), false}
        });
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        student = null;
    }

    @Test
    public void testStudentValidation() {
        if (expectedValidity) {
            assertTrue(StudentValidator.isValidStudent(student));
        } else {
            try {
                StudentValidator.isValidStudent(student);
                assertFalse("Expected an exception for invalid student, but none was thrown.", true);
            } catch (IllegalArgumentException e) {
                assertTrue("Exception thrown as expected for invalid student.", true);
            }
        }
    }

    private static Student createStudent(String firstName, String lastName, String patronymic, int year, int month, int day, String phone, Faculty faculty, int course, int group) {
        LocalDate dob = LocalDate.of(year, month, day);
        return new Student(1, firstName, lastName, patronymic, dob, new Address("Street 1", "City", "Country"), phone, faculty, course, group);
    }
}