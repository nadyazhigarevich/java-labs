package com.zhigarevich.text.parser.impl;

import com.zhigarevich.text.model.Document;
import com.zhigarevich.text.model.TextComponent;
import com.zhigarevich.text.parser.AbstractTextParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocumentParser extends AbstractTextParser {
    private static final String PARAGRAPH_REGEX = "(?m)^\\s{4}.*$(?:\\n^\\s{4}.*$)*";

    @Override
    protected String getRegex() {
        return PARAGRAPH_REGEX;
    }

    @Override
    public TextComponent parse(String text) {
        Document document = new Document();
        Pattern pattern = Pattern.compile(getRegex());
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String paragraphText = matcher.group().trim();
            TextComponent paragraph = parseNext(paragraphText);
            if (paragraph != null) {
                document.add(paragraph);
            }
        }
        return document;
    }
}