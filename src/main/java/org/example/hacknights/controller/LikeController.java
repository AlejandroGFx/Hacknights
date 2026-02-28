package org.example.hacknights.controller;

import lombok.RequiredArgsConstructor;
import org.example.hacknights.dto.LikeDTO;
import org.example.hacknights.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @GetMapping
    public ResponseEntity<List<LikeDTO>> getAll() {
        return ResponseEntity.ok(likeService.findAll());
    }

    @PostMapping("/user/{userId}/quiz/{quizId}")
    public ResponseEntity<LikeDTO> create(@PathVariable Long userId, @PathVariable Long quizId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(likeService.create(userId, quizId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        likeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
