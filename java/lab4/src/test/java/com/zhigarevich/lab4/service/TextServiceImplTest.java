package com.zhigarevich.lab4.service;

import com.zhigarevich.lab4.exception.ApplicationException;
import com.elyashevich.lab4.model.*;
import com.zhigarevich.lab4.model.Document;
import com.zhigarevich.lab4.model.Paragraph;
import com.zhigarevich.lab4.model.Sentence;
import com.zhigarevich.lab4.model.Word;
import com.zhigarevich.lab4.service.impl.TextServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Text Service Implementation Tests")
class TextServiceImplTest {

    private TextService textService;
    private Document testDocument;

    @BeforeEach
    void setUp() {
        textService = new TextServiceImpl();
        testDocument = createTestDocument();
    }

    private Document createTestDocument() {
        var document = new Document("Test Document");

        // Paragraph 1
        var paragraph1 = new Paragraph("First paragraph");

        var sentence1 = new Sentence("Hello world");
        sentence1.add(new Word("Hello"));
        sentence1.add(new Word("world"));

        var sentence2 = new Sentence("This is a test");
        sentence2.add(new Word("This"));
        sentence2.add(new Word("is"));
        sentence2.add(new Word("a"));
        sentence2.add(new Word("test"));

        paragraph1.add(sentence1);
        paragraph1.add(sentence2);
        document.add(paragraph1);

        // Paragraph 2
        var paragraph2 = new Paragraph("Second paragraph");

        var sentence3 = new Sentence("Another paragraph");
        sentence3.add(new Word("Another"));
        sentence3.add(new Word("paragraph"));

        var sentence4 = new Sentence("With duplicate duplicate");
        sentence4.add(new Word("With"));
        sentence4.add(new Word("duplicate"));
        sentence4.add(new Word("duplicate"));

        var sentence5 = new Sentence("Short");
        sentence5.add(new Word("Short"));

        paragraph2.add(sentence3);
        paragraph2.add(sentence4);
        paragraph2.add(sentence5);
        document.add(paragraph2);

        return document;
    }

    @Test
    @Order(1)
    @DisplayName("Sort Paragraphs - Invalid Component")
    void sortParagraphsBySentenceCount_InvalidComponent_ThrowsException() {
        // Arrange
        var invalidComponent = new Paragraph("Invalid");

        // Act & Assert
        var exception = assertThrows(ApplicationException.class,
                () -> textService.sortParagraphsBySentenceCount(invalidComponent));
        assertEquals("Text component must be a Document", exception.getMessage());
    }

    @Test
    @Order(2)
    @DisplayName("Find Sentences With Longest Word")
    void findSentencesWithLongestWord_ValidDocument_ReturnsCorrectSentences() {
        // Arrange
        var expectedLongestWordLength = 9; // "duplicate"

        // Act
        var result = textService.findSentencesWithLongestWord(testDocument);

        // Assert
        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertEquals(expectedLongestWordLength,
                        textService.findMaxWordLength(testDocument))
        );
    }

    @ParameterizedTest
    @CsvSource({
            "1, 5",  // Keeps all sentences
            "2, 4",  // Removes "Short"
            "3, 2",  // Only keeps sentence with 4 words
            "4, 1"   // Removes all
    })
    @Order(3)
    @DisplayName("Remove Short Sentences - Parameterized")
    void removeShortSentences_VariousThresholds_RemovesCorrectly(int minWordCount, int expectedCount)
            throws ApplicationException {
        // Arrange
        var testDoc = createTestDocument();

        // Act
        textService.removeShortSentences(testDoc, minWordCount);
        var remaining = testDoc.getChildren().stream()
                .mapToInt(p -> p.getChildren().size())
                .sum();

        // Assert
        assertEquals(expectedCount, remaining);
    }

    @Test
    @Order(4)
    @DisplayName("Count Duplicate Words")
    void countDuplicateWords_ValidDocument_ReturnsCorrectCounts() {
        // Arrange
        var expectedWord = "duplicate";
        var expectedCount = 2;

        // Act
        var result = textService.countDuplicateWords(testDocument);

        // Assert
        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertTrue(result.containsKey(expectedWord)),
                () -> assertEquals(expectedCount, result.get(expectedWord))
        );
    }

    @Test
    @Order(5)
    @DisplayName("Count Vowels and Consonants")
    void countVowelsAndConsonants_ValidDocument_ReturnsCorrectCounts() {
        // Arrange
        var testSentence = testDocument.getChildren().get(0).getChildren().get(0);
        var expectedVowels = 3; // Hello (e,o) + world (o)

        // Act
        var result = textService.countVowelsAndConsonants(testDocument);
        var counts = result.get(testSentence);

        // Assert
        assertAll(
                () -> assertEquals(5, result.size()),
                () -> assertEquals(expectedVowels, counts[0]),
                () -> assertEquals(7, counts[1])  // H,l,l + w,r,l,d
        );
    }

    @Test
    @Order(6)
    @DisplayName("Find Maximum Word Length")
    void findMaxWordLength_ValidDocument_ReturnsCorrectValue() {
        // Arrange
        var expectedLength = 9; // "duplicate"

        // Act
        var result = textService.findMaxWordLength(testDocument);

        // Assert
        assertEquals(expectedLength, result);
    }

    @ParameterizedTest
    @MethodSource("provideWordLengthTestCases")
    @Order(7)
    @DisplayName("Collect Sentences With Word Length")
    void collectSentencesWithWordLength_VariousLengths_ReturnsCorrectSentences(int length, int expectedCount) {
        // Act
        var result = textService.collectSentencesWithWordLength(testDocument, length);

        // Assert
        assertEquals(expectedCount, result.size());
    }

    private static Stream<Arguments> provideWordLengthTestCases() {
        return Stream.of(
                arguments(5, 2),  // Hello, world, Short
                arguments(7, 1),  // Another
                arguments(8, 0),  // duplicate
                arguments(2, 1)   // is
        );
    }
}