package com.zhigarevich.lab4.reader;

import com.zhigarevich.lab4.exception.ApplicationException;
import com.zhigarevich.lab4.reader.impl.TextPropertyReaderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Text Property Reader Implementation Tests")
class TextPropertyReaderImplTest {

    private TextPropertyReaderImpl propertyReader;
    private static final String TEST_FILE_NAME = "test";
    private static final String TEST_PATH_VALUE = "test/path.txt";

    @BeforeEach
    void setUp() {
        propertyReader = new TextPropertyReaderImpl();
    }

    @Test
    @DisplayName("Load Property - Successful")
    void loadProperty_ValidFile_ReturnsPath() throws ApplicationException {
        try (MockedStatic<ResourceBundle> mockedBundle = mockStatic(ResourceBundle.class)) {
            // Arrange
            var mockBundle = mock(ResourceBundle.class);
            when(ResourceBundle.getBundle(TEST_FILE_NAME)).thenReturn(mockBundle);
            when(mockBundle.getString(TextPropertyReaderImpl.TEXT_FILE_PATH)).thenReturn(TEST_PATH_VALUE);

            // Act
            var result = propertyReader.loadProperty(TEST_FILE_NAME);

            // Assert
            assertEquals(TEST_PATH_VALUE, result);
            mockedBundle.verify(() -> ResourceBundle.getBundle(TEST_FILE_NAME));
            verify(mockBundle).getString(TextPropertyReaderImpl.TEXT_FILE_PATH);
        }
    }

    @Test
    @DisplayName("Load Property - Missing Resource Exception")
    void loadProperty_InvalidFile_ThrowsBusinessException() {
        try (MockedStatic<ResourceBundle> mockedBundle = mockStatic(ResourceBundle.class)) {
            // Arrange
            when(ResourceBundle.getBundle(TEST_FILE_NAME)).thenThrow(new MissingResourceException(
                "Can't find bundle", 
                "test", 
                TEST_FILE_NAME
            ));

            // Act & Assert
            var exception = assertThrows(ApplicationException.class,
                () -> propertyReader.loadProperty(TEST_FILE_NAME));
            assertEquals("Could not load property file: " + TEST_FILE_NAME, exception.getMessage());
        }
    }

    @Test
    @DisplayName("Load Property - Empty File Name")
    void loadProperty_EmptyFileName_ThrowsBusinessException() {
        assertThrows(ApplicationException.class,
            () -> propertyReader.loadProperty(""));
    }

    @Test
    @DisplayName("Constant Value")
    void textFilePathConstant_HasCorrectValue() {
        assertEquals("text.file.path", TextPropertyReaderImpl.TEXT_FILE_PATH);
    }
}