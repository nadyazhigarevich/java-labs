package com.zhigarevich.student.service;

import com.zhigarevich.student.entity.Faculty;
import com.zhigarevich.student.entity.Student;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StudentServiceImpl implements StudentService {

    @Override
    public List<Student> getStudentsByFaculty(List<Student> students, Faculty faculty) {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getFaculty() == faculty) {
                result.add(student);
            }
        }
        return result;
    }

    @Override
    public List<Student> getStudentsByFacultyAndCourse(List<Student> students, Faculty faculty, int course) {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getFaculty() == faculty && student.getCourse() == course) {
                result.add(student);
            }
        }
        return result;
    }

    @Override
    public List<Student> getStudentsBornAfter(List<Student> students, int year) {
        List<Student> result = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, Calendar.JANUARY, 1);
        Date dateThreshold = calendar.getTime();

        for (Student student : students) {
            if (student.getDateOfBirth().after(dateThreshold)) {
                result.add(student);
            }
        }
        return result;
    }
}