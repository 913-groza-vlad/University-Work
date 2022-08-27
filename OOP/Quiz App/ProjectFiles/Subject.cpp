#include "Subject.h"

void Subject::addObserver(Observer* obs)
{
	this->observers.push_back(obs);
}

void Subject::notify()
{
	for (auto obs : observers)
		obs->update();
}
