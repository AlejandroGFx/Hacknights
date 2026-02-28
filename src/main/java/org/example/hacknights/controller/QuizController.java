package org.example.hacknights.controller;

import lombok.RequiredArgsConstructor;
import org.example.hacknights.dto.QuizCreateDTO;
import org.example.hacknights.dto.QuizDTO;
import org.example.hacknights.service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping
    public ResponseEntity<List<QuizDTO>> getAll() {
        return ResponseEntity.ok(quizService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizDTO> getById(@PathVariable Long id) {
        return quizService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<QuizDTO> create(@RequestBody QuizCreateDTO quizCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(quizService.create(quizCreateDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizDTO> update(@PathVariable Long id, @RequestBody QuizCreateDTO quizCreateDTO) {
        return quizService.update(id, quizCreateDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        quizService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
