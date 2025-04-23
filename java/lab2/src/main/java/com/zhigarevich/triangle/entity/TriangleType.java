package com.zhigarevich.triangle.entity;

public enum TriangleType {
    UNDEFINED,
    ISOSCELES,
    EQUILATERAL,
    RECTANGULAR,
    ARBITRARY;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}