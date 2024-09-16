package edu.jhu.userservice.service;

import edu.jhu.userservice.api.LoginRequest;
import edu.jhu.userservice.dto.UserDto;
import edu.jhu.userservice.dto.UserRegisterDto;

public interface AuthenticationService {
    void signup(UserRegisterDto input);
    UserDto authenticate(LoginRequest input);

}
