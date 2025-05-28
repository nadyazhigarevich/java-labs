package com.zhigarevich.text.parser.impl;

import com.zhigarevich.text.model.Symbol;
import com.zhigarevich.text.model.TextComponent;
import com.zhigarevich.text.model.Word;
import com.zhigarevich.text.parser.AbstractTextParser;

public class WordParser extends AbstractTextParser {
    @Override
    protected String getRegex() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TextComponent parse(String text) {
        Word word = new Word();
        for (char c : text.toCharArray()) {
            word.add(new Symbol(c));
        }
        return word;
    }
}