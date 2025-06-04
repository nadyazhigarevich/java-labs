package com.zhigarevich.lab4.reader;

import com.zhigarevich.lab4.exception.ApplicationException;

@FunctionalInterface
public interface TextFileReader {

    String read(String pathToEquationsFile) throws ApplicationException;
}