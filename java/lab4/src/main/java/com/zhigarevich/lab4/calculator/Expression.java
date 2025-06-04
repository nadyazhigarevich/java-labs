package com.zhigarevich.lab4.calculator;

@FunctionalInterface
public interface Expression {
    String interpret(String context);
}