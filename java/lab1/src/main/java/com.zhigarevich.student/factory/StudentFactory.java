package com.zhigarevich.student.factory;

import com.zhigarevich.student.entity.Address;
import com.zhigarevich.student.entity.Faculty;
import com.zhigarevich.student.entity.Student;
import com.zhigarevich.student.factory.utils.StudentUtil;
import com.zhigarevich.student.validator.StudentValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class StudentFactory {
    private static final Random RANDOM = new Random();

    private StudentFactory() {

    }

    public static Student createRandomStudent(int id) {
        String lastName = StudentUtil.generateRandomLastName();
        String firstName = StudentUtil.generateRandomFirstName();
        String patronymic = StudentUtil.generateRandomPatronymic();
        LocalDate dateOfBirth = StudentUtil.generateRandomDateOfBirth();
        Address address = AddressFactory.createRandomAddress();
        String phoneNumber = StudentUtil.generateRandomPhoneNumber();
        Faculty faculty = generateRandomFaculty();
        int course = StudentUtil.generateRandomCourse();
        int group = StudentUtil.generateRandomGroup();

        Student student = new Student(
                id, lastName, firstName, patronymic,
                dateOfBirth, address, phoneNumber,
                faculty, course, group
        );

        if (!StudentValidator.isValidStudent(student)) {
            throw new IllegalArgumentException("Invalid student data: " + student);
        }

        return student;
    }

    public static List<Student> createRandomStudents(int count) {
        List<Student> students = new ArrayList<>(count);
        for (int i = 1; i <= count; i++) {
            students.add(createRandomStudent(i));
        }
        return students;
    }

    private static Faculty generateRandomFaculty() {
        Faculty[] faculties = Faculty.values();
        return faculties[RANDOM.nextInt(faculties.length)];
    }
}