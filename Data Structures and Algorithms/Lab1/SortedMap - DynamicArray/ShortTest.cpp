#include <assert.h>

#include "SortedMap.h"
#include "SMIterator.h"
#include "ShortTest.h"
#include <exception>
using namespace std;

bool relatie1(TKey cheie1, TKey cheie2) {
	if (cheie1 <= cheie2) {
		return true;
	}
	else {
		return false;
	}
}

void testReplace() {
    SortedMap sm(relatie1);
    sm.add(1, 2);
    assert(sm.size() == 1);
    sm.add(3, 5);
    sm.add(7, 2);
    sm.replace(3, 5, 9);
    assert(sm.search(3) == 9);

    TValue v = sm.add(3, 4);
    assert(v == 9);

    assert(sm.search(3) == 4);
    sm.replace(3, 9, 8);
    assert(sm.search(3) == 4);
    sm.replace(3, 4, 2);
    assert(sm.search(3) == 2);

    sm.replace(12, 3, 2);
    assert(sm.search(12) == NULL_TVALUE);
}

void testAll(){
	SortedMap sm(relatie1);
	assert(sm.size() == 0);
	assert(sm.isEmpty());
    sm.add(1,2);
    assert(sm.size() == 1);
    assert(!sm.isEmpty());
    assert(sm.search(1)!=NULL_TVALUE);
    TValue v =sm.add(1,3);
    assert(v == 2);
    assert(sm.search(1) == 3);
    SMIterator it = sm.iterator();
    it.first();
    while (it.valid()){
    	TElem e = it.getCurrent();
    	assert(e.second != NULL_TVALUE);
    	it.next();
    }
    assert(sm.remove(1) == 3);
    assert(sm.isEmpty());

    testReplace();
}

