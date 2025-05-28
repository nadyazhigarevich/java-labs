package com.zhigarevich.text.parser.impl;

import com.zhigarevich.text.model.Paragraph;
import com.zhigarevich.text.model.TextComponent;
import com.zhigarevich.text.parser.AbstractTextParser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParagraphParser extends AbstractTextParser {
    private static final String SENTENCE_REGEX = "[^.!?]+([.!?]|(\\.\\.\\.))";

    @Override
    protected String getRegex() {
        return SENTENCE_REGEX;
    }

    @Override
    public TextComponent parse(String text) {
        Paragraph paragraph = new Paragraph();
        Pattern pattern = Pattern.compile(getRegex());
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String sentenceText = matcher.group().trim();
            TextComponent sentence = parseNext(sentenceText);
            if (sentence != null) {
                paragraph.add(sentence);
            }
        }
        return paragraph;
    }
}