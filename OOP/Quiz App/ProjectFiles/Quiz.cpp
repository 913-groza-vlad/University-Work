#include "Quiz.h"

void Quiz::addQuestion(int id, std::string text, std::string answer, int score)
{
    for (auto q : this->getAllQuestions())
        if (id == q.getId())
            throw std::exception("Question with this id exists");
    Question qu{ id, text, answer, score };
    this->repo->add(qu);
    this->notify();
    this->repo->saveQuestions();
    this->repo->saveParticipants();
}

void Quiz::updatePartScore(Participant& p, int newScore)
{
    this->repo->updateScore(p, newScore);
    this->repo->saveQuestions();
    this->repo->saveParticipants();
    this->notify();
}

std::vector<Participant> Quiz::getAllParticipants()
{
    return repo->getParticipants();
}

std::vector<Question> Quiz::getAllQuestions()
{
    return repo->getQuestions();
}
