package com.zhigarevich.lab4.reader.impl;

import com.zhigarevich.lab4.exception.ApplicationException;
import com.zhigarevich.lab4.reader.TextFileReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

public class TextFileReaderImpl implements TextFileReader {

    @Override
    public String read(final String pathToEquationsFile) throws ApplicationException {
        if (pathToEquationsFile == null) {
            throw new ApplicationException("The path to file cannot be null");
        }
        try {
            return new String(Files.readAllBytes(Paths.get(pathToEquationsFile)));
        } catch (NoSuchFileException e) {
            throw new ApplicationException(String.format("File was not found: %s", e.getMessage()), e);
        } catch (IOException e) {
            throw new ApplicationException("Error during parsing file", e);
        }
    }
}
