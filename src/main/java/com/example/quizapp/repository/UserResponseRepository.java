package com.example.quizapp.repository;

import com.example.quizapp.model.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserResponseRepository extends JpaRepository<UserResponse, Long> {
    List<UserResponse> findByQuizSessionId(Long quizSessionId);
    
    @Query("SELECT COUNT(ur) FROM UserResponse ur WHERE ur.quizSession.id = ?1 AND ur.question.id = ?2")
    long countByQuizSessionIdAndQuestionId(Long quizSessionId, Long questionId);
}