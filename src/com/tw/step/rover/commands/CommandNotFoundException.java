package com.tw.step.rover.commands;

public class CommandNotFoundException extends RuntimeException {

    public CommandNotFoundException(String message) {
        super(message);
    }

}
