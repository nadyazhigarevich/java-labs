package com.zhigarevich.text.service;

import com.zhigarevich.text.model.TextComponent;
import java.util.List;

public interface ParagraphService {
    List<TextComponent> getParagraphs(TextComponent text);
    TextComponent sortParagraphsBySentenceCount(TextComponent text);
}