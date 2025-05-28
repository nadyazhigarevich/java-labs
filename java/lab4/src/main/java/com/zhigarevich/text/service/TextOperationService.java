package com.zhigarevich.text.service;

import com.zhigarevich.text.model.TextComponent;

public interface TextOperationService {
    String formatComponent(TextComponent component);

    TextComponent sortParagraphsBySentenceCount(TextComponent text);
    TextComponent findSentencesWithLongestWord(TextComponent text);
    TextComponent removeShortSentences(TextComponent text, int minWordCount);
    int countSameWords(TextComponent text);
    String countVowelsAndConsonants(TextComponent text);
}