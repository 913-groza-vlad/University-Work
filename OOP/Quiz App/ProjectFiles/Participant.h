#pragma once
#include "Question.h"

class Participant
{
private:
	std::string name;
	int score;
public:
	Participant() : name{ "" }, score{ 0 } {}
	Participant(std::string name, int score) : name{ name }, score{ score } {}

	int getScore();
	std::string getName();
	void setScore(int new_score);

	std::string toStr();

	friend std::ostream& operator<<(std::ostream& os, Participant& p);
	friend std::istream& operator>>(std::istream& is, Participant& p);
};

