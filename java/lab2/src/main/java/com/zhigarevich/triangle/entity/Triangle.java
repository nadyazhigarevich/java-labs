package com.zhigarevich.triangle.entity;

public class Triangle {
    private int id;
    private double a;
    private double b;
    private double c;

    public Triangle() {
    }

    public Triangle(int id, double a, double b, double c) {
        this.id = id;
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public int getId() {
        return id;
    }

    public void setA(double a) {
        this.a = a;
    }

    public void setB(double b) {
        this.b = b;
    }

    public void setC(double c) {
        this.c = c;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != getClass()) return false;
        Triangle triangle = (Triangle) o;
        return a == triangle.a && b == triangle.b && c == triangle.c && id == triangle.id;
    }

    @Override
    public int hashCode() {
        int result = 17;
        long tempA = Double.doubleToLongBits(a);
        long tempB = Double.doubleToLongBits(b);
        long tempC = Double.doubleToLongBits(c);
        result = 31 * result + (int) (tempA ^ (tempA >>> 32));
        result = 31 * result + (int) (tempB ^ (tempB >>> 32));
        result = 31 * result + (int) (tempC ^ (tempC >>> 32));
        result = 31 * result + id;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Triangle{");
        sb.append("id=").append(id);
        sb.append(", a=").append(a);
        sb.append(", b=").append(b);
        sb.append(", c=").append(c);
        sb.append('}');
        return sb.toString();
    }
}