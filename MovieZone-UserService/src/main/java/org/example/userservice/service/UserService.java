package org.example.userservice.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.example.userservice.dto.UserDTO;
import org.example.userservice.entity.UserEntity;
import org.example.userservice.exceptions.UsernameAlreadyExistsException;
import org.example.userservice.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String SECRET_KEY = "secret-key";

    public UserEntity addUser(UserDTO dto){
        if (userRepository.existsByName(dto.getName())) {
            throw new UsernameAlreadyExistsException("Username is already taken.");
        }

        UserEntity user = UserEntity.builder()
                .name(dto.getName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(dto.getRoles())
                .build();
        return userRepository.save(user);
    }

    public List<UserEntity> readAll() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> findById(Long id){ return userRepository.findById(id);}

    public Optional<UserEntity> findByName(String name) { return userRepository.findByName(name);}

    public UserEntity update(UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity changeName(Principal principal, String username){
        if (userRepository.existsByName(username)) {
            throw new UsernameAlreadyExistsException("Username is already taken.");
        }
        getUserByPrincipal(principal).setName(username);
        return userRepository.save(getUserByPrincipal(principal));

    }

    public UserEntity changePass(Principal principal, String password){
        getUserByPrincipal(principal).setPassword(passwordEncoder.encode(password));
        return userRepository.save(getUserByPrincipal(principal));

    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public UserEntity getUserByPrincipal(Principal principal) {
        if (principal == null) return null;
        return userRepository.findByName(principal.getName()).get();
    }

    public boolean authenticate(String username, String password) {
        Optional<UserEntity> user = userRepository.findByName(username);
        return user.filter(userEntity -> passwordEncoder.matches(password, userEntity.getPassword())).isPresent();
    }

    public String generateToken(Authentication authentication) {
        return Jwts.builder()
                .subject(authentication.getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}

