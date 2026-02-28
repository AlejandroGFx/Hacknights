package org.example.hacknights.repository;

import org.example.hacknights.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByUserId(Long userId);
    List<Like> findByQuizId(Long quizId);
    Optional<Like> findByUserIdAndQuizId(Long userId, Long quizId);
}
