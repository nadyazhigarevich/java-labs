package com.zhigarevich.student.factory;

import com.zhigarevich.student.entity.Address;
import com.zhigarevich.student.entity.Faculty;
import com.zhigarevich.student.entity.Student;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class StudentFactory {
    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Williams", "Brown", "Jones"};
    private static final String[] FIRST_NAMES = {"James", "Robert", "John", "Michael", "William"};
    private static final String[] PATRONYMICS = {"Ivanovich", "Petrovich", "Sidorovich", "Nikolayevich", "Dmitrievich"};
    private static final Random random = new Random();

    public static Student createRandomStudent(int id) {
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String patronymic = PATRONYMICS[random.nextInt(PATRONYMICS.length)];

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR) - (random.nextInt(13) + 18);
        calendar.set(year, random.nextInt(12), random.nextInt(28) + 1);
        Date dateOfBirth = calendar.getTime();

        Address address = AddressFactory.createRandomAddress();
        String phoneNumber = "+1" + (random.nextInt(900000000) + 100000000);
        Faculty faculty = Faculty.values()[random.nextInt(Faculty.values().length)];
        int course = random.nextInt(5) + 1;
        int group = random.nextInt(10) + 1;

        return new Student(id, lastName, firstName, patronymic, dateOfBirth, address, phoneNumber, faculty, course, group);
    }

    public static List<Student> createRandomStudents(int count) {
        List<Student> students = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            students.add(createRandomStudent(i));
        }
        return students;
    }
}