package hu.flowacademy.netbank.service;

import hu.flowacademy.netbank.model.Role;
import hu.flowacademy.netbank.model.User;
import hu.flowacademy.netbank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User user) {
        // TODO validate user
        return userRepository.save(
                user.toBuilder()
                        .role(Role.USER)
                        .build()
        );
    }

    public User update(String id, User user) {
        // TODO validate user
        return userRepository.save(
                user.toBuilder()
                        .id(id)
                        .build()
        );
    }

    public void delete(String id) {
        userRepository.deleteById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findOne(String id) {
        return userRepository.findById(id);
    }
}
