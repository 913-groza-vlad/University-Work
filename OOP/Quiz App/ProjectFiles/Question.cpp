#include "Question.h"

int Question::getId()
{
    return this->id;
}

int Question::getScore()
{
    return this->score;
}

std::string Question::getAnswer()
{
    return this->answer;
}

std::string Question::getText()
{
    return this->text;
}

std::string Question::toParticipant()
{
    std::stringstream ss;
    ss << this->id << "|" << this->text << "|" << this->score;
    return ss.str();
}

std::string Question::toPresenter()
{
    std::stringstream ss;
    ss << this->id << "|" << this->text << "|" << this->answer<< "|" << this->score;
    return ss.str();
}

std::ostream& operator<<(std::ostream& os, Question& q)
{
    os << q.toPresenter() << "\n";
    return os;
}

std::istream& operator>>(std::istream& is, Question& q)
{
    std::string line;
    std::getline(is, line);
    std::vector<std::string> tokens = tokenize(line, '|');
    if (tokens.size() != 4)
        return is;
    q.id = stoi(tokens[0]);
    q.text = tokens[1];
    q.answer = tokens[2];
    q.score = stoi(tokens[3]);

    return is;
}

std::vector<std::string> tokenize(std::string str, char delimiter)
{
    std::string token;
    std::vector<std::string> tokens{};
    std::stringstream ss{ str };
    while (std::getline(ss, token, delimiter))
        tokens.push_back(token);
    return tokens;
}
