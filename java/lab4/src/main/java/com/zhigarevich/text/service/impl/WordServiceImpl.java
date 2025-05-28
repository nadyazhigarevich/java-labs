package com.zhigarevich.text.service.impl;

import com.zhigarevich.text.model.TextComponent;
import com.zhigarevich.text.service.WordService;

import java.util.HashMap;
import java.util.Map;

public class WordServiceImpl implements WordService {
    @Override
    public int countSameWords(TextComponent text) {
        Map<String, Integer> wordCount = new HashMap<>();
        collectWords(text, wordCount);

        return (int) wordCount.values().stream()
                .filter(count -> count > 1)
                .count();
    }

    @Override
    public Map<String, Integer> countVowelsAndConsonants(TextComponent text) {
        Map<String, Integer> counts = new HashMap<>();
        counts.put("vowels", 0);
        counts.put("consonants", 0);

        for (TextComponent paragraph : text.getChildren()) {
            for (TextComponent sentence : paragraph.getChildren()) {
                for (TextComponent lexeme : sentence.getChildren()) {
                    for (TextComponent word : lexeme.getChildren()) {
                        countLetters(word.toString(), counts);
                    }
                }
            }
        }

        return counts;
    }

    @Override
    public int getLongestWordLength(TextComponent text) {
        int maxLength = 0;

        for (TextComponent paragraph : text.getChildren()) {
            for (TextComponent sentence : paragraph.getChildren()) {
                for (TextComponent lexeme : sentence.getChildren()) {
                    for (TextComponent word : lexeme.getChildren()) {
                        int length = word.toString().length();
                        if (length > maxLength) {
                            maxLength = length;
                        }
                    }
                }
            }
        }

        return maxLength;
    }

    private void collectWords(TextComponent text, Map<String, Integer> wordCount) {
        for (TextComponent paragraph : text.getChildren()) {
            for (TextComponent sentence : paragraph.getChildren()) {
                for (TextComponent lexeme : sentence.getChildren()) {
                    for (TextComponent word : lexeme.getChildren()) {
                        String wordStr = word.toString().toLowerCase();
                        wordCount.put(wordStr, wordCount.getOrDefault(wordStr, 0) + 1);
                    }
                }
            }
        }
    }

    private void countLetters(String word, Map<String, Integer> counts) {
        String vowels = "aeiouаеёиоуыэюя";
        word = word.toLowerCase();

        for (char c : word.toCharArray()) {
            if (Character.isLetter(c)) {
                if (vowels.indexOf(c) != -1) {
                    counts.put("vowels", counts.get("vowels") + 1);
                } else {
                    counts.put("consonants", counts.get("consonants") + 1);
                }
            }
        }
    }
}