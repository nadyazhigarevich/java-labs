package com.zhigarevich.triangle.validator;

import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.exception.TriangleException;

public interface TriangleValidator {
    boolean isValid(Triangle triangle);
}