package hu.flowacademy.netbank.controller;

import hu.flowacademy.netbank.model.Role;
import hu.flowacademy.netbank.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody User user) {
        log.debug("Creating user with params: {}", user);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable String id, @RequestBody User user) {
        log.debug("Updating user with id and params: {}, {}", id, user);

        return user.toBuilder().id(id).role(Role.USER).build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        log.debug("Deleting user with id: {}", id);
    }

    @GetMapping
    public List<User> findAll() {
        log.debug("findAll called");
        return List.of(
                User.builder()
                        .id(UUID.randomUUID().toString())
                        .email("asd@bsd.com")
                        .fullName("User One")
                        .role(Role.USER).build(),
                User.builder()
                        .id(UUID.randomUUID().toString())
                        .email("asd@example.com")
                        .fullName("User Two")
                        .role(Role.USER).build()
        );
    }

    @GetMapping("/{id}")
    public Optional<User> findOne(@PathVariable String id) {
        log.debug("Getting user with ID: {}", id);
        return Optional.of(User.builder()
                .id(id)
                .email("asd@bsd.com")
                .fullName("User One")
                .role(Role.USER).build());
    }

}
