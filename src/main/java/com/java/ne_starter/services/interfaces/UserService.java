package com.java.ne_starter.services.interfaces;

import com.java.ne_starter.dtos.user.CreateUserDto;
import com.java.ne_starter.models.User;
import org.springframework.http.ResponseEntity;


public interface UserService {
    ResponseEntity<?> createUser(CreateUserDto dto);
    User findUserById(Long id);
}

