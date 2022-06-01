package com.study.realworld.global.exception.status;

import com.study.realworld.global.exception.message.ExceptionMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ApiExceptionStatus {
    private final ExceptionMessage message;
    private final HttpStatus status;
    private final LocalDateTime timestamp;
}
