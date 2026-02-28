package org.example.hacknights.service;

import lombok.RequiredArgsConstructor;
import org.example.hacknights.dto.*;
import org.example.hacknights.entity.*;
import org.example.hacknights.repository.QuizRepository;
import org.example.hacknights.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<QuizDTO> findAll() {
        return quizRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<QuizDTO> findById(Long id) {
        return quizRepository.findById(id).map(this::convertToDTO);
    }

    @Transactional
    public QuizDTO create(QuizCreateDTO quizCreateDTO) {
        User user = userRepository.findById(quizCreateDTO.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Quiz quiz = Quiz.builder()
                .theme(quizCreateDTO.theme())
                .user(user)
                .build();

        if (quizCreateDTO.questions() != null) {
            quizCreateDTO.questions().forEach(qDto -> {
                Question question = Question.builder()
                        .questionText(qDto.questionText())
                        .quiz(quiz)
                        .build();
                
                if (qDto.options() != null) {
                    qDto.options().forEach(oDto -> {
                        Option option = Option.builder()
                                .optionText(oDto.optionText())
                                .isCorrect(oDto.isCorrect())
                                .question(question)
                                .build();
                        question.addOption(option);
                    });
                }
                quiz.addQuestion(question);
            });
        }

        return convertToDTO(quizRepository.save(quiz));
    }

    @Transactional
    public Optional<QuizDTO> update(Long id, QuizCreateDTO quizCreateDTO) {
        return quizRepository.findById(id).map(quiz -> {
            quiz.setTheme(quizCreateDTO.theme());
            // Simplification: update theme only, or complex logic for questions
            return convertToDTO(quizRepository.save(quiz));
        });
    }

    @Transactional
    public void delete(Long id) {
        quizRepository.deleteById(id);
    }

    private QuizDTO convertToDTO(Quiz quiz) {
        List<QuestionDTO> questionDTOs = quiz.getQuestions().stream()
                .map(q -> new QuestionDTO(
                        q.getId(),
                        q.getQuestionText(),
                        q.getOptions().stream()
                                .map(o -> new OptionDTO(o.getId(), o.getOptionText(), o.getIsCorrect()))
                                .toList()
                ))
                .toList();

        return new QuizDTO(
                quiz.getId(),
                quiz.getTheme(),
                quiz.getCreatedAt(),
                quiz.getLikesCount(),
                quiz.getUser() != null ? quiz.getUser().getId() : null,
                questionDTOs
        );
    }
}
