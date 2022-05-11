package com.jhj.realworld.global.exception;

public class NotExistArticleException extends RuntimeException{
    public NotExistArticleException(String message) {
        super(message);
    }
}