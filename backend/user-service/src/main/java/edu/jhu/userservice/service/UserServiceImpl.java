package edu.jhu.userservice.service;

import edu.jhu.userservice.dto.UserDto;
import edu.jhu.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> allUsers() {
        return
                        userRepository.findAll().stream().map(
                                user ->
                                        new UserDto(user.getId(), user.getEmail(), user.getFullName()
                                        )).toList();


    }

    public UserDto getUserById(Long userId) {
        return userRepository.findById(userId).map(x -> new UserDto(x.getId(), x.getEmail(), x.getFullName())).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserDto getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(x -> new UserDto(x.getId(), x.getEmail(), x.getFullName())).orElseThrow(() -> new RuntimeException("User not found"));
    }
}