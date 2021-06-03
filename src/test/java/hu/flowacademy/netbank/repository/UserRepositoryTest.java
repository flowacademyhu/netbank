package hu.flowacademy.netbank.repository;

import hu.flowacademy.netbank.model.Role;
import hu.flowacademy.netbank.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testUserSave() {
        userRepository.save(User.builder().email("asd@bsd.com")
                .fullName("name")
                .password("hash")
                .role(Role.USER)
                .build());

        User user = entityManager.createQuery("select u from User u where u.email=?0", User.class)
                .setMaxResults(1)
                .setParameter(0, "asd@bsd.com")
                .getSingleResult();

        assertNotNull(user);
    }

    // TODO implement this negative case
//    @Test
//    public void testUserEmailUnique() {
//        User user = User.builder().email("asd@bsd.com")
//                .fullName("name")
//                .password("hash")
//                .role(Role.USER)
//                .build();
//
//        userRepository.save(user.toBuilder().build());
//        userRepository.save(user.toBuilder().build());
//
////        assertThrows(Exception.class, () -> {
////        });
//    }

}