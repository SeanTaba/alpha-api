package com.revature.repos;


import com.revature.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsernameAndPassword(String username, String password);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
