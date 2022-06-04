package com.ceydog.mobileactionbootcamp.repository;

import com.ceydog.mobileactionbootcamp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //entity type: User
    //id type: Long
}
