package com.zhigarevich.text.parser.impl;

import com.zhigarevich.text.model.Lexeme;
import com.zhigarevich.text.model.Symbol;
import com.zhigarevich.text.model.TextComponent;
import com.zhigarevich.text.model.Word;
import com.zhigarevich.text.parser.AbstractTextParser;

public class LexemeParser extends AbstractTextParser {
    @Override
    protected String getRegex() {
        return null;
    }

    @Override
    public TextComponent parse(String text) {
        Lexeme lexeme = new Lexeme();
        Word word = new Word();

        for (char c : text.toCharArray()) {
            word.add(new Symbol(c));
        }

        lexeme.add(word);
        return lexeme;
    }
}