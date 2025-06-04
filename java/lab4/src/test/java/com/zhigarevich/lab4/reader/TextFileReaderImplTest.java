package com.zhigarevich.lab4.reader;

import com.zhigarevich.lab4.exception.ApplicationException;
import com.zhigarevich.lab4.reader.impl.TextFileReaderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Text File Reader Implementation Tests")
class TextFileReaderImplTest {

    private TextFileReaderImpl fileReader;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        fileReader = new TextFileReaderImpl();
    }

    @Test
    @DisplayName("Read File - Successful")
    void read_ValidFile_ReturnsContent() throws Exception {
        // Arrange
        Path testFile = tempDir.resolve("test.txt");
        String expectedContent = "test content";
        Files.write(testFile, expectedContent.getBytes());

        // Act
        String result = fileReader.read(testFile.toString());

        // Assert
        assertEquals(expectedContent, result);
    }

    @Test
    @DisplayName("Read File - File Not Found")
    void read_NonExistentFile_ThrowsBusinessException() {
        // Arrange
        String nonExistentPath = tempDir.resolve("nonexistent.txt").toString();

        // Act & Assert
        ApplicationException exception = assertThrows(ApplicationException.class,
                () -> fileReader.read(nonExistentPath));

        assertAll(
                () -> assertEquals("File was not found: " + nonExistentPath, exception.getMessage()),
                () -> assertInstanceOf(NoSuchFileException.class, exception.getCause())
        );
    }

    @Test
    @DisplayName("Read File - IO Error")
    void read_UnreadableFile_ThrowsBusinessException() throws Exception {
        // Arrange
        Path unreadableFile = tempDir.resolve("unreadable.txt");
        Files.createFile(unreadableFile);
        unreadableFile.toFile().setReadable(false);

        // Act & Assert
        ApplicationException exception = assertThrows(ApplicationException.class,
                () -> fileReader.read(unreadableFile.toString()));

        assertAll(
                () -> assertEquals("Error during parsing quadratic equations file", exception.getMessage()),
                () -> assertTrue(exception.getCause() instanceof IOException)
        );
    }

    @Test
    @DisplayName("Read File - Null Path")
    void read_NullPath_ThrowsNullPointerException() {
        // Act & Assert
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> fileReader.read(null));

        assertEquals("The path to migrations cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Read File - Large File")
    void read_LargeFile_ReturnsContent() throws Exception {
        // Arrange
        Path largeFile = tempDir.resolve("large.txt");
        String largeContent = "a".repeat(1000000); // 1MB content
        Files.write(largeFile, largeContent.getBytes());

        // Act
        String result = fileReader.read(largeFile.toString());

        // Assert
        assertEquals(largeContent, result);
    }
}