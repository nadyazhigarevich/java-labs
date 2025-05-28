package com.zhigarevich.text.parser.impl;

import com.zhigarevich.text.model.Paragraph;
import com.zhigarevich.text.model.TextComponent;
import com.zhigarevich.text.parser.AbstractTextParser;

public class ParagraphParser extends AbstractTextParser {
    private static final String SENTENCE_REGEX = "(?<=[.!?])\\s+(?=[A-ZА-Я])";

    @Override
    protected String getRegex() {
        return SENTENCE_REGEX;
    }

    @Override
    public TextComponent parse(String text) {
        Paragraph paragraph = new Paragraph();
        String[] sentences = text.split(getRegex());

        for (String sentenceText : sentences) {
            sentenceText = sentenceText.trim();
            if (!sentenceText.isEmpty()) {
                // Добавляем точку, если предложение заканчивается на букву
                if (Character.isLetter(sentenceText.charAt(sentenceText.length()-1))) {
                    sentenceText += ".";
                }
                TextComponent sentence = parseNext(sentenceText);
                if (sentence != null) {
                    paragraph.add(sentence);
                }
            }
        }
        return paragraph;
    }
}