package com.zhigarevich.text.service;

import com.zhigarevich.text.model.TextComponent;
import java.util.List;

public interface SentenceService {
    List<TextComponent> getAllSentences(TextComponent text);
    List<TextComponent> findSentencesWithLongestWord(TextComponent text);
    TextComponent removeShortSentences(TextComponent text, int minWordCount);
}