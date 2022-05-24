package com.ensemble.sample.moviesapi.exceptionhandler;


import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class MovieExceptionHandler extends ResponseEntityExceptionHandler {

    private MessageSource messageSource;

    public MovieExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String userMessage = messageSource.getMessage("message.invalid", null, LocaleContextHolder.getLocale());
        String supportMessage = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
        List<Error> errors = Arrays.asList(new Error(userMessage, supportMessage));
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Error> errors = createListError(ex.getBindingResult());
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleEmptyResultDataAccessException(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        String userMessage = messageSource.getMessage("resource.not_found", null, LocaleContextHolder.getLocale());
        String supportMessage = ex.toString();
        List<Error> errors = Arrays.asList(new Error(userMessage, supportMessage));
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    private List<Error> createListError(BindingResult bindingResult){
        List<Error> errors = new ArrayList<>();
        for (FieldError fieldError: bindingResult.getFieldErrors()) {
            String userMessage = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String supportMessage = fieldError.toString();
            errors.add(new Error(userMessage, supportMessage));
        }
        return errors;
    }

    public static class Error {

        private String userMessage;
        private String supportMessage;

        public Error(String userMessage, String supportMessage) {
            this.userMessage = userMessage;
            this.supportMessage = supportMessage;
        }

        public String getSupportMessage() {
            return supportMessage;
        }

        public String getUserMessage() {
            return userMessage;
        }
    }
}
