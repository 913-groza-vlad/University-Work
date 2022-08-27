#pragma once
#include "SortedBag.h"

class SortedBag;

class SortedBagIterator
{
	friend class SortedBag;

private:
	const SortedBag& bag;
	SortedBagIterator(const SortedBag& b);

	SortedBag::PNode current_node, first_node;
	int cont;

public:
	TComp getCurrent();
	bool valid();
	void next();
	void first();
};

