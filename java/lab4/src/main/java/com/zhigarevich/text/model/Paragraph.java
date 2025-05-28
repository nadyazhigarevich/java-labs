package com.zhigarevich.text.model;

import java.util.ArrayList;
import java.util.List;

public class Paragraph implements TextComponent {
    private List<TextComponent> sentences = new ArrayList<>();
    private static final TextPartType TYPE = TextPartType.PARAGRAPH;

    @Override
    public void add(TextComponent component) {
        sentences.add(component);
    }

    @Override
    public void remove(TextComponent component) {
        sentences.remove(component);
    }

    @Override
    public TextComponent getChild(int index) {
        return sentences.get(index);
    }

    @Override
    public List<TextComponent> getChildren() {
        return new ArrayList<>(sentences);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (TextComponent sentence : sentences) {
            builder.append(sentence.toString()).append(" ");
        }
        return builder.toString().trim();
    }

    @Override
    public TextPartType getType() {
        return TYPE;
    }

    @Override
    public int count() {
        return sentences.size();
    }
}