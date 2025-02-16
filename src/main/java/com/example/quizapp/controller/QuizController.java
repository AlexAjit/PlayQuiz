package com.example.quizapp.controller;

import com.example.quizapp.dto.AnswerSubmissionDTO;
import com.example.quizapp.dto.QuestionDTO;
import com.example.quizapp.dto.QuizResultDTO;
import com.example.quizapp.dto.QuizSessionDTO;
import com.example.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/start")
    public ResponseEntity<QuizSessionDTO> startNewSession() {
        QuizSessionDTO session = quizService.startNewSession();
        return ResponseEntity.ok(session);
    }

    @GetMapping("/{sessionId}/question")
    public ResponseEntity<QuestionDTO> getQuestion(@PathVariable Long sessionId) {
        QuestionDTO question = quizService.getRandomQuestion(sessionId);
        if (question == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(question);
    }

    @PostMapping("/{sessionId}/submit")
    public ResponseEntity<Boolean> submitAnswer(
            @PathVariable Long sessionId,
            @RequestBody AnswerSubmissionDTO submissionDTO) {
        boolean success = quizService.submitAnswer(sessionId, submissionDTO);
        if (success) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @GetMapping("/{sessionId}/results")
    public ResponseEntity<QuizResultDTO> getResults(@PathVariable Long sessionId) {
        QuizResultDTO result = quizService.getResults(sessionId);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}