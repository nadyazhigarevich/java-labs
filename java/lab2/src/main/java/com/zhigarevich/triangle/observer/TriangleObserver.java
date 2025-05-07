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

    public static TriangleObserver getInstance() {
        if (instance == null) {
            instance = new TriangleObserver();
        }
        return instance;
    }

    public void changed(Triangle triangle) {
        log.debug("Observer works");
        TriangleWarehouse warehouse = TriangleWarehouse.getInstance();
        TriangleValidatorImpl validator = (TriangleValidatorImpl) TriangleValidatorImpl.getInstance();
        TriangleCalculationService calculationService = TriangleCalculationServiceImpl.getInstance(validator);

        try {
            var perimeter = calculationService.calculatePerimeter(triangle);
            var area = calculationService.calculateArea(triangle);
            warehouse.put(triangle.getId(), area, perimeter);
        } catch (TriangleException e) {
            log.error(e);
        }
    }
}