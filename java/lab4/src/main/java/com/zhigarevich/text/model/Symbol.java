package com.zhigarevich.text.model;

import java.util.Collections;
import java.util.List;

public class Symbol implements TextComponent {
    private char symbol;
    private static final TextPartType TYPE = TextPartType.SYMBOL;

    public Symbol(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public void add(TextComponent component) {
        throw new UnsupportedOperationException("Cannot add to a Symbol");
    }

    @Override
    public void remove(TextComponent component) {
        throw new UnsupportedOperationException("Cannot remove from a Symbol");
    }

    @Override
    public TextComponent getChild(int index) {
        throw new UnsupportedOperationException("Symbol has no children");
    }

    @Override
    public List<TextComponent> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }

    @Override
    public TextPartType getType() {
        return TYPE;
    }

    @Override
    public int count() {
        return 1;
    }

    public char getSymbol() {
        return symbol;
    }
}