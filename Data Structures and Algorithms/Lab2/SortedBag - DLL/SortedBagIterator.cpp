#include "SortedBagIterator.h"
#include "SortedBag.h"
#include <exception>

using namespace std;

SortedBagIterator::SortedBagIterator(const SortedBag& b) : bag(b) {
	this->first_node = this->bag.head;
	this->current_node = this->first_node;
	this->cont = 0;
}
// Theta(1)

TComp SortedBagIterator::getCurrent() {
	if (!this->valid())
		throw std::exception();
	return this->current_node->data;
}
// Theta(1)

bool SortedBagIterator::valid() {
	return this->current_node != nullptr;
}
// Theta(1)

void SortedBagIterator::next() {
	if (!this->valid())
		throw std::exception();

	this->cont++;
	if (this->cont >= this->current_node->frequency)
	{
		this->cont = 0;
		if (this->current_node != nullptr)
			this->current_node = this->current_node->next;
	}
}
// Theta(1)


void SortedBagIterator::first() {
	if (!this->bag.isEmpty())
		this->current_node = this->first_node;
	this->cont = 0;
}
// Theta(1)

