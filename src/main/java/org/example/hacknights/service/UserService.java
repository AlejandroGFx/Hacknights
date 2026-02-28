package org.example.hacknights.service;

import lombok.RequiredArgsConstructor;
import org.example.hacknights.dto.UserCreateDTO;
import org.example.hacknights.dto.UserDTO;
import org.example.hacknights.entity.User;
import org.example.hacknights.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> findById(Long id) {
        return userRepository.findById(id).map(this::convertToDTO);
    }

    @Transactional
    public UserDTO create(UserCreateDTO userCreateDTO) {
        User user = User.builder()
                .username(userCreateDTO.username())
                .email(userCreateDTO.email())
                .password(userCreateDTO.password())
                .build();
        return convertToDTO(userRepository.save(user));
    }

    @Transactional
    public Optional<UserDTO> update(Long id, UserCreateDTO userCreateDTO) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userCreateDTO.username());
            user.setEmail(userCreateDTO.email());
            user.setPassword(userCreateDTO.password());
            return convertToDTO(userRepository.save(user));
        });
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail());
    }
}
