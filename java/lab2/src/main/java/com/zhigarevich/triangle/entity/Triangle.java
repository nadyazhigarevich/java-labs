package com.zhigarevich.triangle.entity;

import com.zhigarevich.triangle.observer.TriangleObserver;

public class Triangle {
    private int id;
    private double a;
    private double b;
    private double c;
    private TriangleType type = TriangleType.UNDEFINED;
    private TriangleObserver observer = TriangleObserver.getInstance();

    public Triangle() {
    }

    public Triangle(int id, double a, double b, double c) {
        this.id = id;
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;

    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
        observer.put(this);
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
        observer.put(this);
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
        observer.put(this);
    }

    public TriangleType getType() {
        return type;
    }

    public void setType(TriangleType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return id == triangle.id && Double.compare(a, triangle.a) == 0 && Double.compare(b, triangle.b) == 0 && Double.compare(c, triangle.c) == 0 && type == triangle.type;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(a);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(b);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(c);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Triangle{");
        sb.append("id=").append(id);
        sb.append(", a=").append(a);
        sb.append(", b=").append(b);
        sb.append(", c=").append(c);
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}