#include "SMIterator.h"
#include "SortedMap.h"
#include <exception>

using namespace std;

SortedMap::SortedMap(Relation r) {

	this->relation = r;
	this->nrelements = 0;
	this->elements = new TElem[6005];
}
// Theta(1)

SortedMap::SortedMap(const SortedMap& sm) {

	this->relation = sm.relation;
	this->nrelements = sm.nrelements;
	this->elements = new TElem[6005];
	for (int i = 0; i < this->size(); i++)
		this->elements[i] = sm.elements[i];
}
// Theta(nrelements)

TValue SortedMap::add(TKey k, TValue v) {
	int index = 0;
	while (index < this->nrelements)
	{
		if (this->elements[index].first == k)
		{
			TValue copy = this->elements[index].second;
			this->elements[index].second = v;
			return copy;
		}
		index++;
	}
	this->elements[this->nrelements].first = k;
	this->elements[this->nrelements].second = v;
	this->nrelements++;
	return NULL_TVALUE;
}
// Best case: Theta(1) / Worst case: Theta(nrelements) / Average case: Theta(nrelements)
// => Overall complexity: O(nrelements)

TValue SortedMap::search(TKey k) const {
	int index = 0;
	while (index < this->nrelements)
	{
		if (this->elements[index].first == k) 
			return this->elements[index].second;
		index++;
	}

	return NULL_TVALUE;
}
// Best case: Theta(1) / Worst case: Theta(nrelements) / Average case: Theta(nrelements)
// Overall complexity: O(nrelements)

TValue SortedMap::remove(TKey k) {
	int index = 0, found = 0;
	while (index < this->nrelements && !found)
	{
		if (this->elements[index].first == k)
			found = 1;
		else
			index++;
	}
	if (!found)
		return NULL_TVALUE;
	TValue copy = this->elements[index].second;
	this->elements[index] = this->elements[this->nrelements - 1];
	this->nrelements--;
	return copy;
}
// Best case: Theta(1) / Worst case: Theta(nrelements) / Average case: Theta(nrelements)
// Overall complexity: O(nrelements)

int SortedMap::size() const {
	return this->nrelements;
}
// Theta(1)

bool SortedMap::isEmpty() const {
	if (this->nrelements == 0)
		return true;
	return false;
}
// Theta(1)

SMIterator SortedMap::iterator()
{	
	sort();
	//quickSort(0, this->nrelements - 1);
	return SMIterator(*this);
}
// iterator() complexity is the same as the sort function's => Overall complexity: O(nrelements^2)

SortedMap::~SortedMap() {
	delete[] this->elements;
}
// Theta(1)


void SortedMap::swap(TElem* a, TElem* b)
{
	TElem t = *a;
	*a = *b;
	*b = t;
}
// Theta(1) - constant complexity (performs exactly one construction and two assignments)

void SortedMap::sort()
{
	int pos = 0, length = this->size();
	while (pos < length)
	{
		if (pos == 0 || this->relation(this->elements[pos].first, this->elements[pos - 1].first) == false)
			pos++;
		else
		{
			swap(&this->elements[pos], &this->elements[pos - 1]);
			pos--;
		}
	}
}
// Best case: Theta(nrelements), when the keys are already sorted 
// Worst case: Theta(nrelements^2), the keys are sorted in the opposite order
// Average case: Theta(nrelements^2) => Overall complexity O(nrelements^2)


//int SortedMap::partition(int left, int right)
//{
//	TElem pivot = this->elements[right];
//
//	// pointer for greater element
//	int i = (left - 1);
//
//	// traverse each element of the array
//	// compare them with the pivot
//	for (int j = left; j < right; j++)
//		if (this->relation(this->elements[j].first, pivot.first) == true)
//		{
//			i++;
//			swap(&this->elements[i], &this->elements[j]);
//		}
//
//	// swap pivot with the greater element at i
//	swap(&this->elements[i + 1], &this->elements[right]);
//
//	// return the partition point
//	return (i + 1);
//}
//
//
//void SortedMap::quickSort(int left, int right)
//{
//	if (left < right)
//	{
//		int pi = partition(left, right);
//		quickSort(left, pi - 1);
//		quickSort(pi + 1, right);
//	}
//}


void SortedMap::replace(TKey k, TValue oldValue, TValue newValue)
{
	int index = 0, found = 0;
	while (index < this->size() && !found)
	{
		if (this->elements[index].first == k)
		{
			if (this->elements[index].second == oldValue)
				this->elements[index].second = newValue;
			found = 1;
		}
		else
			index++;
	}
}
// Best case: Theta(1) -> the element mapped to key k is the first in the map
// Worst case: Theta(nrelements) -> the element mapped to key k is the last in the map
// Average case: Theta(nrelements)
// => Overall complexity: O(nrelements)
