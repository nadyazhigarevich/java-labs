package com.zhigarevich.text.parser.impl;

import com.zhigarevich.text.model.Lexeme;
import com.zhigarevich.text.model.Symbol;
import com.zhigarevich.text.model.TextComponent;
import com.zhigarevich.text.parser.AbstractTextParser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceParser extends AbstractTextParser {
    private static final String LEXEME_REGEX = "\\S+";

    @Override
    protected String getRegex() {
        return LEXEME_REGEX;
    }

    @Override
    public TextComponent parse(String text) {
        com.zhigarevich.text.model.Sentence sentence = new com.zhigarevich.text.model.Sentence();
        Pattern pattern = Pattern.compile(getRegex());
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String lexemeText = matcher.group();
            TextComponent lexeme = parseNext(lexemeText);
            if (lexeme != null) {
                sentence.add(lexeme);
            }
        }
        return sentence;
    }
}