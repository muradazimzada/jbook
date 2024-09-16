package edu.jhu.userservice.api;

import lombok.Data;

@Data
public class LoginResponse  {
    private String token;

    public LoginResponse (String token, long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }
        private long expiresIn;

}
