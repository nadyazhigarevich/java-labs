package com.zhigarevich.text.service.impl;

import com.zhigarevich.text.model.*;
import com.zhigarevich.text.service.TextOperationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.*;
import java.util.stream.Collectors;

public class TextOperationServiceImpl implements TextOperationService {
    private static final Logger logger = LogManager.getLogger();
    private static final String VOWELS = "aeiouаеёиоуыэюя";

    @Override
    public String formatComponent(TextComponent component) {
        return "";
    }

    @Override
    public TextComponent sortParagraphsBySentenceCount(TextComponent text) {
        logger.debug("Sorting paragraphs by sentence count");
        List<TextComponent> paragraphs = new ArrayList<>(text.getChildren());
        paragraphs.sort(Comparator.comparingInt(p -> p.getChildren().size()));

        Document result = new Document();
        paragraphs.forEach(result::add);
        return result;
    }

    @Override
    public TextComponent findSentencesWithLongestWord(TextComponent text) {
        logger.debug("Finding sentences with longest word");
        int maxLength = findMaxWordLength(text);
        Document result = new Document();
        Paragraph tempParagraph = new Paragraph();

        text.getChildren().forEach(paragraph -> {
            paragraph.getChildren().forEach(sentence -> {
                if (hasWordOfLength(sentence, maxLength)) {
                    tempParagraph.add(sentence);
                }
            });
        });

        if (!tempParagraph.getChildren().isEmpty()) {
            result.add(tempParagraph);
        }
        return result;
    }

    @Override
    public TextComponent removeShortSentences(TextComponent text, int minWordCount) {
        logger.debug("Removing sentences with word count less than " + minWordCount);
        Document result = new Document();

        for (TextComponent paragraph : text.getChildren()) {
            Paragraph newParagraph = new Paragraph();
            paragraph.getChildren().forEach(sentence -> {
                if (countWords(sentence) >= minWordCount) {
                    newParagraph.add(sentence);
                }
            });

            if (!newParagraph.getChildren().isEmpty()) {
                result.add(newParagraph);
            }
        }
        return result;
    }

    @Override
    public int countSameWords(TextComponent text) {
        logger.debug("Counting same words");
        Map<String, Integer> wordCounts = new HashMap<>();
        countWords(text, wordCounts);

        return (int) wordCounts.values().stream()
                .filter(count -> count > 1)
                .count();
    }

    @Override
    public String countVowelsAndConsonants(TextComponent text) {
        logger.debug("Counting vowels and consonants");
        int[] counts = countLetters(text);
        return String.format("Гласные: %d, Согласные: %d", counts[0], counts[1]);
    }

    private int findMaxWordLength(TextComponent component) {
        int maxLength = 0;
        for (TextComponent paragraph : component.getChildren()) {
            for (TextComponent sentence : paragraph.getChildren()) {
                for (TextComponent lexeme : sentence.getChildren()) {
                    for (TextComponent word : lexeme.getChildren()) {
                        maxLength = Math.max(maxLength, word.toString().length());
                    }
                }
            }
        }
        return maxLength;
    }

    private boolean hasWordOfLength(TextComponent sentence, int length) {
        for (TextComponent lexeme : sentence.getChildren()) {
            for (TextComponent word : lexeme.getChildren()) {
                if (word.toString().length() == length) {
                    return true;
                }
            }
        }
        return false;
    }

    private int countWords(TextComponent sentence) {
        int count = 0;
        for (TextComponent lexeme : sentence.getChildren()) {
            count += lexeme.getChildren().size();
        }
        return count;
    }

    private void countWords(TextComponent text, Map<String, Integer> wordCounts) {
        for (TextComponent paragraph : text.getChildren()) {
            for (TextComponent sentence : paragraph.getChildren()) {
                for (TextComponent lexeme : sentence.getChildren()) {
                    for (TextComponent word : lexeme.getChildren()) {
                        String wordStr = word.toString().toLowerCase();
                        wordCounts.put(wordStr, wordCounts.getOrDefault(wordStr, 0) + 1);
                    }
                }
            }
        }
    }

    private int[] countLetters(TextComponent text) {
        int vowels = 0;
        int consonants = 0;

        for (TextComponent paragraph : text.getChildren()) {
            for (TextComponent sentence : paragraph.getChildren()) {
                for (TextComponent lexeme : sentence.getChildren()) {
                    for (TextComponent word : lexeme.getChildren()) {
                        for (char c : word.toString().toLowerCase().toCharArray()) {
                            if (Character.isLetter(c)) {
                                if (VOWELS.indexOf(c) != -1) {
                                    vowels++;
                                } else {
                                    consonants++;
                                }
                            }
                        }
                    }
                }
            }
        }
        return new int[]{vowels, consonants};
    }
}