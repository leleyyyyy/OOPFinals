package com.CareNet.CN.repository;

import com.CareNet.CN.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = ?1")
    User findByUsername(String username);
    List<User> findByRole(String rolePatient);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}