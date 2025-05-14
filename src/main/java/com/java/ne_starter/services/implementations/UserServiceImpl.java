package com.java.ne_starter.services.implementations;

import com.java.ne_starter.dtos.user.CreateUserDto;
import com.java.ne_starter.dtos.user.UserResponseDto;
import com.java.ne_starter.enumerations.user.EUserRole;
import com.java.ne_starter.exceptions.ConflictException;
import com.java.ne_starter.models.Role;
import com.java.ne_starter.models.User;
import com.java.ne_starter.repositories.RoleRepository;
import com.java.ne_starter.repositories.UserRepository;
import com.java.ne_starter.services.interfaces.UserService;
import com.java.ne_starter.utils.HashUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public ResponseEntity<?> createUser(CreateUserDto dto) {
        // Check if email already exists BEFORE creating the user
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ConflictException("Email already exists");
        }

        // Create and populate the User entity
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setNationalId(dto.getNationalId());
        user.setPassword(HashUtil.hashPassword(dto.getPassword()));

        // Set role
        EUserRole requestedRole = EUserRole.valueOf(dto.getRole());
        Role selectedRole = roleRepository.findByName(requestedRole)
                .orElseThrow(() -> new RuntimeException("Requested role not found"));

        Set<Role> roles = new HashSet<>();
        roles.add(selectedRole);
        user.setRoles(roles);

        // Save the user
        userRepository.save(user);

        // Prepare response
        UserResponseDto response = new UserResponseDto();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setUsername(user.getUsername());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setNationalId(user.getNationalId());
        response.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }
}
