package com.zhigarevich.text.model;

import java.util.List;

public interface TextComponent {
    void add(TextComponent component);

    void remove(TextComponent component);

    TextComponent getChild(int index);

    List<TextComponent> getChildren();

    String toString();

    TextPartType getType();

    int count();
}