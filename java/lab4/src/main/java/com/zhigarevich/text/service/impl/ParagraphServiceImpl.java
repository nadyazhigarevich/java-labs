package com.zhigarevich.text.service.impl;

import com.zhigarevich.text.model.Document;
import com.zhigarevich.text.model.TextComponent;
import com.zhigarevich.text.service.ParagraphService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ParagraphServiceImpl implements ParagraphService {
    @Override
    public List<TextComponent> getParagraphs(TextComponent text) {
        return text.getChildren();
    }

    @Override
    public TextComponent sortParagraphsBySentenceCount(TextComponent text) {
        List<TextComponent> paragraphs = new ArrayList<>(getParagraphs(text));
        paragraphs.sort(Comparator.comparingInt(p -> p.getChildren().size()));

        Document sortedDocument = new Document();
        paragraphs.forEach(sortedDocument::add);
        return sortedDocument;
    }
}