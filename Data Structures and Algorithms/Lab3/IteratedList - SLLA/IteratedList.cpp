
#include <exception>
#include "ListIterator.h"
#include "IteratedList.h"


IteratedList::IteratedList() {
	this->capacity = 10;
	this->elements = new TElem[this->capacity];
	this->next = new int[this->capacity];
	this->head = -1;
	for (int i = 0; i < this->capacity - 1; i++)
		this->next[i] = i + 1;
	this->next[this->capacity - 1] = -1;
	this->firstFree = 0;
	this->length = 0;
}
// Complexity: Theta(capacity)

int IteratedList::size() const {
	return this->length;
}
// Complexity: Theta(1)

bool IteratedList::isEmpty() const {
	if (this->length == 0)
		return true;
	return false;
}
// Complexity: Theta(1)

ListIterator IteratedList::first() const {
	return ListIterator(*this);
}
// Complexity: Theta(1)

TElem IteratedList::getElement(ListIterator pos) const {
	if (pos.valid() == false)
		throw std::exception();
	return pos.getCurrent();
}
// Complexity: Theta(1)

TElem IteratedList::remove(ListIterator& pos) {
	if (!pos.valid())
		throw std::exception();

	TElem el = pos.getCurrent();
	int current = this->head;
	int prev = -1;

	while (current != pos.current)
	{
		prev = current;
		current = this->next[current];
	}
	
	if (current == this->head)
		this->head = this->next[this->head];
	else
		this->next[prev] = this->next[current];
	this->next[current] = this->firstFree;
	this->firstFree = current;
	
	this->length--;
	return el;
}
// Best case: Theta(1) - when el is the first element of the list
// Worst case: Theta(length) - when el on the last position of the list
// Average case: Theta(length)
// => Overall complexity: O(length)

ListIterator IteratedList::search(TElem e) const{
	ListIterator it = this->first();
	//int current = this->head;
	while (it.valid())
	{
		if (it.getCurrent() == e)
			return it;	
		it.next();
	}
	return it;
}
// Best case: Theta(1) - e is on the first position in the list
// Worst case: Theta(length) - e is not in the list
// Average case: Theta(length)
// => Overall complexity: O(length)

TElem IteratedList::setElement(ListIterator pos, TElem e) {
	if (pos.valid() == false)
		throw std::exception();
	TElem old = pos.getCurrent();
	this->elements[pos.current] = e;
	return old;
}
// Complexity: Theta(1)

void IteratedList::addToPosition(ListIterator& pos, TElem e) {
	if (!pos.valid())
		throw std::exception();

	if (this->firstFree == -1)
		this->resize();

	int position = this->firstFree;
	this->firstFree = this->next[this->firstFree];
	this->elements[position] = e;
	this->next[position] = this->next[pos.current];
	this->next[pos.current] = position;
	this->length++;
	pos.next();
}
// If the capacity of the array is reached, the complexity is the same as the complexity of resize -> Theta(capacity)
// Otherwise -> Complexity: Theta(1)

void IteratedList::addToEnd(TElem e) {
	if (this->firstFree == -1)
		this->resize();
	if (this->length == 0)
	{
		this->elements[0] = e;
		this->firstFree = this->next[this->firstFree];
		this->next[0] = this->head;
		this->head = 0;
	}
	else
	{
		int current = this->head, prev = 0;
		while (current != -1)
		{
			prev = current;
			current = this->next[current];
		}
		int position = this->firstFree;
		this->next[prev] = position;
		this->firstFree = this->next[this->firstFree];
		this->next[position] = -1;
		this->elements[position] = e;
	}
	this->length++;
}
// If the capacity of the array is reached, the complexity is the same as the complexity of resize -> Theta(capacity)
// Otherwise -> Best case: Theta(1) - there are no elements in the list
// Worst case: Theta(length)
// Average case: Theta(length) => Overall complexity: O(length)

void IteratedList::addToBeginning(TElem e) {
	if (this->firstFree == -1)
		this->resize();
	int newPos = this->firstFree;
	this->elements[newPos] = e;
	this->firstFree = this->next[this->firstFree];
	this->next[newPos] = this->head;
	this->head = newPos;
	this->length++;
}
// Overall complexity: Theta(1)

void IteratedList::resize() {
	TElem* newelems = new TElem[this->capacity * 2];
	int* newnext = new int[this->capacity * 2];

	for (int i = 0; i < this->size(); i++)
	{
		newelems[i] = this->elements[i];
		newnext[i] = this->next[i];
	}

	for (int i = this->capacity; i < this->capacity * 2 - 1; i++)
		newnext[i] = i + 1;
	newnext[this->capacity * 2 - 1] = -1;

	delete[] this->elements;
	delete[] this->next;

	this->elements = newelems;
	this->next = newnext;
	this->firstFree = this->capacity;
	this->capacity *= 2;
}
// Complexity: Theta(capacity)


IteratedList::~IteratedList() {
	delete[] this->elements;
	delete[] this->next;
}
// The destructor has a Theta(1) overall complexity


int IteratedList::removeAll(IteratedList& list)
{
	ListIterator iter = list.first();
	int nrelems = 0;
	while (iter.valid())
	{
		ListIterator found = this->search(iter.getCurrent());
		if (found.valid())
		{
			int el = this->remove(found);
			nrelems++;
		}
		iter.next();
	}
	return nrelems;
}
// Best case: Theta(length) - when no element is found in the list
// Worst case: Theta(length ^ 2) - when every element appears in the list
// Average case: Theta(length ^ 2) -> Overall complexity: O(length^2)