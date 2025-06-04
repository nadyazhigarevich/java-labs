package com.zhigarevich.lab4.service;

import com.zhigarevich.lab4.exception.ApplicationException;
import com.zhigarevich.lab4.model.Sentence;
import com.zhigarevich.lab4.model.contract.TextComponent;

import java.util.List;
import java.util.Map;

/**
 * Provides text processing operations for composite text structures.
 */
public interface TextService {

    /**
     * Sorts paragraphs by their sentence count in ascending order.
     * @param text the root text component to process
     * @throws ApplicationException if sorting fails
     */
    void sortParagraphsBySentenceCount(TextComponent text) throws ApplicationException;

    /**
     * Finds sentences containing the longest word in the text.
     * @param text the root text component to analyze
     * @return list of sentences with longest words
     */
    List<Sentence> findSentencesWithLongestWord(TextComponent text);

    /**
     * Removes sentences shorter than the specified word count.
     * @param text the root text component to modify
     * @param minWordCount minimum words required to keep sentence
     * @throws ApplicationException if removal fails
     */
    void removeShortSentences(TextComponent text, int minWordCount) throws ApplicationException;

    /**
     * Counts occurrences of duplicate words in the text.
     * @param text the root text component to analyze
     * @return map of words and their occurrence counts
     */
    Map<String, Integer> countDuplicateWords(TextComponent text);

    /**
     * Counts vowels and consonants in each sentence.
     * @param text the root text component to analyze
     * @return map with sentences as keys and int arrays [vowels, consonants] as values
     */
    Map<Sentence, int[]> countVowelsAndConsonants(TextComponent text);

    /**
     * Finds the maximum word length in the text.
     * @param text the root text component to analyze
     * @return length of the longest word
     */
    int findMaxWordLength(TextComponent text);

    /**
     * Collects sentences containing words of specified length.
     * @param text the root text component to analyze
     * @param length required word length to match
     * @return list of matching sentences
     */
    List<Sentence> collectSentencesWithWordLength(TextComponent text, int length);
}