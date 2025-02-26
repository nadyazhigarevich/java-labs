package com.zhigarevich.student.service;

import com.zhigarevich.student.entity.Faculty;
import com.zhigarevich.student.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> getStudentsByFaculty(List<Student> students, Faculty faculty);
    List<Student> getStudentsByFacultyAndCourse(List<Student> students, Faculty faculty, int course);
    List<Student> getStudentsBornAfter(List<Student> students, int year);
}