#include "SortedBag.h"
#include "SortedBagIterator.h"
#include <iostream>

SortedBag::SortedBag(Relation r) {
	this->relation = r;
	this->head = nullptr;
	this->tail = nullptr;
	this->nrelems = 0;
}
// Theta(1)

void SortedBag::add(TComp e) {
	PNode temp, ptr;
	ptr = new DLLNode(e, nullptr, nullptr);
	if (this->head == nullptr)
		this->head = ptr;
	else if (!this->search(e))
	{
		temp = this->head;
		while (temp->next != nullptr)
			temp = temp->next;
		temp->next = ptr;
		ptr->prev = temp;
		ptr->next = nullptr;
	}
	else
	{
		temp = head;
		while (temp->data != e)
			temp = temp->next;
		temp->frequency++;
	}
	this->nrelems++;
}
// Best case: Theta(1) / Worst case: Theta(nrelems) / Average case: Theta(nrelems)
// => Overall complexity: O(nrelems)


//bool SortedBag::remove(TComp e) {
//	PNode currentNode = this->head;
//	while (currentNode != nullptr && currentNode->data != e)
//		currentNode = currentNode->next;
//	PNode deletedNode = currentNode;
//	if (currentNode != nullptr)
//	{
//		if (currentNode->frequency == 1)
//		{
//			if (currentNode == this->head)
//			{
//				if (currentNode == this->tail)
//				{
//					this->head = nullptr;
//					this->tail = nullptr;
//				}
//				else
//				{
//					this->head = this->head->next;
//					this->head->prev = nullptr;
//				}
//			}
//			else if (currentNode == this->tail)
//			{
//				this->tail = this->tail->prev;
//				this->tail = nullptr;
//			}
//			else
//			{
//				currentNode->prev->next = currentNode->next;
//				currentNode->next->prev = currentNode->prev;
//			}
//			delete deletedNode;
//		}
//		else if (currentNode->frequency > 1)
//			currentNode->frequency--;
//		this->nrelems--;
//		return true;
//	}
//	return false;
//}


void SortedBag::deleteNode(PNode del)
{
	if (this->head == nullptr || del == nullptr)
		return;
	if (this->head == del)
		this->head = del->next;

	if (del->next != nullptr)
		del->next->prev = del->prev;
	if (del->prev != nullptr)
		del->prev->next = del->next;

	free(del);
}
// Complexity: Theta(1)


bool SortedBag::remove(TComp e) {
	PNode currentNode = this->head;
	while (currentNode != nullptr && currentNode->data != e)
		currentNode = currentNode->next;
	//PNode deletedNode = currentNode;
	if (currentNode != nullptr)
	{
		if (currentNode->frequency == 1)
			this->deleteNode(currentNode);
		else if (currentNode->frequency > 1)
			currentNode->frequency--;
		this->nrelems--;
		return true;
	}
	return false;
}
// Best case: Theta(1) / Worst case: Theta(nrelems) / Average case: Theta(nrelems)
// Overall complexity: O(nrelems)


bool SortedBag::search(TComp elem) const {
	PNode currentNode = this->head;
	while (currentNode != nullptr)
	{
		if (currentNode->data == elem)
			return true;
		currentNode = currentNode->next;
	}
	return false;
}
// Best case: Theta(1) (when elem is the data of the first node in the bag) / Worst case: Theta(nrelems) / Average case: Theta(nrelems)
// Overall complexity: O(nrelems)


int SortedBag::nrOccurrences(TComp elem) const {
	if (this->head == nullptr)
		return 0;
	if (!this->search(elem))
		return 0;
	PNode ptr = this->head;
	while (ptr != nullptr)
	{
		if (ptr->data == elem)
			return ptr->frequency;
		ptr = ptr->next;
	}
}
// Best case: Theta(1) (elem is the data of the first node in the bag) / Worst case: Theta (nrelems) / Average case: Theta(nrelems)
// Overall complexity: O(nrelems)


int SortedBag::size() const{
	return this->nrelems;
}
// Theta(1) - constant complexity


bool SortedBag::isEmpty() const {
	if (this->head == nullptr)
		return true;
	return false;
}
// Theta(1)


SortedBagIterator SortedBag::iterator() {
	this->sort();
	return SortedBagIterator(*this);
}
// iterator() complexity is the same as the sort function's => Overall complexity: O(nrelements^2)


SortedBag::~SortedBag() {
	while (head != nullptr)
	{
		PNode p = head;
		head = head->next;
		delete p;
	}
}
// Theta(nrelems)


void SortedBag::sort()
{
	int swapped = 0, i = 0;
	PNode node, ptr = nullptr;
	if (this->head == nullptr)
		return;
	do {
		swapped = 0;
		node = this->head;
		while (node->next != ptr)
		{
			if (!this->relation(node->data, node->next->data))
			{
				std::swap(node->data, node->next->data);
				std::swap(node->frequency, node->next->frequency);
				swapped = 1;
			}
			node = node->next;
		}
		ptr = node;

	} while (swapped);
}
//		-> Bubble sort
// Best case: Theta(nrelems), when the elements are already sorted
// Worst case: Theta(nrelems^2), when elements are sorted in reverse order of the relation
// Average case: Theta(nrelems^2) => Overall complexity: O(nrelems)


void SortedBag::addAll(const SortedBag& b)
{
	PNode current = b.head;
	while (current != nullptr)
	{
		TComp elem = current->data;
		int freq = current->frequency;
		while (freq > 0)
		{
			this->add(elem);
			freq--;
		}
		current = current->next;
	}
}
// Overall Complexity: Theta(nrelements*b.nrelems^2)



//void SortedBag::sortedInsert(PNode head, PNode newNode)
//{
//	PNode current;
//	if (head == nullptr)
//		head = newNode;
//	else if (this->relation(head->data, newNode->data))
//	{
//		newNode->next = head;
//		newNode->next->prev = newNode;
//		head = newNode;
//	}
//	else
//	{
//		current = head;
//		while (current->next != nullptr && !this->relation(current->next->data, newNode->data))
//			current = current->next;
//		newNode->next = current->next;
//
//		if (current->next != nullptr)
//			newNode->next->prev = newNode;
//		current->next = newNode;
//		newNode->prev = current;
//	}
//
//}