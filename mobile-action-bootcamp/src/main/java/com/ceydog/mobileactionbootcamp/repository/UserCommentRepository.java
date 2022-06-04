package com.ceydog.mobileactionbootcamp.repository;

import com.ceydog.mobileactionbootcamp.entity.UserComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCommentRepository extends JpaRepository<UserComment, Long> {
    //entity type: UserComment
    //id type: Long
}
