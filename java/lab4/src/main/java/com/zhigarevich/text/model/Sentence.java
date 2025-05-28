package com.zhigarevich.text.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Sentence implements TextComponent {
    private List<TextComponent> lexemes = new ArrayList<>();
    private static final TextPartType TYPE = TextPartType.SENTENCE;

    @Override
    public void add(TextComponent component) {
        lexemes.add(component);
    }

    @Override
    public void remove(TextComponent component) {
        lexemes.remove(component);
    }

    @Override
    public TextComponent getChild(int index) {
        return lexemes.get(index);
    }

    @Override
    public List<TextComponent> getChildren() {
        return new ArrayList<>(lexemes);
    }

    @Override
    public TextPartType getType() {
        return TYPE;
    }

    @Override
    public String toString() {
        String sentence = lexemes.stream()
                .map(TextComponent::toString)
                .collect(Collectors.joining(""));
        return sentence.substring(0, 1).toUpperCase() + sentence.substring(1);
    }

    @Override
    public String toStructuredString() {
        return this.toString();
    }
    @Override
    public int count() {
        return lexemes.size();
    }
}