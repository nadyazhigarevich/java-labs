package com.zhigarevich.text.model;

import java.util.ArrayList;
import java.util.List;

public class Lexeme implements TextComponent {
    private List<TextComponent> words = new ArrayList<>();
    private static final TextPartType TYPE = TextPartType.LEXEME;

    @Override
    public void add(TextComponent component) {
        words.add(component);
    }

    @Override
    public void remove(TextComponent component) {
        words.remove(component);
    }

    @Override
    public TextComponent getChild(int index) {
        return words.get(index);
    }

    @Override
    public List<TextComponent> getChildren() {
        return new ArrayList<>(words);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (TextComponent word : words) {
            builder.append(word.toString());
        }
        return builder.toString();
    }

    @Override
    public TextPartType getType() {
        return TYPE;
    }

    @Override
    public String toStructuredString() {
        return this.toString();
    }

    @Override
    public int count() {
        return words.size();
    }
}