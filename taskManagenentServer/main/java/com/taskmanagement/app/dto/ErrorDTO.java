package com.taskmanagement.app.dto;
 
import lombok.Data;
 
@Data
public class ErrorDTO {

    private int statusCode;
    private String message;
 
    public ErrorDTO(String message){
        super();
        this.statusCode = 500;
        this.message = message;
    }
    public ErrorDTO(int statusCode, String message){
        super();
        this.statusCode = statusCode;
        this.message = message;
    }
}