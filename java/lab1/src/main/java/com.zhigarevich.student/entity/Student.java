package com.zhigarevich.student.entity;

import java.time.LocalDate;

public class Student {
    private long id;
    private String lastName;
    private String firstName;
    private String patronymic;
    private LocalDate dateOfBirth;
    private Address address;
    private String phoneNumber;
    private Faculty faculty;
    private int course;
    private int group;

    public Student(int id, String lastName, String firstName, String patronymic, LocalDate dateOfBirth, Address address, String phoneNumber, Faculty faculty, int course, int group) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.faculty = faculty;
        this.course = course;
        this.group = group;
    }

    public Student() {
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address=" + address +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", faculty=" + faculty +
                ", course=" + course +
                ", group=" + group +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Student)) return false;

        Student student = (Student) o;
        return id == student.id
                && course == student.course
                && group == student.group
                && (lastName == null ? student.lastName == null : lastName.equals(student.lastName))
                && (firstName == null ? student.firstName == null : firstName.equals(student.firstName))
                && (patronymic == null ? student.patronymic == null : patronymic.equals(student.patronymic))
                && (dateOfBirth == null ? student.dateOfBirth == null : dateOfBirth.equals(student.dateOfBirth))
                && (address == null ? student.address == null : address.equals(student.address))
                && (phoneNumber == null ? student.phoneNumber == null : phoneNumber.equals(student.phoneNumber))
                && (faculty == null ? student.faculty == null : faculty.equals(student.faculty));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Long.hashCode(id);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (patronymic != null ? patronymic.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (faculty != null ? faculty.hashCode() : 0);
        result = 31 * result + course;
        result = 31 * result + group;
        return result;
    }
}