package com.zhigarevich.lab4.reader;

import com.zhigarevich.lab4.exception.ApplicationException;

@FunctionalInterface
public interface TextPropertyReader {

    String loadProperty(String fileName) throws ApplicationException;
}