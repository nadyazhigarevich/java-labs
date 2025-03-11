package com.zhigarevich.command;

import com.zhigarevich.command.impl.FindAllPhonesCommand;

public enum CommandType {

    FIND_ALL_PHONES(new FindAllPhonesCommand());

    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return this.command;
    }
}
