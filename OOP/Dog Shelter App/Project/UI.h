#pragma once
#include "Services.h"
#include <iostream>

class UI
{
private:
	Services& serv;

	void adminMenu();
	void userMenu();
	void adoptionMenu();
	void print_menu();

	void addDogUi();
	void removeDogUi();
	void updateDogUi();
	void displayDogs();
	void displayAdoptionList();
	void dogsOfBreed();
	void adoptDogUi(Repository* dogs);

public:
	UI(Services& s);
	void start();
};
