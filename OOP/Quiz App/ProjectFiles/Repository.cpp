#include "Repository.h"

void Repository::loadParticipants()
{
	std::ifstream fin(this->partFile);
	Participant p{};
	while (fin >> p)
		this->participants.push_back(p);

	fin.close();
}

void Repository::loadQuestions()
{
	std::ifstream fin(this->quesFile);
	Question q{};
	while (fin >> q)
		this->questions.push_back(q);

	if (!this->presenter)
		std::sort(this->questions.begin(), this->questions.end(), [](Question q1, Question q2) { return q1.getScore() > q2.getScore(); });
	else
		std::sort(this->questions.begin(), this->questions.end(), [](Question q1, Question q2) { return q1.getId() < q2.getId(); });

	fin.close();
}

void Repository::saveParticipants()
{
	std::ofstream fout(this->partFile);
	for (auto p : participants)
		fout << p;
	fout.close();
}

void Repository::saveQuestions()
{
	std::ofstream fout(this->quesFile);
	for (auto p : questions)
		fout << p;
	fout.close();
}

std::vector<Participant> Repository::getParticipants()
{
	return this->participants;
}

std::vector<Question> Repository::getQuestions()
{
	return this->questions;
}

Question Repository::getQuestion(int id)
{
	for (auto q : questions)
		if (q.getId() == id)
			return q;
	throw std::exception("No q with this id");
}
