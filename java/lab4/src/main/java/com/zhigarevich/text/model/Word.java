package com.zhigarevich.text.model;

import java.util.ArrayList;
import java.util.List;

public class Word implements TextComponent {
    private List<TextComponent> symbols = new ArrayList<>();
    private static final TextPartType TYPE = TextPartType.WORD;

    @Override
    public void add(TextComponent component) {
        symbols.add(component);
    }

    @Override
    public String toStructuredString() {
        return this.toString();
    }

    @Override
    public void remove(TextComponent component) {
        symbols.remove(component);
    }

    @Override
    public TextComponent getChild(int index) {
        return symbols.get(index);
    }

    @Override
    public List<TextComponent> getChildren() {
        return new ArrayList<>(symbols);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (TextComponent symbol : symbols) {
            builder.append(symbol.toString());
        }
        return builder.toString();
    }

    @Override
    public TextPartType getType() {
        return TYPE;
    }

    @Override
    public int count() {
        return symbols.size();
    }
}