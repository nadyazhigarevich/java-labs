package com.zhigarevich.text.parser;

import com.zhigarevich.text.model.TextComponent;

public abstract class AbstractTextParser implements Parser {
    protected Parser nextParser;

    @Override
    public void setNextParser(Parser nextParser) {
        this.nextParser = nextParser;
    }

    protected abstract String getRegex();

    protected TextComponent parseNext(String text) {
        if (nextParser != null) {
            return nextParser.parse(text);
        }
        return null;
    }
}