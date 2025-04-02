package com.zhigarevich.student;

import com.zhigarevich.student.entity.Address;
import com.zhigarevich.student.entity.Faculty;
import com.zhigarevich.student.entity.Student;
import com.zhigarevich.student.validator.StudentValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Student Validator Tests")
class StudentValidatorTest {

    private static Stream<Arguments> provideTestStudents() {
        return Stream.of(
                Arguments.of(createStudent("John", "Doe", "Ivanovich", 2000, "+1234567890", Faculty.FPMI, 2, 1), true),
                Arguments.of(createStudent("", "Doe", "Ivanovich", 2000, "+1234567890", Faculty.FPMI, 2, 1), false), Arguments.of(createStudent("John", "", "Ivanovich", 2000, "+1234567890", Faculty.FPMI, 2, 1), false), Arguments.of(createStudent("John", "Doe", "", 2000, "+1234567890", Faculty.FPMI, 2, 1), false),
                Arguments.of(createStudent("John", "Doe", "Ivanovich", 2026, "+1234567890", Faculty.FPMI, 2, 1), false),
                Arguments.of(createStudent("John", "Doe", "Ivanovich", 2000, "1234567890", Faculty.FPMI, 2, 1), false),
                Arguments.of(createStudent("John", "Doe", "Ivanovich", 2000, "+1234567890", Faculty.FPMI, 0, 1), false),
                Arguments.of(createStudent("John", "Doe", "Ivanovich", 2000, "+1234567890", Faculty.FPMI, 2, 11), false),
                Arguments.of(createStudent("J0hn", "Doe", "Ivanovich", 2000, "+1234567890", Faculty.FPMI, 2, 1), false),
                Arguments.of(createStudent("John", "Doe", "Ivanovich", 2000, "+1234567890", null, 2, 1), false));
    }

    @ParameterizedTest(name = "{index} => firstName={0}, valid={1}")
    @MethodSource("provideTestStudents")
    @DisplayName("Validate student")
    void testStudentValidation(Student student, boolean expectedValid) {
        assertEquals(expectedValid, StudentValidator.isValidStudent(student));
    }

    private static Student createStudent(String firstName, String lastName, String patronymic, int birthYear, String phone, Faculty faculty, int course, int group) {
        return new Student(1, firstName, lastName, patronymic, LocalDate.of(birthYear, 1, 1), new Address("Country", "City", "Street 1"), phone, faculty, course, group);
    }
}