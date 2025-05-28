package com.zhigarevich.text.interpreter;

import java.util.ArrayDeque;

public class ExpressionContext {
    private ArrayDeque<Double> values = new ArrayDeque<>();

    public Double popValue() {
        return values.pop();
    }

    public void pushValue(Double value) {
        values.push(value);
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public void clear() {
        values.clear();
    }
}