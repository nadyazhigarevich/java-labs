package com.zhigarevich.triangle.warehouse;

import java.util.HashMap;
import java.util.Map;

public class TriangleWarehouse {
    private static TriangleWarehouse instance;
    private final Map<Integer, TriangleParams> params;

    private TriangleWarehouse() {
        this.params = new HashMap<>();
    }

    public static TriangleWarehouse getInstance() {
        if (instance == null) {
            instance = new TriangleWarehouse();
        }
        return instance;
    }

    public void put(int id, double area, double perimeter) {
        this.params.put(id, new TriangleParams(area, perimeter));
    }

    public TriangleParams findParam(int id) {
        return this.params.get(id);
    }
}
