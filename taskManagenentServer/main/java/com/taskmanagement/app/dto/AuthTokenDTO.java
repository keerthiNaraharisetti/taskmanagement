package com.taskmanagement.app.dto;

import lombok.Data;

@Data
public class AuthTokenDTO {
    private String token;

    public AuthTokenDTO(String token){
        this.token = token;
    }
}