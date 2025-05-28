package com.zhigarevich.text.repository.impl;

import com.zhigarevich.text.model.*;
import com.zhigarevich.text.repository.TextRepository;
import java.util.*;
import java.util.stream.Collectors;

public class TextRepositoryImpl implements TextRepository {
    private TextComponent textComponent;
    private final Map<String, Integer> wordCountMap = new HashMap<>();

    @Override
    public void saveText(TextComponent textComponent) {
        this.textComponent = textComponent;
        updateWordCountMap();
    }

    @Override
    public TextComponent getText() {
        return textComponent;
    }

    @Override
    public List<TextComponent> getParagraphs() {
        return textComponent.getChildren();
    }

    @Override
    public List<TextComponent> getSentences() {
        return getParagraphs().stream()
                .flatMap(p -> p.getChildren().stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<TextComponent> getLexemes() {
        return getSentences().stream()
                .flatMap(s -> s.getChildren().stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<TextComponent> getWords() {
        return getLexemes().stream()
                .flatMap(l -> l.getChildren().stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<TextComponent> findSentencesWithLongestWord() {
        int maxLength = getWords().stream()
                .mapToInt(w -> w.toString().length())
                .max()
                .orElse(0);

        return getSentences().stream()
                .filter(s -> s.getChildren().stream()
                        .anyMatch(l -> l.getChildren().stream()
                                .anyMatch(w -> w.toString().length() == maxLength)))
                .collect(Collectors.toList());
    }

    @Override
    public void sortParagraphsBySentenceCount() {
        List<TextComponent> paragraphs = new ArrayList<>(getParagraphs());
        paragraphs.sort(Comparator.comparingInt(p -> p.getChildren().size()));

        Document newDocument = new Document();
        paragraphs.forEach(newDocument::add);
        this.textComponent = newDocument;
    }

    @Override
    public void removeSentencesWithWordCountLessThan(int minWordCount) {
        for (TextComponent paragraph : getParagraphs()) {
            List<TextComponent> sentences = paragraph.getChildren().stream()
                    .filter(s -> s.getChildren().size() >= minWordCount)
                    .collect(Collectors.toList());

            Paragraph newParagraph = new Paragraph();
            sentences.forEach(newParagraph::add);
            paragraph.getChildren().clear();
            paragraph.getChildren().addAll(newParagraph.getChildren());
        }
    }

    @Override
    public int countSameWordsIgnoreCase() {
        return (int) wordCountMap.values().stream()
                .filter(count -> count > 1)
                .count();
    }

    @Override
    public Map<String, Integer> getVowelAndConsonantCount() {
        Map<String, Integer> result = new HashMap<>();
        result.put("vowels", 0);
        result.put("consonants", 0);

        getWords().forEach(word -> {
            String wordStr = word.toString().toLowerCase();
            for (char c : wordStr.toCharArray()) {
                if (isVowel(c)) {
                    result.put("vowels", result.get("vowels") + 1);
                } else if (Character.isLetter(c)) {
                    result.put("consonants", result.get("consonants") + 1);
                }
            }
        });

        return result;
    }

    private void updateWordCountMap() {
        wordCountMap.clear();
        getWords().forEach(word -> {
            String wordStr = word.toString().toLowerCase();
            wordCountMap.put(wordStr, wordCountMap.getOrDefault(wordStr, 0) + 1);
        });
    }

    private boolean isVowel(char c) {
        return "aeiouаеёиоуыэюя".indexOf(c) != -1;
    }
}