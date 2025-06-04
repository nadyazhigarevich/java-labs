package com.zhigarevich.lab4.model.contract;

import java.util.List;

public interface TextComponent {

    String getText();

    void add(TextComponent component);

    List<TextComponent> getChildren();
}