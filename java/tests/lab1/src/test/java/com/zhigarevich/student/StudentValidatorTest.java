package com.zhigarevich.student;

import com.zhigarevich.student.entity.Address;
import com.zhigarevich.student.entity.Faculty;
import com.zhigarevich.student.entity.Student;
import com.zhigarevich.student.validator.StudentValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.assertEquals;

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
                {createValidStudent(), true},
                {createInvalidStudentWithEmptyName(), false},
                {createInvalidStudentWithEmptyLastName(), false},
                {createInvalidStudentWithEmptyPatronymic(), false},
                {createInvalidStudentWithFutureDOB(), false},
                {createInvalidStudentWithFutureDOBDifferentYear(), false},
                {createInvalidStudentWithInvalidPhone(), false},
                {createInvalidStudentWithInvalidCourseNumber(), false},
                {createInvalidStudentWithInvalidGroupNumber(), false},
                {createInvalidStudentWithSpecialCharactersInName(), false}
        });
    }

    @Test
    public void testStudentValidation() {
        boolean actual = StudentValidator.isValidStudent(student);
        assertEquals(expectedValidity, actual);
    }

    private static Student createValidStudent() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.JANUARY, 1);
        Date dob = calendar.getTime();
        Address address = new Address("Street 1", "City", "Country");
        return new Student(1, "John", "Doe", "Ivanovich", dob, address, "+1234567890", Faculty.FPMI, 2, 1);
    }

    private static Student createInvalidStudentWithEmptyName() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.JANUARY, 1);
        Date dob = calendar.getTime();
        Address address = new Address("Street 1", "City", "Country");
        return new Student(2, "", "Doe", "Ivanovich", dob, address, "+1234567890", Faculty.FPMI, 2, 1);
    }

    private static Student createInvalidStudentWithEmptyLastName() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.JANUARY, 1);
        Date dob = calendar.getTime();
        Address address = new Address("Street 1", "City", "Country");
        return new Student(3, "Alice", "", "Petrovich", dob, address, "+1234567890", Faculty.FPMI, 2, 1);
    }

    private static Student createInvalidStudentWithEmptyPatronymic() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.JANUARY, 1);
        Date dob = calendar.getTime();
        Address address = new Address("Street 1", "City", "Country");
        return new Student(4, "Bob", "Johnson", "", dob, address, "+1234567890", Faculty.FPMI, 2, 1);
    }

    private static Student createInvalidStudentWithFutureDOB() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 5);
        Date dob = calendar.getTime();
        Address address = new Address("Street 1", "City", "Country");
        return new Student(5, "Charlie", "Brown", "Sidorovich", dob, address, "+1234567890", Faculty.FPMI, 2, 1);
    }

    private static Student createInvalidStudentWithFutureDOBDifferentYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2026, Calendar.JANUARY, 1);
        Date dob = calendar.getTime();
        Address address = new Address("Street 1", "City", "Country");
        return new Student(6, "Diana", "Green", "Nikolayevich", dob, address, "+1234567890", Faculty.FPMI, 2, 1);
    }

    private static Student createInvalidStudentWithInvalidPhone() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.JANUARY, 1);
        Date dob = calendar.getTime();
        Address address = new Address("Street 1", "City", "Country");
        return new Student(7, "Eve", "White", "Dmitrievich", dob, address, "123456789", Faculty.FPMI, 2, 1);
    }

    private static Student createInvalidStudentWithInvalidCourseNumber() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.JANUARY, 1);
        Date dob = calendar.getTime();
        Address address = new Address("Street 1", "City", "Country");
        return new Student(8, "Frank", "Black", "Petrovich", dob, address, "+1234567890", Faculty.FPMI, 0, 1);
    }

    private static Student createInvalidStudentWithInvalidGroupNumber() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.JANUARY, 1);
        Date dob = calendar.getTime();
        Address address = new Address("Street 1", "City", "Country");
        return new Student(9, "Grace", "Blue", "Sidorovich", dob, address, "+1234567890", Faculty.FPMI, 2, 11);
    }

    private static Student createInvalidStudentWithSpecialCharactersInName() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.JANUARY, 1);
        Date dob = calendar.getTime();
        Address address = new Address("Street 1", "City", "Country");
        return new Student(10, "H@rry", "Potter", "Ivanovich", dob, address, "+1234567890", Faculty.FPMI, 2, 1);
    }
}