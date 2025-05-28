package com.zhigarevich.text.model;

import java.util.ArrayList;
import java.util.List;

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
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (TextComponent lexeme : lexemes) {
            builder.append(lexeme.toString()).append(" ");
        }
        String sentence = builder.toString().trim();
        return sentence.substring(0, 1).toUpperCase() + sentence.substring(1);
    }

    @Override
    public TextPartType getType() {
        return TYPE;
    }

    @Override
    public int count() {
        return lexemes.size();
    }
}