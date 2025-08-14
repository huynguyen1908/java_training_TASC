package org.example.repository;


import org.example.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
    User findByPhoneNumber(String phoneNumber);
    Optional<User> findById(String userId);
    Page<User> findAll(Pageable pageable);
    boolean existsByEmail(String email);
}
