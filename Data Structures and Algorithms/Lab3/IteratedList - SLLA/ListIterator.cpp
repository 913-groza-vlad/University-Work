#include "ListIterator.h"
#include "IteratedList.h"
#include <exception>

ListIterator::ListIterator(const IteratedList& list) : list(list) {
	this->current = list.head;
}
// Complexity: Theta(1)

void ListIterator::first() {
	this->current = list.head;
}
// Complexity: Theta(1)

void ListIterator::next() {
	if (!this->valid())
		throw std::exception();
	this->current = list.next[this->current];
}
// Complexity: Theta(1)

bool ListIterator::valid() const {
	if (this->current != -1)
		return true;
	return false;
}
// Complexity: Theta(1)

TElem ListIterator::getCurrent() const {
	if (!this->valid())
		throw std::exception();
	return list.elements[this->current];
}
// Complexity: Theta(1)



