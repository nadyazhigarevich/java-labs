package com.zhigarevich.text.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        return sentences.stream()
                .map(TextComponent::toString)
                .collect(Collectors.joining(" "));
    }

    @Override
    public String toStructuredString() {
        return sentences.stream()
                .map(TextComponent::toStructuredString)
                .collect(Collectors.joining(" "));
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