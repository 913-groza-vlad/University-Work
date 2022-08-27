#pragma once
#include "Participant.h"
#include <algorithm>
#include <fstream>

class Repository
{
private:
	std::vector<Participant> participants;
	std::vector<Question> questions;
	std::string partFile, quesFile;
	bool presenter;
public:
	Repository(std::string _partFile, std::string _quesFile, bool isPresenter) : partFile{ _partFile }, quesFile{ _quesFile }, presenter{ isPresenter }
	{
		this->loadParticipants();
		this->loadQuestions();
	}

	void loadParticipants();
	void loadQuestions();
	void saveParticipants();
	void saveQuestions();

	std::vector<Participant> getParticipants();
	std::vector<Question> getQuestions();

	void add(Question q) { this->questions.push_back(q); }
	void updateScore(Participant& q, int new_score) { q.setScore(new_score); }

	Question getQuestion(int id);

	~Repository()
	{
		this->saveParticipants();
		this->saveQuestions();
	}

};

