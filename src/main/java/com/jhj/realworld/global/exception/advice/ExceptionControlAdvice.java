package com.jhj.realworld.global.exception.advice;

import com.jhj.realworld.global.exception.*;
import com.jhj.realworld.global.exception.message.ExceptionMessage;
import com.jhj.realworld.global.exception.status.ApiExceptionStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.NoResultException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionControlAdvice {
    @ExceptionHandler(value = {NotExistMemberException.class})
    public ResponseEntity<ApiExceptionStatus> notExistArticleException(NotExistMemberException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ApiExceptionStatus apiExceptionStatus = new ApiExceptionStatus(
                ExceptionMessage.MEMBER_NOT_FOUND,
                httpStatus,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiExceptionStatus, httpStatus);
    }

    @ExceptionHandler(value = {NotExistArticleException.class})
    public ResponseEntity<Object> notExistArticleException(NotExistArticleException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiExceptionStatus apiExceptionStatus = new ApiExceptionStatus(
                ExceptionMessage.ARTICLE_NOT_FOUND,
                httpStatus,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiExceptionStatus, httpStatus);
    }

    @ExceptionHandler(value = {NotExistCommentException.class})
    public ResponseEntity<Object> notExistArticleException(NotExistCommentException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ApiExceptionStatus apiExceptionStatus = new ApiExceptionStatus(
                ExceptionMessage.COMMENT_NOT_FOUND,
                httpStatus,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiExceptionStatus, httpStatus);
    }

    @ExceptionHandler(value = {AlreadyMemberExistException.class})
    public ResponseEntity<Object> notExistArticleException(AlreadyMemberExistException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ApiExceptionStatus apiExceptionStatus = new ApiExceptionStatus(
                ExceptionMessage.ALREADY_MEMBER_EXIST,
                httpStatus,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiExceptionStatus, httpStatus);
    }

    @ExceptionHandler(value = {NoResultException.class})
    public ResponseEntity<Object> notExistArticleException(NoResultException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ApiExceptionStatus apiExceptionStatus = new ApiExceptionStatus(
                ExceptionMessage.NO_RESULT,
                httpStatus,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiExceptionStatus, httpStatus);
    }


}
