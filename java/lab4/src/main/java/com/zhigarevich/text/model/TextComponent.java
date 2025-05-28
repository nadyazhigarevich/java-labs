package com.zhigarevich.text.model;

import java.util.List;

public interface TextComponent {
    void add(TextComponent component);
    void remove(TextComponent component);
    List<TextComponent> getChildren();
    String getText();
    TextPartType getType();
    int count();
}