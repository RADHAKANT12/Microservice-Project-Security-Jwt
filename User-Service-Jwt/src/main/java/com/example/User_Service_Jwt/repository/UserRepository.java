package com.example.User_Service_Jwt.repository;

import com.example.User_Service_Jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByName(String username);

}
