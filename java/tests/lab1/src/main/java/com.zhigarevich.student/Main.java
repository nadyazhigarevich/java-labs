package com.zhigarevich.student;

import com.zhigarevich.student.controller.StudentController;
import com.zhigarevich.student.entity.Faculty;
import com.zhigarevich.student.entity.Student;
import com.zhigarevich.student.factory.StudentFactory;
import com.zhigarevich.student.service.StudentService;
import com.zhigarevich.student.service.StudentServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Student> studentList = StudentFactory.createRandomStudents(20);
        StudentService studentService = new StudentServiceImpl();

        StudentController controller = new StudentController(studentService);

        System.out.println("All Students:");
        for (Student student : studentList) {
            System.out.println(student);
        }
        System.out.println();

        Faculty facultyToSearch = Faculty.FPMI;
        List<Student> studentsInFPMI = controller.getStudentsByFaculty(studentList, facultyToSearch);
        System.out.println("Students in " + facultyToSearch + ":");
        for (Student student : studentsInFPMI) {
            System.out.println(student);
        }
        System.out.println();

        int courseToSearch = 2;
        List<Student> fmoCourse2Students = controller.getStudentsByFacultyAndCourse(studentList, Faculty.FMO, courseToSearch);
        System.out.println("FMO Course " + courseToSearch + " Students:");
        for (Student student : fmoCourse2Students) {
            System.out.println(student);
        }
        System.out.println();

        List<Student> studentsBornAfter2000 = controller.getStudentsBornAfter(studentList, 2000);
        System.out.println("Students born after 2000:");
        for (Student student : studentsBornAfter2000) {
            System.out.println(student);
        }
    }
}