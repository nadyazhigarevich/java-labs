package com.zhigarevich.text.model;

import java.util.ArrayList;
import java.util.List;

public class Document implements TextComponent {
    private List<TextComponent> paragraphs = new ArrayList<>();
    private static final TextPartType TYPE = TextPartType.DOCUMENT;

    @Override
    public void add(TextComponent component) {
        paragraphs.add(component);
    }

    @Override
    public void remove(TextComponent component) {
        paragraphs.remove(component);
    }

    @Override
    public TextComponent getChild(int index) {
        return paragraphs.get(index);
    }

    @Override
    public List<TextComponent> getChildren() {
        return new ArrayList<>(paragraphs);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (TextComponent paragraph : paragraphs) {
            builder.append(paragraph.toString()).append("\n");
        }
        return builder.toString().trim();
    }

    @Override
    public TextPartType getType() {
        return TYPE;
    }

    @Override
    public int count() {
        return paragraphs.size();
    }
}