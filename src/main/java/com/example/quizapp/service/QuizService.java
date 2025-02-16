package com.example.quizapp.service;

import com.example.quizapp.dto.*;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.QuizSession;
import com.example.quizapp.model.UserResponse;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.repository.QuizSessionRepository;
import com.example.quizapp.repository.UserResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizService {

    @Autowired
    private QuizSessionRepository quizSessionRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserResponseRepository userResponseRepository;

    @Transactional
    public QuizSessionDTO startNewSession() {
        QuizSession session = new QuizSession();
        session.setStartTime(LocalDateTime.now());
        session.setActive(true);
        session = quizSessionRepository.save(session);

        QuizSessionDTO dto = new QuizSessionDTO();
        dto.setId(session.getId());
        dto.setActive(session.isActive());
        return dto;
    }

    @Transactional(readOnly = true)
    public QuestionDTO getRandomQuestion(Long sessionId) {
        // Check if session exists and is active
        Optional<QuizSession> sessionOpt = quizSessionRepository.findById(sessionId);
        if (sessionOpt.isEmpty() || !sessionOpt.get().isActive()) {
            return null;
        }

        Question question = questionRepository.findRandomQuestion();
        if (question == null) {
            return null;
        }

        // Check if this question has already been answered in this session
        long count = userResponseRepository.countByQuizSessionIdAndQuestionId(sessionId, question.getId());
        if (count > 0) {
            // Try to find another question that hasn't been answered yet
            List<Question> allQuestions = questionRepository.findAll();
            for (Question q : allQuestions) {
                count = userResponseRepository.countByQuizSessionIdAndQuestionId(sessionId, q.getId());
                if (count == 0) {
                    question = q;
                    break;
                }
            }
            
            // If all questions have been answered, return null
            if (count > 0) {
                return null;
            }
        }

        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setText(question.getText());
        dto.setAnswers(question.getAnswers().stream()
                .map(answer -> answer.getText())
                .collect(Collectors.toList()));
        return dto;
    }

    @Transactional
    public boolean submitAnswer(Long sessionId, AnswerSubmissionDTO submissionDTO) {
        Optional<QuizSession> sessionOpt = quizSessionRepository.findById(sessionId);
        if (sessionOpt.isEmpty() || !sessionOpt.get().isActive()) {
            return false;
        }

        QuizSession session = sessionOpt.get();
        Optional<Question> questionOpt = questionRepository.findById(submissionDTO.getQuestionId());
        if (questionOpt.isEmpty()) {
            return false;
        }

        Question question = questionOpt.get();
        
        // Check if this question has already been answered in this session
        long count = userResponseRepository.countByQuizSessionIdAndQuestionId(sessionId, question.getId());
        if (count > 0) {
            return false;
        }

        UserResponse response = new UserResponse();
        response.setQuizSession(session);
        response.setQuestion(question);
        response.setSelectedAnswerIndex(submissionDTO.getSelectedAnswerIndex());
        response.setCorrect(submissionDTO.getSelectedAnswerIndex() == question.getCorrectAnswerIndex());
        
        userResponseRepository.save(response);
        return true;
    }

    @Transactional
    public QuizResultDTO getResults(Long sessionId) {
        Optional<QuizSession> sessionOpt = quizSessionRepository.findById(sessionId);
        if (sessionOpt.isEmpty()) {
            return null;
        }

        QuizSession session = sessionOpt.get();
        List<UserResponse> responses = userResponseRepository.findByQuizSessionId(sessionId);
        
        QuizResultDTO result = new QuizResultDTO();
        result.setTotalQuestions(responses.size());
        result.setCorrectAnswers((int) responses.stream().filter(UserResponse::isCorrect).count());
        result.setIncorrectAnswers(responses.size() - result.getCorrectAnswers());
        
        List<QuizResultDTO.QuestionResultDTO> questionResults = new ArrayList<>();
        for (UserResponse response : responses) {
            QuizResultDTO.QuestionResultDTO qr = new QuizResultDTO.QuestionResultDTO();
            qr.setQuestionId(response.getQuestion().getId());
            qr.setQuestionText(response.getQuestion().getText());
            qr.setAnswers(response.getQuestion().getAnswers().stream()
                    .map(answer -> answer.getText())
                    .collect(Collectors.toList()));
            qr.setSelectedAnswerIndex(response.getSelectedAnswerIndex());
            qr.setCorrectAnswerIndex(response.getQuestion().getCorrectAnswerIndex());
            qr.setCorrect(response.isCorrect());
            questionResults.add(qr);
        }
        result.setQuestionResults(questionResults);
        
        // If session is still active, end it
        if (session.isActive()) {
            session.setActive(false);
            session.setEndTime(LocalDateTime.now());
            quizSessionRepository.save(session);
        }
        
        return result;
    }
}