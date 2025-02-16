package com.example.quizapp.dto;

import java.util.List;

public class QuizResultDTO {
    private int totalQuestions;
    private int correctAnswers;
    private int incorrectAnswers;
    private List<QuestionResultDTO> questionResults;

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(int incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public List<QuestionResultDTO> getQuestionResults() {
        return questionResults;
    }

    public void setQuestionResults(List<QuestionResultDTO> questionResults) {
        this.questionResults = questionResults;
    }

    public static class QuestionResultDTO {
        private Long questionId;
        private String questionText;
        private List<String> answers;
        private int selectedAnswerIndex;
        private int correctAnswerIndex;
        private boolean correct;

        public Long getQuestionId() {
            return questionId;
        }

        public void setQuestionId(Long questionId) {
            this.questionId = questionId;
        }

        public String getQuestionText() {
            return questionText;
        }

        public void setQuestionText(String questionText) {
            this.questionText = questionText;
        }

        public List<String> getAnswers() {
            return answers;
        }

        public void setAnswers(List<String> answers) {
            this.answers = answers;
        }

        public int getSelectedAnswerIndex() {
            return selectedAnswerIndex;
        }

        public void setSelectedAnswerIndex(int selectedAnswerIndex) {
            this.selectedAnswerIndex = selectedAnswerIndex;
        }

        public int getCorrectAnswerIndex() {
            return correctAnswerIndex;
        }

        public void setCorrectAnswerIndex(int correctAnswerIndex) {
            this.correctAnswerIndex = correctAnswerIndex;
        }

        public boolean isCorrect() {
            return correct;
        }

        public void setCorrect(boolean correct) {
            this.correct = correct;
        }
    }
}