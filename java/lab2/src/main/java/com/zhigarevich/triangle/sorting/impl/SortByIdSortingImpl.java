package com.zhigarevich.triangle.sorting.impl;

import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.sorting.SortByIdSorting;

public class SortByIdSortingImpl implements SortByIdSorting {
    @Override
    public int compare(Triangle o1, Triangle o2) {
        return Integer.compare(o1.getId(), o2.getId());
    }
}
