#include "ShortTest.h"
#include "MultiMap.h"
#include "MultiMapIterator.h"
#include <assert.h>
#include <vector>
#include<iostream>

void testMostFrequent()
{
	MultiMap mm;
	assert(mm.isEmpty() == true);
	assert(mm.mostFrequent() == NULL_TVALUE);

	mm.add(1, 10);
	mm.add(2, 200);
	mm.add(3, 10);
	mm.add(1, 500);
	mm.add(4, 20);
	mm.add(7, 10);
	mm.add(2, 20);

	assert(mm.size() == 7);
	assert(mm.mostFrequent() == 10);

	assert(mm.remove(3, 10) == true);
	assert(mm.remove(7, 10) == true);
	assert(mm.mostFrequent() == 20);
	assert(mm.size() == 5);
}

void testAll() {
	MultiMap m;
	m.add(1, 100);
	m.add(2, 200);
	m.add(3, 300);
	m.add(1, 500);
	m.add(2, 600);
	m.add(4, 800);

	assert(m.size() == 6);

	assert(m.remove(5, 600) == false);
	assert(m.remove(1, 500) == true);

	assert(m.size() == 5);

    vector<TValue> v;
	v=m.search(6);
	assert(v.size()==0);

	v=m.search(1);
	assert(v.size()==1);

	assert(m.isEmpty() == false);

	MultiMapIterator im = m.iterator();
	assert(im.valid() == true);
	while (im.valid()) {
		im.getCurrent();
		im.next();
	}
	assert(im.valid() == false);
	im.first();
	assert(im.valid() == true);
}
