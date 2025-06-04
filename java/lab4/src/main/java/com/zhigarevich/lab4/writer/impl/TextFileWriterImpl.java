package com.zhigarevich.lab4.writer.impl;

import com.zhigarevich.lab4.exception.ApplicationException;
import com.zhigarevich.lab4.writer.TextFileWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TextFileWriterImpl implements TextFileWriter {

    public static final String FILEPATH = "output.txt";

    @Override
    public void write(String text) throws ApplicationException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILEPATH))) {
            writer.println(text);
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }
}
