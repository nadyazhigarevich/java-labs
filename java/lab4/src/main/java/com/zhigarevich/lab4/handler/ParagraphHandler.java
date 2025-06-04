package com.zhigarevich.lab4.handler;

import com.zhigarevich.lab4.exception.ApplicationException;
import com.zhigarevich.lab4.model.Document;
import com.zhigarevich.lab4.model.Paragraph;
import com.zhigarevich.lab4.model.contract.TextComponent;

public class ParagraphHandler extends AbstractTextHandler {

    public static final String PARAGRAPH_REGEX = "\\n\\s*\\n";

    @Override
    public TextComponent handle(String text) throws ApplicationException {
        String[] paragraphs = text.split(PARAGRAPH_REGEX);
        TextComponent document = new Document(text);

        for (var paragraph : paragraphs) {
            if (!paragraph.trim().isEmpty()) {
                var paragraphComponent = nextHandler != null
                        ? nextHandler.handle(paragraph)
                        : new Paragraph(paragraph);
                document.add(paragraphComponent);
            }
        }

        return document;
    }
}