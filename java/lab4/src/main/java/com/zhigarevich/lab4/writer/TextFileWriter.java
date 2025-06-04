package com.zhigarevich.lab4.writer;

import com.zhigarevich.lab4.exception.ApplicationException;

/**
 * Component for writing text content.
 */
@FunctionalInterface
public interface TextFileWriter {

    /**
     * Writes the specified text content.
     *
     * @param text the content to be written
     * @throws ApplicationException if writing fails
     */
    void write(String text) throws ApplicationException;
}