package com.zhigarevich.lab4.handler;

import com.zhigarevich.lab4.model.Letter;
import com.zhigarevich.lab4.model.contract.TextComponent;
import com.zhigarevich.lab4.model.Word;

public class LetterHandler extends AbstractTextHandler {

    @Override
    public TextComponent handle(String text) {
        TextComponent word = new Word(text);

        for (char c : text.toCharArray()) {
            word.add(new Letter(String.valueOf(c)));
        }

        return word;
    }
}