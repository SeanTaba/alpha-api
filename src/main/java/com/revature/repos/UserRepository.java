package com.revature.repos;


import com.revature.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
    User findUserByUsername(@Param("username") String username);
    User findUserById(@Param("user_id") int id);
}
