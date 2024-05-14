package org.okten.carservice.mapper;

import lombok.RequiredArgsConstructor;
import org.okten.carservice.dto.auth.SignUpRequestDto;
import org.okten.carservice.dto.user.CreateUserRequest;
import org.okten.carservice.dto.user.UserDto;
import org.okten.carservice.dto.user.UpdateUserRequest;
import org.okten.carservice.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User mapToUserEntity(CreateUserRequest createUserRequest) {
        return User.builder()
                .username(createUserRequest.getUsername())
                .email(createUserRequest.getEmail())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .role(createUserRequest.getRole())
                .build();
    }

    public User mapToUserEntity(SignUpRequestDto signUpRequestDto) {
        return User.builder()
                .username(signUpRequestDto.getUsername())
                .email(signUpRequestDto.getEmail())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .role(signUpRequestDto.getRole())
                .build();
    }

    public UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public User updateUser(User user, UpdateUserRequest updateUserRequest) {
        if (updateUserRequest.getUsername() != null) {
            user.setUsername(updateUserRequest.getUsername());
        }

        if (updateUserRequest.getEmail() != null) {
            user.setEmail(updateUserRequest.getEmail());
        }

        if (updateUserRequest.getRole() != null) {
            user.setRole(updateUserRequest.getRole());
        }

        if (updateUserRequest.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
        }

        return user;
    }
}
