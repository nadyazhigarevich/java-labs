package com.zhigarevich.student.controller;

import com.zhigarevich.student.entity.Faculty;
import com.zhigarevich.student.entity.Student;
import com.zhigarevich.student.service.StudentService;

import java.util.List;

public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    public List<Student> getStudentsByFaculty(List<Student> studentList, Faculty faculty) {
        return studentService.getStudentsByFaculty(studentList, faculty);
    }

    public List<Student> getStudentsByFacultyAndCourse(List<Student> studentList, Faculty faculty, int course) {
        return studentService.getStudentsByFacultyAndCourse(studentList, faculty, course);
    }

    public List<Student> getStudentsBornAfter(List<Student> studentList, int year) {
        return studentService.getStudentsBornAfter(studentList, year);
    }
}