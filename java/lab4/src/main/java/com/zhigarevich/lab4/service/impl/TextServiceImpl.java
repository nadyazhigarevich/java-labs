package com.zhigarevich.lab4.service.impl;

import com.zhigarevich.lab4.exception.ApplicationException;
import com.zhigarevich.lab4.model.Document;
import com.zhigarevich.lab4.model.Sentence;
import com.zhigarevich.lab4.model.contract.TextComponent;
import com.zhigarevich.lab4.model.Word;
import com.zhigarevich.lab4.service.TextService;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TextServiceImpl implements TextService {

    @Override
    public void sortParagraphsBySentenceCount(TextComponent text) throws ApplicationException {
        if (!(text instanceof Document)) {
            throw new ApplicationException("Text component must be a Document");
        }
        text.getChildren().sort(Comparator.comparingInt(p -> p.getChildren().size()));
    }

    @Override
    public List<Sentence> findSentencesWithLongestWord(TextComponent text) {
        int maxLength = text.getChildren().stream()
                .flatMap(p -> p.getChildren().stream())
                .flatMap(s -> s.getChildren().stream())
                .mapToInt(w -> ((Word)w).getText().length())
                .max().orElse(0);

        return text.getChildren().stream()
                .flatMap(p -> p.getChildren().stream())
                .filter(s -> s.getChildren().stream()
                        .anyMatch(w -> ((Word)w).getText().length() == maxLength))
                .map(s -> (Sentence)s)
                .toList();
    }

    @Override
    public void removeShortSentences(TextComponent text, int minWordCount) throws ApplicationException {
        if (!(text instanceof Document)) throw new ApplicationException("Text component must be a Document");
        text.getChildren().forEach(p ->
                p.getChildren().removeIf(s -> s.getChildren().size() < minWordCount));
    }

    @Override
    public Map<String, Integer> countDuplicateWords(TextComponent text) {
        Map<String, Integer> counts = new HashMap<>();
        text.getChildren().forEach(p ->
                p.getChildren().forEach(s ->
                        s.getChildren().forEach(w ->
                                counts.merge(((Word)w).getText().toLowerCase(), 1, Integer::sum))));

        return counts.entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<Sentence, int[]> countVowelsAndConsonants(TextComponent text) {
        Map<Sentence, int[]> result = new HashMap<>();
        String vowels = "aeiouAEIOU";

        text.getChildren().forEach(p ->
                p.getChildren().forEach(s -> {
                    int[] counts = new int[2];
                    s.getChildren().forEach(w -> {
                        for (char c : ((Word)w).getText().toCharArray()) {
                            if (Character.isLetter(c)) {
                                counts[vowels.indexOf(c) >= 0 ? 0 : 1]++;
                            }
                        }
                    });
                    result.put((Sentence)s, counts);
                }));

        return result;
    }

    @Override
    public int findMaxWordLength(TextComponent text) {
        return text.getChildren().stream()
                .flatMap(p -> p.getChildren().stream())
                .flatMap(s -> s.getChildren().stream())
                .mapToInt(w -> ((Word)w).getText().length())
                .max().orElse(0);
    }

    @Override
    public List<Sentence> collectSentencesWithWordLength(TextComponent text, int length) {
        return text.getChildren().stream()
                .flatMap(p -> p.getChildren().stream())
                .filter(s -> s.getChildren().stream()
                        .anyMatch(w -> ((Word)w).getText().length() == length))
                .map(s -> (Sentence)s)
                .toList();
    }
}