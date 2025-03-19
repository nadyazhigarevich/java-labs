package com.zhigarevich.student.factory;

import com.zhigarevich.student.entity.Address;
import com.zhigarevich.student.entity.Faculty;
import com.zhigarevich.student.entity.Student;
import com.zhigarevich.student.utility.StudentUtils;
import com.zhigarevich.student.validator.StudentValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StudentFactory {
    private static final Random random = new Random();

    public static Student createRandomStudent(int id) {
        String lastName = StudentUtils.getRandomLastName();
        String firstName = StudentUtils.getRandomFirstName();
        String patronymic = StudentUtils.getRandomPatronymic();
        LocalDate dateOfBirth = StudentUtils.generateRandomDateOfBirth();
        Address address = AddressFactory.createRandomAddress();
        String phoneNumber = StudentUtils.generateRandomPhoneNumber();

        Faculty faculty = Faculty.values()[random.nextInt(Faculty.values().length)];
        int course = StudentUtils.generateRandomCourse();
        int group = StudentUtils.generateRandomGroup();

        Student student = new Student(id, lastName, firstName, patronymic, dateOfBirth, address, phoneNumber, faculty, course, group);

        if (!StudentValidator.isValidStudent(student)) {
            throw new IllegalArgumentException("Invalid student data: " + student);
        }

        return student;
    }

    public static List<Student> createRandomStudents(int count) {
        List<Student> students = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            students.add(createRandomStudent(i));
        }
        return students;
    }
}