package com.zhigarevich.text.model;

import java.util.ArrayList;
import java.util.List;

public class Document implements TextComponent {
    private final List<TextComponent> paragraphs = new ArrayList<>();

    @Override
    public void add(TextComponent component) {
        paragraphs.add(component);
    }

    @Override
    public void remove(TextComponent component) {
        paragraphs.remove(component);
    }

    @Override
    public List<TextComponent> getChildren() {
        return new ArrayList<>(paragraphs);
    }

    @Override
    public String getText() {
        StringBuilder builder = new StringBuilder();
        for (TextComponent paragraph : paragraphs) {
            builder.append(paragraph.getText()).append("\n\n");
        }
        return builder.toString().trim();
    }

    @Override
    public TextPartType getType() {
        return TextPartType.DOCUMENT;
    }

    @Override
    public int count() {
        return paragraphs.size();
    }
}