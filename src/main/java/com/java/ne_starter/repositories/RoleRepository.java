package com.java.ne_starter.repositories;


import com.java.ne_starter.models.Role;
import com.java.ne_starter.enumerations.user.EUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(EUserRole name);
}