package com.example.bbang0.adapter.controller;

import com.example.bbang0.domain.exception.BaseException;
import com.example.bbang0.domain.exception.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

import static com.example.bbang0.domain.exception.BaseResponseStatus.EXIST_EMAIL;

@Component
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {BaseException.class})
    public BaseResponse<String> testException(BaseException e) {
        GlobalExceptionHandler.log.error("error message", e);
        return new BaseResponse<>(e.getStatus());
    }

}
