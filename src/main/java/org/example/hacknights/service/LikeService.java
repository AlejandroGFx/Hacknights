package org.example.hacknights.service;

import lombok.RequiredArgsConstructor;
import org.example.hacknights.dto.LikeDTO;
import org.example.hacknights.entity.Like;
import org.example.hacknights.entity.Quiz;
import org.example.hacknights.entity.User;
import org.example.hacknights.repository.LikeRepository;
import org.example.hacknights.repository.QuizRepository;
import org.example.hacknights.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;

    @Transactional(readOnly = true)
    public List<LikeDTO> findAll() {
        return likeRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Transactional
    public LikeDTO create(Long userId, Long quizId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        Like like = Like.builder()
                .user(user)
                .quiz(quiz)
                .build();
        
        Like savedLike = likeRepository.save(like);
        
        // Update likesCount in Quiz
        quiz.setLikesCount(quiz.getLikesCount() + 1);
        quizRepository.save(quiz);

        return convertToDTO(savedLike);
    }

    @Transactional
    public void delete(Long id) {
        likeRepository.findById(id).ifPresent(like -> {
            Quiz quiz = like.getQuiz();
            if (quiz != null) {
                quiz.setLikesCount(Math.max(0, quiz.getLikesCount() - 1));
                quizRepository.save(quiz);
            }
            likeRepository.delete(like);
        });
    }

    private LikeDTO convertToDTO(Like like) {
        return new LikeDTO(
                like.getId(),
                like.getVoteDate(),
                like.getUser() != null ? like.getUser().getId() : null,
                like.getQuiz() != null ? like.getQuiz().getId() : null
        );
    }
}
