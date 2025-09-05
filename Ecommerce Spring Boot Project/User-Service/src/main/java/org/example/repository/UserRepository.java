package org.example.repository;


import org.example.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
    User findByPhoneNumber(String phoneNumber);
    Optional<User> findById(String userId);

    @Query(value = "SELECT u from User u order by u.createdAt")
    Page<User> findAll(Pageable pageable);
    boolean existsByEmail(String email);
}
