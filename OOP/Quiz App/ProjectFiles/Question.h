#pragma once
#include <string>
#include <vector>
#include <sstream>
#include <iostream>

class Question
{
private:
	int id;
	std::string text;
	std::string answer;
	int score;
public:
	Question() :id{ 0 }, text{ "" }, answer{ "" }, score{ 0 } {};
	Question(int _id, const std::string& _text, const std::string& _answer, int _score) : id{ _id }, text{ _text }, answer{ _answer }, score{ _score } {}

	int getId();
	int getScore();
	std::string getAnswer();
	std::string getText();

	std::string toParticipant();
	std::string toPresenter();

	friend std::ostream& operator<<(std::ostream& os, Question& q);
	friend std::istream& operator>>(std::istream& is, Question& q);

};

std::vector<std::string> tokenize(std::string str, char delimiter);
