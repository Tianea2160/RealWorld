package com.study.realworld.global.exception;

public class NotExistArticleException extends RuntimeException{
    public NotExistArticleException(String message) {
        super(message);
    }
}