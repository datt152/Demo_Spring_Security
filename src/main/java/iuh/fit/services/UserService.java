package iuh.fit.services;

import iuh.fit.model.User;
import iuh.fit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


}
