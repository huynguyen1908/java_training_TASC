package com.example.User.Service.repository;

import com.example.User.Service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}
