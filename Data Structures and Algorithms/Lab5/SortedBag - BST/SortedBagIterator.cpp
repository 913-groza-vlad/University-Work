#include "SortedBagIterator.h"
#include "SortedBag.h"
#include <exception>

using namespace std;

SortedBagIterator::SortedBagIterator(const SortedBag& b) : bag(b) {
	this->firstNode = bag.root;
	if (this->firstNode != nullptr)
		while (this->firstNode->left != nullptr)
			this->firstNode = this->firstNode->left;
	this->current = this->firstNode;
}
// Complexity: Theta(length)

TComp SortedBagIterator::getCurrent() {
	if (!this->valid())
		throw std::exception();
	return this->current->info;
}
// Complexity: Theta(1)

bool SortedBagIterator::valid() {
	if (this->current != nullptr)
		return true;
	return false;
}
// Complexity: Theta(1)

void SortedBagIterator::next() {
	if (!this->valid())
		throw std::exception();
	this->current = bag.successor(this->current);
}
// Complexity: O(length) - the same as the complexity of successor

void SortedBagIterator::first() {
	this->current = this->firstNode;
}
// Complexity: Theta(1)

