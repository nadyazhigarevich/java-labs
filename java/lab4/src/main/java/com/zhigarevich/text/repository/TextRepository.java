package com.zhigarevich.text.repository;

import com.zhigarevich.text.model.TextComponent;
import java.util.List;
import java.util.Map;

public interface TextRepository {
    void saveText(TextComponent textComponent);
    TextComponent getText();
    List<TextComponent> getParagraphs();
    List<TextComponent> getSentences();
    List<TextComponent> getLexemes();
    List<TextComponent> getWords();
    List<TextComponent> findSentencesWithLongestWord();
    void sortParagraphsBySentenceCount();
    void removeSentencesWithWordCountLessThan(int minWordCount);
    int countSameWordsIgnoreCase();
    Map<String, Integer> getVowelAndConsonantCount();
}