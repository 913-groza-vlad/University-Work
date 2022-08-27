#pragma once
#include "Repository.h"
#include "Subject.h"
#include <exception>

class Quiz : public Subject
{
private:
	Repository* repo;
public:
	Quiz(Repository* r1) : repo{ r1 } {}

	void addQuestion(int id, std::string text, std::string answer, int score);
	void updatePartScore(Participant& p, int newScore);
	Question get_ques(int id) { return this->repo->getQuestion(id); }

	std::vector<Participant> getAllParticipants();
	std::vector<Question> getAllQuestions();
};

