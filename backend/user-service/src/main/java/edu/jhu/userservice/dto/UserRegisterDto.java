package edu.jhu.userservice.dto;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class UserRegisterDto {
    private String email;

    private String password;

    private String fullName;
}
