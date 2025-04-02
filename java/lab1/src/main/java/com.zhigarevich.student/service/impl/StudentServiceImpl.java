package com.zhigarevich.student.service.impl;

import com.zhigarevich.student.entity.Faculty;
import com.zhigarevich.student.entity.Student;
import com.zhigarevich.student.service.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentServiceImpl implements StudentService {

    private static final Logger logger = LogManager.getLogger(StudentServiceImpl.class);

    @Override
    public List<Student> findStudentsByFaculty(List<Student> students, Faculty faculty) {
        List<Student> result = new ArrayList<>();
        logger.info("Finding students by faculty: " + faculty);
        for (Student student : students) {
            if (student.getFaculty() == faculty) {
                result.add(student);
            }
        }
        logger.info("Found " + result.size() + " students for faculty: " + faculty);
        return result;
    }

    @Override
    public List<Student> findStudentsByFacultyAndCourse(List<Student> students, Faculty faculty, int course) {
        List<Student> result = new ArrayList<>();
        logger.info("Finding students by faculty: " + faculty + " and course: " + course);
        for (Student student : students) {
            if (student.getFaculty() == faculty && student.getCourse() == course) {
                result.add(student);
            }
        }
        logger.info("Found " + result.size() + " students for faculty: " + faculty + " and course: " + course);
        return result;
    }

    @Override
    public List<Student> findStudentsBornAfter(List<Student> students, int year) {
        List<Student> result = new ArrayList<>();
        LocalDate dateThreshold = LocalDate.of(year, 1, 1);
        logger.info("Finding students born after: " + dateThreshold);

        for (Student student : students) {
            if (student.getDateOfBirth().isAfter(dateThreshold)) {
                result.add(student);
            }
        }
        logger.info("Found " + result.size() + " students born after: " + dateThreshold);
        return result;
    }
}