#include "SortedBag.h"
#include "SortedBagIterator.h"

BSTNode* SortedBag::createNode(TComp e)
{
	BSTNode* node = new BSTNode;
	node->info = e;
	node->left = nullptr;
	node->right = nullptr;
	return node;
}
//Complexity: Theta(1)

bool SortedBag::searchElem(BSTNode* node, TComp elem)
{
	if (node == nullptr)
		return false;
	if (node->info == elem)
		return true;
	if (this->relation(elem, node->info))
		return searchElem(node->left, elem);
	return searchElem(node->right, elem);
}
// Best case: Theta(1) - when elem is the value stored in node
// Worst case: Theta(length) - when all nodes values of the bst are checked
// Average case: Theta(length) -> Overall Complexity: O(length) // actually, complexity: O(h), h = height

BSTNode* SortedBag::insertNode(BSTNode* node, TComp elem)
{
	if (node == nullptr)
		node = createNode(elem);
	else if (this->relation(elem, node->info))
		node->left = insertNode(node->left, elem);
	else
		node->right = insertNode(node->right, elem);
	return node;
}
// Overall Complexity: O(length)

void SortedBag::bstDestructor(BSTNode* root)
{
	if (root == nullptr)
		return;
	this->bstDestructor(root->left);
	this->bstDestructor(root->right);
	delete root;
}
// Complexity: Theta(length)

BSTNode* SortedBag::parent(BSTNode* node) const
{
	BSTNode* c = this->root;
	if (c == node)
		return nullptr;
	while (c != nullptr && c->left != node && c->right != node)
		if (this->relation(node->info, c->info))
			c = c->left;
		else
			c = c->right;
	return c;
}
// Best case: Theta(1) - when c is the root or the node is the left/right of c
// Worst case: Theta(length)
// Average case: Theta(length) -> Overall Complexity: O(length)

BSTNode* SortedBag::successor(BSTNode* node) const
{
	BSTNode* c = node;
	if (node->right != nullptr)
	{
		c = node->right;
		if (c != nullptr)
			while (c->left != nullptr)
				c = c->left;
		return c;
	}
	else
	{
		BSTNode* p = this->parent(c);
		while (p != nullptr && p->left != c)
		{
			c = p;
			p = this->parent(p);
		}
		return p;
	}
}
// Complexity: O(h)

SortedBag::SortedBag(Relation r) {
	this->root = nullptr;
	this->relation = r;
	this->length = 0;
}
// Complexity: Theta(1)

void SortedBag::add(TComp e) {
	if (this->root == nullptr)
		this->root = createNode(e);
	else
		this->insertNode(this->root, e);
	this->length++;
}
// Overall complexity is the same as the complexity of insertNode -> O(length)

bool SortedBag::remove(TComp e) {
	BSTNode* current = this->root;
	while (current != nullptr && current->info != e)
	{
		if (this->relation(e, current->info))
			current = current->left;
		else
			current = current->right;
	}
	if (current == nullptr)
		return false;

	BSTNode* parent = this->parent(current);
	if (this->length == 1)
	{
		delete this->root;
		this->root = nullptr;
	}
	else if (parent == nullptr && (current->left == nullptr || current->right == nullptr)) 
	{
		if (current->left == nullptr) 
		{
			BSTNode* node = current->right;
			delete this->root;
			this->root = node;
		}
		else 
		{
			BSTNode* node = current->left;
			delete this->root;
			this->root = node;
		}
	}
	else if (current->left == nullptr && current->right == nullptr) 
	{
		if (this->relation(current->info, parent->info)) 
		{
			delete parent->left;
			parent->left = nullptr;
		}
		else 
		{
			delete parent->right;
			parent->right = nullptr;
		}
	}
	else if (current->left == nullptr && current->right != nullptr) 
	{
		if (this->relation(current->info, parent->info)) 
			parent->left = current->right;
		else 
			parent->right = current->right;
		delete current;
		current = nullptr;
	}
	else if (current->left != nullptr && current->right == nullptr) 
	{
		if (this->relation(current->info, parent->info))
			parent->left = current->left;
		else
			parent->right = current->left;
		delete current;
		current = nullptr;
	}
	else 
	{
		BSTNode* maxNode = current->left;
		if (maxNode->right == nullptr) 
		{
			current->left = maxNode->left;
			delete maxNode;
			maxNode = nullptr;
		}
		else 
		{
			BSTNode* previousMax = nullptr;
			while (maxNode->right != nullptr) 
			{
				previousMax = maxNode;
				maxNode = maxNode->right;
			}
			current->info = maxNode->info;
			delete previousMax->right;
			previousMax->right = nullptr;
		}
	}
	return length--;
	return true;
}
// Overall Complexity: O(length)

bool SortedBag::search(TComp elem) {
	return this->searchElem(this->root, elem);
}
// Complexity: O(length) (the same complexity as the method searchElem)

int SortedBag::nrOccurrences(TComp elem) const {
	int count = 0;
	BSTNode* currentNode = this->root;
	while (currentNode != nullptr)
	{
		if (currentNode->info == elem)
			count++;
		if (this->relation(elem, currentNode->info))
			currentNode = currentNode->left;
		else
			currentNode = currentNode->right;
	}
	return count;
}
// Complexity: O(length)

int SortedBag::size() const {
	return this->length;
}
// Complexity: Theta(1)

bool SortedBag::isEmpty() const {
	if (this->length == 0)
		return true;
	return false;
}
// Complexity: Theta(1)


SortedBagIterator SortedBag::iterator() const {
	return SortedBagIterator(*this);
}
// Complexity: O(length)

SortedBag::~SortedBag() {
	this->bstDestructor(this->root);
}
// Complexity: Theta(length) (the same complexity as the method bstDestructor


//void SortedBag::iterateTree(BSTNode* current, int& removed)
//{
//	if (current == nullptr)
//		return;
//	if (this->nrOccurrences(current->info) > 1)
//	{
//		this->remove(current->info);
//		removed++;
//	}
//	this->iterateTree(current->left, removed);
//	this->iterateTree(current->right, removed);
//}


int SortedBag::toSet()
{
	BSTNode* current = this->root;
	int removedNr = 0;
	SortedBagIterator it(*this);
	while (it.valid())
	{
		TComp elem = it.getCurrent();
		if (this->nrOccurrences(elem) > 1) 
		{
			this->remove(elem);
			removedNr++;
		}
		it.next();
	}
	
	return removedNr;
}
// Complexity: O(length^2)