package com.zhigarevich.triangle.entity;

public enum TriangleType {
    ISOSCELES,
    EQUILATERAL,
    RECTANGULAR,
    ARBITRARY;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}