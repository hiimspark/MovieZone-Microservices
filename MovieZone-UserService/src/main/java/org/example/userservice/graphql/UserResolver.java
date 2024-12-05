package org.example.userservice.graphql;

import lombok.AllArgsConstructor;
import org.example.userservice.dto.UserDTO;
import org.example.userservice.entity.UserEntity;
import org.example.userservice.service.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor
public class UserResolver {
    private final UserService userService;

    @QueryMapping
    public List<UserEntity> users() {
        return userService.readAll();
    }

    @QueryMapping
    public UserEntity user(@Argument Long id) {
        return userService.findById(id).orElse(null);
    }

    @MutationMapping
    public UserEntity createUser(@Argument UserDTO input) {
        UserDTO dto = new UserDTO(input.getName(), input.getPassword(), input.getRoles());
        return userService.addUser(dto);
    }

    @MutationMapping
    public Boolean deleteUser(Long id) {
        userService.delete(id);
        return true;
    }
}
