package com.zhigarevich.lab4.handler;

import com.zhigarevich.lab4.exception.ApplicationException;
import com.zhigarevich.lab4.model.Paragraph;
import com.zhigarevich.lab4.model.Sentence;
import com.zhigarevich.lab4.model.contract.TextComponent;

public class SentenceHandler extends AbstractTextHandler {

    public static final String SENTENCE_REGEX = "(?<=[.!?])\\s+";

    @Override
    public TextComponent handle(String text) throws ApplicationException {
        TextComponent paragraph = new Paragraph(text);
        StringBuilder sb = new StringBuilder();
        String[] sentences = text.split(SENTENCE_REGEX);

        for (var sentence : sentences) {
            if (!sentence.isEmpty()) {
                var sentenceComponent = nextHandler != null
                        ? nextHandler.handle(sentence)
                        : new Sentence(sentence);
                paragraph.add(sentenceComponent);
                sb.append(sentenceComponent.getText());
            }
        }
        return paragraph;
    }
}