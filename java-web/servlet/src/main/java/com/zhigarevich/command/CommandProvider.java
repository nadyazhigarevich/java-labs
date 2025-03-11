package com.zhigarevich.command;

public class CommandProvider {

    public static Command defineCommand(final String action) {
        Command command;
        try {
            var type = CommandType.valueOf(action.toUpperCase());
            command = type.getCommand();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
        return command;
    }
}