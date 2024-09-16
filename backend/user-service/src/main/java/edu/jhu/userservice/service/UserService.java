package edu.jhu.userservice.service;

import edu.jhu.userservice.dto.UserDto;

import java.util.List;

public interface UserService {
     List<UserDto> allUsers();
     UserDto getUserById(Long userId);
     UserDto getUserByEmail(String email);
}
