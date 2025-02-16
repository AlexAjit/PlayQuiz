-- Questions and Answers
-- Question 1
INSERT INTO question (id, text, correct_answer_index) VALUES (1, 'What is the capital of France?', 2);
INSERT INTO answer (question_id, text) VALUES (1, 'London');
INSERT INTO answer (question_id, text) VALUES (1, 'Berlin');
INSERT INTO answer (question_id, text) VALUES (1, 'Paris');
INSERT INTO answer (question_id, text) VALUES (1, 'Madrid');

-- Question 2
INSERT INTO question (id, text, correct_answer_index) VALUES (2, 'Which planet is known as the Red Planet?', 1);
INSERT INTO answer (question_id, text) VALUES (2, 'Venus');
INSERT INTO answer (question_id, text) VALUES (2, 'Mars');
INSERT INTO answer (question_id, text) VALUES (2, 'Jupiter');
INSERT INTO answer (question_id, text) VALUES (2, 'Saturn');

-- Question 3
INSERT INTO question (id, text, correct_answer_index) VALUES (3, 'Who painted the Mona Lisa?', 0);
INSERT INTO answer (question_id, text) VALUES (3, 'Leonardo da Vinci');
INSERT INTO answer (question_id, text) VALUES (3, 'Pablo Picasso');
INSERT INTO answer (question_id, text) VALUES (3, 'Vincent van Gogh');
INSERT INTO answer (question_id, text) VALUES (3, 'Michelangelo');

-- Question 4
INSERT INTO question (id, text, correct_answer_index) VALUES (4, 'What is the largest mammal in the world?', 2);
INSERT INTO answer (question_id, text) VALUES (4, 'Elephant');
INSERT INTO answer (question_id, text) VALUES (4, 'Giraffe');
INSERT INTO answer (question_id, text) VALUES (4, 'Blue Whale');
INSERT INTO answer (question_id, text) VALUES (4, 'Polar Bear');

-- Question 5
INSERT INTO question (id, text, correct_answer_index) VALUES (5, 'Which element has the chemical symbol ''O''?', 1);
INSERT INTO answer (question_id, text) VALUES (5, 'Gold');
INSERT INTO answer (question_id, text) VALUES (5, 'Oxygen');
INSERT INTO answer (question_id, text) VALUES (5, 'Osmium');
INSERT INTO answer (question_id, text) VALUES (5, 'Oganesson');