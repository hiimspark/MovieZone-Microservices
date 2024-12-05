package org.example.watchlistservice.service;
import lombok.AllArgsConstructor;
import org.example.watchlistservice.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class UserServiceClient {

    @Autowired
    private final RestTemplate restTemplate;

    public UserDTO getUserById(Long userId) {
        return restTemplate.getForObject("http://user-service:8081/users/{userId}", UserDTO.class, userId);
    }

    public UserDTO getUserByName(String name){
        return restTemplate.getForObject("http://user-service:8081/users/name?name={name}", UserDTO.class, name);
    }
}
