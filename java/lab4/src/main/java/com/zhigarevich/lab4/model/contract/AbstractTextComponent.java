package com.zhigarevich.lab4.model.contract;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractTextComponent implements TextComponent {

    protected List<TextComponent> children = new ArrayList<>();
    protected String text;

    public AbstractTextComponent(String text) {
        this.text = text;
    }

    @Override
    public void add(TextComponent component) {
        children.add(component);
    }

    @Override
    public List<TextComponent> getChildren() {
        return children;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        var result = new StringBuilder();
        for (var c : this.children) {
            result.append(c.getText());
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        AbstractTextComponent that = (AbstractTextComponent) o;
        return Objects.equals(children, that.children) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(children);
        result = 31 * result + Objects.hashCode(text);
        return result;
    }
}