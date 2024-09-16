package edu.jhu.bookservice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@RequiredArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String fullName;
}
