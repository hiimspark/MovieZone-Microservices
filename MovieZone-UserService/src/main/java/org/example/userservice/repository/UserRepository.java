package org.example.userservice.repository;

import org.example.userservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByName(String username);
    boolean existsByName(String name);
}