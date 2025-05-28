package com.zhigarevich.text.service.impl;

import com.zhigarevich.text.model.Document;
import com.zhigarevich.text.model.TextComponent;
import com.zhigarevich.text.service.*;

import java.util.Map;

public class TextOperationServiceImpl implements TextOperationService {
    private final ParagraphService paragraphService;
    private final SentenceService sentenceService;
    private final WordService wordService;

    public TextOperationServiceImpl() {
        this.paragraphService = new ParagraphServiceImpl();
        this.sentenceService = new SentenceServiceImpl();
        this.wordService = new WordServiceImpl();
    }

    @Override
    public TextComponent sortParagraphsBySentenceCount(TextComponent text) {
        return paragraphService.sortParagraphsBySentenceCount(text);
    }

    @Override
    public TextComponent findSentencesWithLongestWord(TextComponent text) {
        TextComponent result = new Document();
        sentenceService.findSentencesWithLongestWord(text).forEach(result::add);
        return result;
    }

    @Override
    public TextComponent removeShortSentences(TextComponent text, int minWordCount) {
        return sentenceService.removeShortSentences(text, minWordCount);
    }

    @Override
    public int countSameWords(TextComponent text) {
        return wordService.countSameWords(text);
    }

    @Override
    public String countVowelsAndConsonants(TextComponent text) {
        Map<String, Integer> counts = wordService.countVowelsAndConsonants(text);
        return String.format("Гласные: %d, Согласные: %d",
                counts.get("vowels"), counts.get("consonants"));
    }
}