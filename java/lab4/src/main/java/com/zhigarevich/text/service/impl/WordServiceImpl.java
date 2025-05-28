package com.zhigarevich.text.service.impl;

import com.zhigarevich.text.model.TextComponent;
import com.zhigarevich.text.service.WordService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WordServiceImpl implements WordService {
    @Override
    public int countSameWords(TextComponent text) {
        Map<String, Long> wordCount = getAllWords(text).stream()
                .collect(Collectors.groupingBy(
                        String::toLowerCase,
                        Collectors.counting()
                ));

        return (int) wordCount.values().stream()
                .filter(count -> count > 1)
                .count();
    }

    @Override
    public Map<String, Integer> countVowelsAndConsonants(TextComponent text) {
        Map<String, Integer> result = new HashMap<>();
        result.put("vowels", 0);
        result.put("consonants", 0);

        getAllWords(text).forEach(word -> {
            for (char c : word.toLowerCase().toCharArray()) {
                if (isVowel(c)) {
                    result.put("vowels", result.get("vowels") + 1);
                } else if (Character.isLetter(c)) {
                    result.put("consonants", result.get("consonants") + 1);
                }
            }
        });

        return result;
    }

    @Override
    public int getLongestWordLength(TextComponent text) {
        return getAllWords(text).stream()
                .mapToInt(String::length)
                .max()
                .orElse(0);
    }

    private List<String> getAllWords(TextComponent text) {
        List<String> words = new ArrayList<>();
        for (TextComponent paragraph : text.getChildren()) {
            for (TextComponent sentence : paragraph.getChildren()) {
                for (TextComponent lexeme : sentence.getChildren()) {
                    for (TextComponent word : lexeme.getChildren()) {
                        words.add(word.toString());
                    }
                }
            }
        }
        return words;
    }

    private boolean isVowel(char c) {
        return "aeiouаеёиоуыэюя".indexOf(c) != -1;
    }
}