// Global variables
let sessionId = null;
let currentQuestion = null;
let answeredQuestions = 0;
let totalQuestions = 0;
let questionsList = [];
let selectedAnswerIndex = null; // Track the selected answer

// DOM Elements
const startScreen = document.getElementById('start-screen');
const quizScreen = document.getElementById('quiz-screen');
const resultScreen = document.getElementById('result-screen');
const startButton = document.getElementById('start-button');
const nextButton = document.getElementById('next-button');
const finishButton = document.getElementById('finish-button');
const restartButton = document.getElementById('restart-button');
const questionText = document.getElementById('question-text');
const answersContainer = document.getElementById('answers-container');
const progressBar = document.getElementById('progress');
const progressText = document.getElementById('progress-text');
const totalQuestionsElement = document.getElementById('total-questions');
const correctAnswersElement = document.getElementById('correct-answers');
const incorrectAnswersElement = document.getElementById('incorrect-answers');
const scorePercentageElement = document.getElementById('score-percentage');
//const questionsSummary = document.getElementById('questions-summary');

// Event Listeners
startButton.addEventListener('click', startQuiz);
nextButton.addEventListener('click', handleNextQuestion);
finishButton.addEventListener('click', finishQuiz);
restartButton.addEventListener('click', resetQuiz);

// Start a new quiz session
async function startQuiz() {
    try {
        const response = await fetch('/api/quiz/start', { method: 'POST' });

        if (!response.ok) {
            throw new Error('Failed to start quiz session');
        }

        const data = await response.json();
        sessionId = data.id;

        // Reset quiz state
        answeredQuestions = 0;
        questionsList = [];
        selectedAnswerIndex = null;

        // Show quiz screen
        startScreen.classList.add('hidden');
        quizScreen.classList.remove('hidden');

        // Get first question
        await getNextQuestion();
    } catch (error) {
        console.error('Error starting quiz:', error);
        alert('Failed to start quiz. Please try again.');
    }
}

// Get the next random question
async function getNextQuestion() {
    nextButton.disabled = true;
    selectedAnswerIndex = null; // Reset selected answer for the new question

    try {
        const response = await fetch(`/api/quiz/${sessionId}/question`);

        if (response.status === 204) {
            // No more questions available
            finishButton.classList.remove('hidden');
            nextButton.classList.add('hidden');
            return;
        }

        if (!response.ok) {
            throw new Error('Failed to get question');
        }

        currentQuestion = await response.json();
        displayQuestion(currentQuestion);
    } catch (error) {
        console.error('Error getting question:', error);
        alert('Failed to get next question. Please try again.');
    }
}

// Display the current question
function displayQuestion(question) {
    questionText.textContent = question.text;
    answersContainer.innerHTML = '';

    question.answers.forEach((answer, index) => {
        const answerElement = document.createElement('div');
        answerElement.classList.add('answer-option');
        answerElement.textContent = answer;
        answerElement.dataset.index = index;
        answerElement.addEventListener('click', selectAnswer);
        answersContainer.appendChild(answerElement);
    });

    // Ensure the progress updates correctly
    if (questionsList.findIndex(q => q.id === question.id) === -1) {
        totalQuestions++;
        questionsList.push(question);
    }

    updateProgress();
}

// Handle answer selection
function selectAnswer(event) {
    // Remove selected class from all answers
    const answers = answersContainer.querySelectorAll('.answer-option');
    answers.forEach(a => a.classList.remove('selected'));

    // Add selected class to the clicked answer
    event.target.classList.add('selected');

    // Store the selected answer index
    selectedAnswerIndex = parseInt(event.target.dataset.index);

    // Enable the next button
    nextButton.disabled = false;
}

// Handle "Next Question" click
async function handleNextQuestion() {
    if (selectedAnswerIndex === null) {
        alert('Please select an answer before proceeding.');
        return;
    }

    await submitAnswer(selectedAnswerIndex);
    await getNextQuestion();
}

// Submit the selected answer
async function submitAnswer(selectedIndex) {
    try {
        const response = await fetch(`/api/quiz/${sessionId}/submit`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                questionId: currentQuestion.id,
                selectedAnswerIndex: selectedIndex
            })
        });

        if (!response.ok) {
            throw new Error('Failed to submit answer');
        }

        // ✅ Now increment the answeredQuestions count here
        answeredQuestions++;

        // ✅ Show finish button when 5 questions are answered
        if (answeredQuestions >= 5) {
            finishButton.classList.remove('hidden');
            nextButton.classList.add('hidden');
        }

        updateProgress();
    } catch (error) {
        console.error('Error submitting answer:', error);
        alert('Failed to submit answer. Please try again.');
    }
}

// Update the progress bar
function updateProgress() {
    const percentage = (answeredQuestions / 5) * 100;
    progressBar.style.width = `${percentage}%`;
    progressText.textContent = `Question ${answeredQuestions}/5`;
}

// Finish the quiz and show results
async function finishQuiz() {
    try {
        const response = await fetch(`/api/quiz/${sessionId}/results`);

        if (!response.ok) {
            throw new Error('Failed to get results');
        }

        const results = await response.json();
        displayResults(results);

        // Show result screen
        quizScreen.classList.add('hidden');
        resultScreen.classList.remove('hidden');
    } catch (error) {
        console.error('Error finishing quiz:', error);
        alert('Failed to get quiz results. Please try again.');
    }
}

// Display the quiz results
function displayResults(results) {
    totalQuestionsElement.textContent = results.totalQuestions;
    correctAnswersElement.textContent = results.correctAnswers;
    incorrectAnswersElement.textContent = results.incorrectAnswers;

    const percentage = Math.round((results.correctAnswers / results.totalQuestions) * 100);
    scorePercentageElement.textContent = `${percentage}%`;

    //questionsSummary.innerHTML = '';

    results.questionResults.forEach((result, index) => {
        const questionDiv = document.createElement('div');
        questionDiv.classList.add('question-result');

        const questionHeading = document.createElement('h4');
        questionHeading.textContent = `Question ${index + 1}: ${result.questionText}`;
        questionDiv.appendChild(questionHeading);

        const statusText = document.createElement('p');
        statusText.textContent = result.correct ? '✓ Correct' : '✗ Incorrect';
        statusText.style.color = result.correct ? '#2ecc71' : '#e74c3c';
        statusText.style.fontWeight = 'bold';
        questionDiv.appendChild(statusText);

        result.answers.forEach((answer, i) => {
            const answerDiv = document.createElement('div');
            answerDiv.classList.add('answer-review');
            answerDiv.textContent = answer;

            if (i === result.selectedAnswerIndex) {
                answerDiv.classList.add('selected');
            }

            if (i === result.correctAnswerIndex) {
                answerDiv.classList.add('correct');
            } else if (i === result.selectedAnswerIndex && i !== result.correctAnswerIndex) {
                answerDiv.classList.add('incorrect');
            }

            questionDiv.appendChild(answerDiv);
        });

        //questionsSummary.appendChild(questionDiv);
    });
}

// Reset the quiz to start again
function resetQuiz() {
    resultScreen.classList.add('hidden');
    startScreen.classList.remove('hidden');

    nextButton.classList.remove('hidden');
    finishButton.classList.add('hidden');
    progressBar.style.width = '0%';

    sessionId = null;
    currentQuestion = null;
    answeredQuestions = 0;
    questionsList = [];
    selectedAnswerIndex = null;
}