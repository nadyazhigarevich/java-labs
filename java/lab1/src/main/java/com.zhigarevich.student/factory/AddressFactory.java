package com.zhigarevich.student.factory;

import com.zhigarevich.student.entity.Address;
import com.zhigarevich.student.utility.AddressUtils;

public class AddressFactory {
    public static Address createRandomAddress() {
        return new Address(
                AddressUtils.getRandomCountry(),
                AddressUtils.getRandomCity(),
                AddressUtils.getRandomStreet()
        );
    }
}