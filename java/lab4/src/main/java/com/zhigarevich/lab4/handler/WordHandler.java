package com.zhigarevich.lab4.handler;

import com.zhigarevich.lab4.exception.ApplicationException;
import com.zhigarevich.lab4.model.Sentence;
import com.zhigarevich.lab4.model.contract.TextComponent;
import com.zhigarevich.lab4.model.Word;

public class WordHandler extends AbstractTextHandler {


    public static final String WORD_REGEX = "\\s+";

    @Override
    public TextComponent handle(String text) throws ApplicationException {
        TextComponent sentence = new Sentence(text);
        String[] words = text.split(WORD_REGEX);

        for (String word : words) {
            if (!word.isEmpty()) {
                TextComponent wordComponent = nextHandler != null
                        ? nextHandler.handle(word)
                        : new Word(word);
                sentence.add(wordComponent);
            }
        }
        return sentence;
    }
}
