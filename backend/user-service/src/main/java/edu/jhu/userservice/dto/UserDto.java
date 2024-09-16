package edu.jhu.userservice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.beans.ConstructorProperties;

@Data
@RequiredArgsConstructor
public class UserDto {

    private Long id;
    private String email;
    private String fullName;

    public UserDto(Long id, String email, String fullName) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
    }


    // Getters and Setters

}
