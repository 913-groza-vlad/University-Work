#include "Participant.h"

int Participant::getScore()
{
    return this->score;
}

std::string Participant::getName()
{
    return this->name;
}

void Participant::setScore(int new_score)
{
    this->score = new_score;
}

std::string Participant::toStr()
{
    std::stringstream ss;
    ss << this->name << "|" << this->score;
    return ss.str();
}

std::ostream& operator<<(std::ostream& os, Participant& p)
{
    os << p.toStr() << "\n";
    return os;
}

std::istream& operator>>(std::istream& is, Participant& p)
{
    std::string line;
    std::getline(is, line);
    std::vector<std::string> tokens = tokenize(line, '|');
    if (tokens.size() != 2)
        return is;
    p.name = tokens[0];
    p.score = stoi(tokens[1]);

    return is;
}
