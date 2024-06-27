package org.mf.langchain.exception;

public class InvalidStep extends RuntimeException{
    public InvalidStep() {
        super("Invalid step");
    }
}
