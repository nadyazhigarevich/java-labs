package com.zhigarevich.text.parser;

import com.zhigarevich.text.model.TextComponent;

public interface Parser {
    TextComponent parse(String text);

    void setNextParser(Parser nextParser);
}