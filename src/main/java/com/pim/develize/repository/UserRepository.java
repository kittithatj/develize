package com.pim.develize.repository;

import com.pim.develize.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    public Optional<User> findByUsername(String username);

    public List<User> findByRole(String role);

    public List<User> findAll();

}
