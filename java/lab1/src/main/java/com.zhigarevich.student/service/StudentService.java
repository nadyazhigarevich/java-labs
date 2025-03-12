package com.zhigarevich.student.service;

import com.zhigarevich.student.entity.Faculty;
import com.zhigarevich.student.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> findStudentsByFaculty(List<Student> students, Faculty faculty);

    List<Student> findStudentsByFacultyAndCourse(List<Student> students, Faculty faculty, int course);

    List<Student> findStudentsBornAfter(List<Student> students, int year);
}