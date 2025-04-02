package com.zhigarevich.student;

import com.zhigarevich.student.entity.Faculty;
import com.zhigarevich.student.entity.Student;
import com.zhigarevich.student.factory.StudentFactory;
import com.zhigarevich.student.service.StudentService;
import com.zhigarevich.student.service.impl.StudentServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            List<Student> studentList = StudentFactory.createRandomStudents(20);
            StudentService studentService = new StudentServiceImpl();

            logger.info("All Students:");
            for (Student student : studentList) {
                logger.info(student);
            }
            logger.info("");

            Faculty facultyToSearch = Faculty.FPMI;
            List<Student> studentsInFPMI = studentService.findStudentsByFaculty(studentList, facultyToSearch);
            logger.info("Students in " + facultyToSearch + ":");
            for (Student student : studentsInFPMI) {
                logger.info(student);
            }
            logger.info("");

            int courseToSearch = 2;
            List<Student> fmoCourse2Students = studentService.findStudentsByFacultyAndCourse(studentList, Faculty.FMO, courseToSearch);
            logger.info("FMO Course " + courseToSearch + " Students:");
            for (Student student : fmoCourse2Students) {
                logger.info(student);
            }
            logger.info("");

            List<Student> studentsBornAfter2000 = studentService.findStudentsBornAfter(studentList, 2000);
            logger.info("Students born after 2000:");
            for (Student student : studentsBornAfter2000) {
                logger.info(student);
            }
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        }
    }
}