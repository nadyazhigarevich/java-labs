package com.zhigarevich.text.service;

import com.zhigarevich.text.model.TextComponent;
import java.util.Map;

public interface WordService {
    int countSameWords(TextComponent text);
    Map<String, Integer> countVowelsAndConsonants(TextComponent text);
    int getLongestWordLength(TextComponent text);
}