package edu.jhu.userservice.api;
import jakarta.validation.Valid;
import lombok.Data;

@Data
public class LoginRequest {
    @Valid()
    private String email    ;
    @Valid()
    private String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}