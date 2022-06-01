package com.study.realworld.global.exception;

public class NotExistCommentException extends RuntimeException{
    public NotExistCommentException(String message) {
        super(message);
    }

    public NotExistCommentException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotExistCommentException(Throwable cause) {
        super(cause);
    }
}
