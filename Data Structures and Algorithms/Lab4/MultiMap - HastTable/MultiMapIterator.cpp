#include "MultiMapIterator.h"
#include "MultiMap.h"


MultiMapIterator::MultiMapIterator(const MultiMap& c): col(c) {
	this->current = nullptr;
	this->currentHash = -1;
	this->position = -1;
	int i = 0, ok = 0;
	while (i < col.m && ok == 0)
	{
		if (this->col.elements[i] != nullptr)
		{
			this->current = this->col.elements[i];
			this->currentHash = i;
			this->position = 0;
			ok = 1;
		}
		else
			i++;
	}
}
// Best case: Theta(1) - the list with index 0 of the array is not empty
// Worst case: Theta(m) - all lists are empty
// Average case: Theta(m) => Overall complexity: O(m)

TElem MultiMapIterator::getCurrent() const{
	if (!this->valid())
		throw std::exception();
	TElem pair(this->current->key, this->current->values[position]);
	return pair;
}
// Complexity: Theta(1)

bool MultiMapIterator::valid() const {
	if (this->current != nullptr)
		return true;
	return false;
}
// Complexity: Theta(1)

void MultiMapIterator::next() {
	if (!this->valid())
		throw std::exception();
	if (this->position == this->current->length - 1)
	{
		if (this->current->next != nullptr)
		{
			this->current = this->current->next;
			this->position = 0;
			return;
		}
		for (int i = this->currentHash + 1; i < this->col.m; i++)
		{
			if (this->col.elements[i] != nullptr)
			{
				this->current = this->col.elements[i];
				this->currentHash = i;
				this->position = 0;
				return;
			}
		}
		this->current = nullptr;
		this->currentHash = -1;
		this->position = -1;
	}
	else
		this->position++;
}
// Overall complexity: O(m)

void MultiMapIterator::first() {
	this->current = nullptr;
	this->currentHash = -1;
	this->position = -1;
	for (int i = 0; i < this->col.m; i++)
	{
		if (this->col.elements[i] != nullptr)
		{
			this->current = this->col.elements[i];
			this->currentHash = i;
			this->position = 0;
			break;
		}
	}
}
// Complexity: O(m)


