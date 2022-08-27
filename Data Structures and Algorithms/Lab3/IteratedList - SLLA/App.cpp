#include <iostream>
#include "ShortTest.h"
#include "ExtendedTest.h"

int main() {
	testRemoveAll();
	testAll();
	testAllExtended();
	std::cout << "Finished LP Tests!" << std::endl;
	system("pause");
}
