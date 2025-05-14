package com.java.ne_starter.exceptions;


import lombok.Getter;
import org.zalando.problem.AbstractThrowableProblem;

@Getter
public class CustomException extends AbstractThrowableProblem {

    private final Exception exception;

    public CustomException(Exception exception) {
        this.exception = exception;
    }
}

