package com.example.assignment.repository;

import com.example.assignment.entity.User;
import com.example.assignment.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
    Optional<UserDetails> findByUser(User user);
}
