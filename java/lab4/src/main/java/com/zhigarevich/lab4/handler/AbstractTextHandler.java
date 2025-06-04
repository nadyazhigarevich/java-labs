package com.zhigarevich.lab4.handler;

import com.zhigarevich.lab4.exception.ApplicationException;
import com.zhigarevich.lab4.model.contract.TextComponent;

public abstract class AbstractTextHandler {
    protected AbstractTextHandler nextHandler;

    public void setNextHandler(AbstractTextHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract TextComponent handle(String text) throws ApplicationException;
}