package com.zhigarevich.text.service.impl;

import com.zhigarevich.text.model.*;
import com.zhigarevich.text.service.SentenceService;
import com.zhigarevich.text.service.WordService;

import java.util.*;

public class SentenceServiceImpl implements SentenceService {
    @Override
    public List<TextComponent> getAllSentences(TextComponent text) {
        List<TextComponent> sentences = new ArrayList<>();
        for (TextComponent paragraph : text.getChildren()) {
            sentences.addAll(paragraph.getChildren());
        }
        return sentences;
    }

    @Override
    public List<TextComponent> findSentencesWithLongestWord(TextComponent text) {
        WordService wordService = new WordServiceImpl();
        int maxLength = wordService.getLongestWordLength(text);

        List<TextComponent> result = new ArrayList<>();
        for (TextComponent paragraph : text.getChildren()) {
            for (TextComponent sentence : paragraph.getChildren()) {
                if (containsWordOfLength(sentence, maxLength)) {
                    result.add(sentence);
                }
            }
        }
        return result;
    }

    @Override
    public TextComponent removeShortSentences(TextComponent text, int minWordCount) {
        Document newDocument = new Document();

        for (TextComponent paragraph : text.getChildren()) {
            Paragraph newParagraph = new Paragraph();

            for (TextComponent sentence : paragraph.getChildren()) {
                if (sentence.getChildren().size() >= minWordCount) {
                    newParagraph.add(sentence);
                }
            }

            if (newParagraph.getChildren().size() > 0) {
                newDocument.add(newParagraph);
            }
        }

        return newDocument;
    }

    private boolean containsWordOfLength(TextComponent sentence, int length) {
        for (TextComponent lexeme : sentence.getChildren()) {
            for (TextComponent word : lexeme.getChildren()) {
                if (word.toString().length() == length) {
                    return true;
                }
            }
        }
        return false;
    }
}