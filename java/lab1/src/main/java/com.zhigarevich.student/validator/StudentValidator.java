package com.zhigarevich.student.validator;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhigarevich.student.entity.Faculty;
import com.zhigarevich.student.entity.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StudentValidator {
    private static final Logger logger = LogManager.getLogger(StudentValidator.class);

    public static boolean isValidStudent(Student student) {
        boolean isValid = isValidName(student.getFirstName()) &&
                isValidName(student.getLastName()) &&
                isValidPatronymic(student.getPatronymic()) &&
                isValidDateOfBirth(student.getDateOfBirth()) &&
                isValidPhoneNumber(student.getPhoneNumber()) &&
                isValidFaculty(student.getFaculty()) &&
                isValidCourse(student.getCourse()) &&
                isValidGroup(student.getGroup());

        if (isValid) {
            logger.info("Student is valid: " + student);
        } else {
            logger.warn("Student is invalid: " + student);
        }
        return isValid;
    }

    private static boolean isValidName(String name) {
        boolean valid = name != null && !name.trim().isEmpty() && name.matches("[A-Za-z]+");
        logValidationResult("Name", name, valid);
        return valid;
    }

    private static boolean isValidPatronymic(String patronymic) {
        boolean valid = patronymic != null && !patronymic.trim().isEmpty() && patronymic.matches("[A-Za-z]+");
        logValidationResult("Patronymic", patronymic, valid);
        return valid;
    }

    private static boolean isValidDateOfBirth(Date dateOfBirth) {
        boolean valid = dateOfBirth != null && dateOfBirth.before(new Date());
        logValidationResult("Date of Birth", dateOfBirth != null ? dateOfBirth.toString() : "null", valid);
        return valid;
    }

    private static boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "\\+\\d{1,3}\\d{9}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        boolean valid = matcher.matches();
        logValidationResult("Phone Number", phoneNumber, valid);
        return valid;
    }

    private static boolean isValidFaculty(Faculty faculty) {
        boolean valid = faculty != null;
        logValidationResult("Faculty", faculty != null ? faculty.toString() : "null", valid);
        return valid;
    }

    private static boolean isValidCourse(int course) {
        boolean valid = course >= 1 && course <= 5;
        logValidationResult("Course", String.valueOf(course), valid);
        return valid;
    }

    private static boolean isValidGroup(int group) {
        boolean valid = group >= 1 && group <= 10;
        logValidationResult("Group", String.valueOf(group), valid);
        return valid;
    }

    private static void logValidationResult(String fieldName, String value, boolean isValid) {
        if (isValid) {
            logger.info(fieldName + " is valid: " + value);
        } else {
            logger.error(fieldName + " is invalid: " + value);
        }
    }
}