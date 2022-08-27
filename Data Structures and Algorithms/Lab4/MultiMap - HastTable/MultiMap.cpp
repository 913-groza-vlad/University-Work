#include "MultiMap.h"
#include "MultiMapIterator.h"
#include <exception>
#include <iostream>

using namespace std;


int MultiMap::hash(TKey key) const
{
	return abs(key) % this->m;
}
// Complexity: Theta(1)

MultiMap::MultiMap() {
	this->m = 10;
	this->length = 0;
	this->elements = new Node * [this->m]();
	/*this->elements = new Node * [this->m];
	for (int i = 0; i < this->m; i++)
		this->elements[i] = nullptr;*/
}
// Complexity: Theta(1)


void MultiMap::add(TKey c, TValue v) {
	if (this->length >= this->m)
		this->resize();

	Node* current = this->elements[this->hash(c)];
	while (current != nullptr && current->key != c)
		current = current->next;

	if (current != nullptr) {
		if (current->length == current->capacity)
			this->resizeNode(current);
		current->values[current->length] = v;
		current->length++;
	}
	else
	{
		Node* newNode = new Node;
		newNode->key = c;
		newNode->values[0] = v;
		newNode->length = 1;
		newNode->next = this->elements[this->hash(c)];
		this->elements[this->hash(c)] = newNode;
	}
	this->length++;
}
// if resize() function is called -> Complexity: Theta(m * keysNr)
// if resizeNode() funcion is called -> Complexity: Theta(length)
// otherwise -> Complexity: O(keysNr)
// an overall complexity for add function is Theta(1) - amortized

bool MultiMap::remove(TKey c, TValue v) {
	Node* previous = nullptr;
	Node* current = this->elements[this->hash(c)];
	/*if (current == nullptr)
		return false;*/

	while (current != nullptr && current->key != c)
	{
		previous = current;
		current = current->next;
	}
	if (current == nullptr)
		return false;

	for (int i = 0; i < current->length; i++)
		if (current->values[i] == v)
		{
			for (int j = i; j < current->length - 1; j++)
				current->values[j] = current->values[j + 1];
			current->length--;

			if (current->length == 0)
			{
				if (previous == nullptr)
					this->elements[this->hash(c)] = current->next;
				else
					previous->next = current->next;
				delete[] current->values;
				delete current;
			}
			this->length--;
			return true;
		}
	return false;
}
// Best case: Theta(1), if there is no node of key c in the array of nodes
// Average case: Theta(length), if there is a node of key c
// Complexity: O(length), length is the number of elements stored in the node of key c

vector<TValue> MultiMap::search(TKey c) const {
	vector<TValue> foundValues;
	Node* current = this->elements[this->hash(c)];
	int ok = 0;

	while (current != nullptr && ok == 0)
	{
		if (current->key == c)
		{
			for (int i = 0; i < current->length; i++)
				foundValues.push_back(current->values[i]);
			ok = 1;
		}
		else
			current = current->next;
	}
	return foundValues;
}
// Best case: Theta(length) - when the node of key c is the first in the linked list with index hash(c)
// Worst case: Theta(keysNr * length) - when the node of key c is the last in the linked list with index hash(c)
// Average case: Theta(keysNr * length) => overall complexity: O(length)

int MultiMap::size() const {
	return this->length;
}
// Complexity: Theta(1)

bool MultiMap::isEmpty() const {
	if (this->length == 0)
		return true;
	return false;
}
// Complexity: Theta(1)

MultiMapIterator MultiMap::iterator() const {
	return MultiMapIterator(*this);
}
// Complexity: O(m)

MultiMap::~MultiMap() {
	for (int i = 0; i < this->m; i++)
	{
		Node* currentNode = this->elements[i];
		while (currentNode != nullptr)
		{
			Node* nextNode = currentNode->next;
			delete[] currentNode->values;
			delete currentNode;
			currentNode = nextNode;
		}
	}
	delete[] this->elements;
}
// Complexity: Theta(m * keysNr)

void MultiMap::resize() {
	this->m *= 2;
	Node** newElements = new Node * [this->m]();
	/*Node** newElements = new Node * [this->m];
	for (int i = 0; i < this->m; i++)
		newElements[i] = nullptr;*/

	for (int i = 0; i < this->m / 2; i++)
	{
		Node* current = this->elements[i];
		while (current != nullptr)
		{
			Node* next = current->next;
			if (newElements[this->hash(current->key)] == nullptr)
			{
				current->next = nullptr;
				newElements[this->hash(current->key)] = current;
			}
			else
			{
				current->next = newElements[this->hash(current->key)];
				newElements[this->hash(current->key)] = current;
			}
			current = next;
		}
	}
	delete[] this->elements;
	this->elements = newElements;
}
// Complexity: Theta(m * keysNr)

void MultiMap::resizeNode(Node* node)
{
	node->capacity *= 2;
	TValue* newElems = new TValue[node->capacity];
	for (int i = 0; i < node->length; i++)
		newElems[i] = node->values[i];
	delete[] node->values;
	node->values = newElems;
}
//Complexity: Theta(length)


TValue MultiMap::mostFrequent() const
{
	TValue elem = NULL_TVALUE;
	TValue* elems = new TValue[this->length+1];
	int elemsLength = 0;
	if (this->isEmpty())
		return elem;
	int maxCount = 0;
	for (int i = 0; i < this->m; i++)
	{
		Node* current = this->elements[i];
		while (current != nullptr)
		{
			for (int j = 0; j < current->length; j++) {
				elems[elemsLength] = current->values[j];
				elemsLength++;
			}
			current = current->next;
		}
	}
	for (int i = 0; i < elemsLength - 1; i++)
	{
		int nr = 1;
		for (int j = i + 1; j < elemsLength; j++)
		{
			if (elems[i] == elems[j])
				nr++;
		}
		if (nr > maxCount) {
			maxCount = nr;
			elem = elems[i];
		}
	}
	return elem;
}
// Best case: Theta(1) -> when the multimap is empty
// Worst case: Theta(m*node_length + length^2)
// Complexity: O(m*node_length)