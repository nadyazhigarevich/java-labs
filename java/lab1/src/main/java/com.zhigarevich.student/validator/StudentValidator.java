package com.zhigarevich.student.validator;

import java.time.LocalDate;
import java.util.regex.Pattern;

import com.zhigarevich.student.entity.Faculty;
import com.zhigarevich.student.entity.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StudentValidator {
    private static final Logger logger = LogManager.getLogger(StudentValidator.class);

    private static final String PHONE_NUMBER_REGEX = "\\+\\d{1,3}\\d{9}";
    private static final String NAME_REGEX = "[A-Za-z]+";

    private static final String INVALID_FIRST_NAME = "Invalid First Name: ";
    private static final String INVALID_LAST_NAME = "Invalid Last Name: ";
    private static final String INVALID_PATRONYMIC = "Invalid Patronymic: ";
    private static final String INVALID_DATE_OF_BIRTH = "Invalid Date of Birth: ";
    private static final String INVALID_PHONE_NUMBER = "Invalid Phone Number: ";
    private static final String INVALID_FACULTY = "Faculty cannot be null";
    private static final String INVALID_COURSE = "Course must be between 1 and 5: ";
    private static final String INVALID_GROUP = "Group must be between 1 and 10: ";

    private static final int MIN_COURSE = 1;
    private static final int MAX_COURSE = 5;
    private static final int MIN_GROUP = 1;
    private static final int MAX_GROUP = 10;

    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);
    private static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);

    public static boolean isValidStudent(Student student) {
        return validateName(student.getFirstName(), INVALID_FIRST_NAME)
                && validateName(student.getLastName(), INVALID_LAST_NAME)
                && validatePatronymic(student.getPatronymic())
                && validateDateOfBirth(student.getDateOfBirth())
                && validatePhoneNumber(student.getPhoneNumber())
                && validateFaculty(student.getFaculty())
                && validateCourse(student.getCourse())
                && validateGroup(student.getGroup());
    }

    private static boolean validateName(String name, String errorMessage) {
        if (name == null || name.trim().isEmpty() || !NAME_PATTERN.matcher(name).matches()) {
            logger.warn(errorMessage + name);
            return false;
        }
        return true;
    }

    private static boolean validatePatronymic(String patronymic) {
        return validateName(patronymic, INVALID_PATRONYMIC);
    }

    private static boolean validateDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth == null || dateOfBirth.isAfter(LocalDate.now())) {
            logger.warn(INVALID_DATE_OF_BIRTH + (dateOfBirth != null ? dateOfBirth : "null"));
            return false;
        }
        return true;
    }

    private static boolean validatePhoneNumber(String phoneNumber) {
        if (!PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
            logger.warn(INVALID_PHONE_NUMBER + phoneNumber);
            return false;
        }
        return true;
    }

    private static boolean validateFaculty(Faculty faculty) {
        if (faculty == null) {
            logger.warn(INVALID_FACULTY);
            return false;
        }
        return true;
    }

    private static boolean validateCourse(int course) {
        if (course < MIN_COURSE || course > MAX_COURSE) {
            logger.warn(INVALID_COURSE + course);
            return false;
        }
        return true;
    }

    private static boolean validateGroup(int group) {
        if (group < MIN_GROUP || group > MAX_GROUP) {
            logger.warn(INVALID_GROUP + group);
            return false;
        }
        return true;
    }
}