package com.cognologix.bankapplication.repositories;

import com.cognologix.bankapplication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByIdEquals(Integer customerId);
}
