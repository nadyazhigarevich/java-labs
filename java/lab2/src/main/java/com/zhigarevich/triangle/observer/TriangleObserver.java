package com.zhigarevich.triangle.observer;

import com.zhigarevich.triangle.entity.Triangle;
import com.zhigarevich.triangle.exception.TriangleException;
import com.zhigarevich.triangle.service.TriangleCalculationService;
import com.zhigarevich.triangle.service.impl.TriangleCalculationServiceImpl;
import com.zhigarevich.triangle.validator.impl.TriangleValidatorImpl;
import com.zhigarevich.triangle.warehouse.TriangleWarehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TriangleObserver {

    private static final Logger log = LogManager.getLogger(TriangleObserver.class);
    private static TriangleObserver instance;
    private final TriangleWarehouse warehouse;
    private final TriangleCalculationService calculationService;

    private TriangleObserver(){
        this.warehouse = TriangleWarehouse.getInstance();
        var validator = TriangleValidatorImpl.getInstance();
        this.calculationService = TriangleCalculationServiceImpl.getInstance(validator);
    }

    public static TriangleObserver getInstance(){
        if (instance == null) {
            instance = new TriangleObserver();
        }
        return instance;
    }

    public void put(Triangle triangle) {
        log.debug("Observer works");
        try {
            var perimeter = this.calculationService.calculatePerimeter(triangle);
            var area = this.calculationService.calculateArea(triangle);
            this.warehouse.put(triangle.getId(), area, perimeter);
        } catch (TriangleException e) {
            log.error(e);
        }
    }
}
