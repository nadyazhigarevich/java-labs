package com.zhigarevich.lab4.reader.impl;

import com.zhigarevich.lab4.exception.ApplicationException;
import com.zhigarevich.lab4.reader.TextPropertyReader;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class TextPropertyReaderImpl implements TextPropertyReader {
    public static final String TEXT_FILE_PATH = "text.file.path";

    @Override
    public String loadProperty(final String fileName) throws ApplicationException {
        try {
            ResourceBundle resource = ResourceBundle.getBundle(fileName);
            return resource.getString(TEXT_FILE_PATH);
        } catch (MissingResourceException e) {
            throw new ApplicationException("Could not load property file: " + fileName);
        }
    }
}
