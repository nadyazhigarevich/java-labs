package com.zhigarevich.student.validator;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhigarevich.student.entity.Faculty;
import com.zhigarevich.student.entity.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StudentValidator {
    private static final Logger logger = LogManager.getLogger(StudentValidator.class);
    private static final String PHONE_NUMBER_REGEX = "\\+\\d{1,3}\\d{9}";

    public static boolean isValidStudent(Student student) {
        validateName(student.getFirstName(), "First Name");
        validateName(student.getLastName(), "Last Name");
        validatePatronymic(student.getPatronymic());
        validateDateOfBirth(student.getDateOfBirth());
        validatePhoneNumber(student.getPhoneNumber());
        validateFaculty(student.getFaculty());
        validateCourse(student.getCourse());
        validateGroup(student.getGroup());

        return true;
    }

    private static void validateName(String name, String fieldName) {
        if (name == null || name.trim().isEmpty() || !name.matches("[A-Za-z]+")) {
            logger.error(fieldName + " is invalid: " + name);
            throw new IllegalArgumentException(fieldName + " is invalid: " + name);
        }
    }

    private static void validatePatronymic(String patronymic) {
        validateName(patronymic, "Patronymic");
    }

    private static void validateDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth == null || dateOfBirth.isAfter(LocalDate.now())) {
            logger.error("Date of Birth is invalid: " + (dateOfBirth != null ? dateOfBirth.toString() : "null"));
            throw new IllegalArgumentException("Date of Birth is invalid: " + (dateOfBirth != null ? dateOfBirth.toString() : "null"));
        }
    }

    private static void validatePhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(phoneNumber);
        if (!matcher.matches()) {
            logger.error("Phone Number is invalid: " + phoneNumber);
            throw new IllegalArgumentException("Phone Number is invalid: " + phoneNumber);
        }
    }

    private static void validateFaculty(Faculty faculty) {
        if (faculty == null) {
            logger.error("Faculty is invalid: null");
            throw new IllegalArgumentException("Faculty is invalid: null");
        }
    }

    private static void validateCourse(int course) {
        if (course < 1 || course > 5) {
            logger.error("Course is invalid: " + course);
            throw new IllegalArgumentException("Course is invalid: " + course);
        }
    }

    private static void validateGroup(int group) {
        if (group < 1 || group > 10) {
            logger.error("Group is invalid: " + group);
            throw new IllegalArgumentException("Group is invalid: " + group);
        }
    }
}