package com.zhigarevich.student.factory;

import com.zhigarevich.student.entity.Address;
import com.zhigarevich.student.factory.utils.AddressUtil;

public final class AddressFactory {
    private AddressFactory() {

    }

    public static Address createRandomAddress() {
        return new Address(
                AddressUtil.generateRandomCountry(),
                AddressUtil.generateRandomCity(),
                AddressUtil.generateRandomStreet()
        );
    }
}