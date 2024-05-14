package org.okten.carservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.okten.carservice.dto.auth.SignUpRequestDto;
import org.okten.carservice.dto.auth.SignUpResponseDto;
import org.okten.carservice.dto.user.CreateUserRequest;
import org.okten.carservice.dto.user.UpdateUserRequest;
import org.okten.carservice.dto.user.UserDto;
import org.okten.carservice.entity.User;
import org.okten.carservice.exception.UserAlreadyExistsException;
import org.okten.carservice.exception.UserCannotBeDeletedException;
import org.okten.carservice.mapper.UserMapper;
import org.okten.carservice.repository.CarRepository;
import org.okten.carservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final CarRepository carRepository;

    private final UserMapper userMapper;

    public List<UserDto> findUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::mapToUserDto)
                .toList();
    }

    public Optional<UserDto> findUser(Long id) {
        return userRepository
                .findById(id)
                .map(userMapper::mapToUserDto);
    }

    @Transactional
    public UserDto createUser(CreateUserRequest createUserRequest) {
        userRepository
                .findByUsername(createUserRequest.getUsername())
                .ifPresent((user) -> {
                    throw new UserAlreadyExistsException("User '%s' already exists".formatted(createUserRequest.getUsername()));
                });
        userRepository
                .findByEmail(createUserRequest.getEmail())
                .ifPresent((user) -> {
                    throw new UserAlreadyExistsException("User with email '%s' already exists".formatted(createUserRequest.getEmail()));
                });

        User user = userMapper.mapToUserEntity(createUserRequest);
        User savedUser = userRepository.save(user);
        return userMapper.mapToUserDto(savedUser);
    }

    @Transactional
    public UserDto updateUser(Long userId, UpdateUserRequest updateUserRequest) {
        if (updateUserRequest.getUsername() != null) {
            userRepository
                    .findByUsername(updateUserRequest.getUsername())
                    .ifPresent((user) -> {
                        throw new UserAlreadyExistsException("User '%s' already exists".formatted(updateUserRequest.getUsername()));
                    });
        }

        if (updateUserRequest.getEmail() != null) {
            userRepository
                    .findByEmail(updateUserRequest.getEmail())
                    .ifPresent((user) -> {
                        throw new UserAlreadyExistsException("User with email '%s' already exists".formatted(updateUserRequest.getEmail()));
                    });
        }

        return userRepository
                .findById(userId)
                .map(user -> userMapper.updateUser(user, updateUserRequest))
                .map(userMapper::mapToUserDto)
                .orElseThrow(() -> new NoSuchElementException("Owner '%s' does not exist".formatted(userId)));
    }

    public void deleteUser(Long id) {
        if (carRepository.existsByOwnerId(id)) {
            throw new UserCannotBeDeletedException("User '%s' has cars and can not be deleted".formatted(id));
        }

        userRepository.deleteById(id);
    }

    @Transactional
    public SignUpResponseDto createUser(SignUpRequestDto signUpRequestDto) {
        userRepository
                .findByUsername(signUpRequestDto.getUsername())
                .ifPresent((user) -> {
                    throw new UserAlreadyExistsException("User '%s' already exists".formatted(signUpRequestDto.getUsername()));
                });
        userRepository
                .findByEmail(signUpRequestDto.getEmail())
                .ifPresent((user) -> {
                    throw new UserAlreadyExistsException("User with email '%s' already exists".formatted(signUpRequestDto.getEmail()));
                });

        User user = userMapper.mapToUserEntity(signUpRequestDto);
        User savedUser = userRepository.save(user);

        return SignUpResponseDto.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '%s' not found".formatted(username)));
    }
}
