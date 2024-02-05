package com.taskmanagement.app.exception;
 
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.security.core.AuthenticationException;
import com.taskmanagement.app.dto.ErrorDTO;
 
@ControllerAdvice
public class GlobalExceptionHandler {
 
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorDTO handleException(ResourceNotFoundException ex) {
        return new ErrorDTO(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public @ResponseBody ErrorDTO handleException(UsernameNotFoundException ex) {
        return new ErrorDTO(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public @ResponseBody ErrorDTO handleException(AuthenticationException ex) {
        return new ErrorDTO(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    }

    @ExceptionHandler(ApplicationException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public @ResponseBody ErrorDTO handleException(ApplicationException ex) {
        return new ErrorDTO(HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorDTO handleException(BadRequestException ex) {
        return new ErrorDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorDTO handleException(Exception ex) {
        return new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

}