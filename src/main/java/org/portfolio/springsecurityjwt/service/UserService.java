package org.portfolio.springsecurityjwt.service;

import org.portfolio.springsecurityjwt.model.entities.User;
import org.portfolio.springsecurityjwt.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void create(User user) {
        repository.save(user);
    }

    public User getUserByUsername(String username) {
            return repository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Client not found for username: " + username));
    }

    public List<User> getAll() {
        return repository.findAll();
    }
}
