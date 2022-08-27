#include "UI.h"
#include <stdio.h>
#include <cstdlib>


UI::UI(Services& s) : serv{ s }
{

}


void UI::addDogUi()
{
	int age = 0, id = 0, r1 = -1, r2 = -1;
	std::string breed, name, photo, str;

	while (r1 == -1)
	{
		std::cout << "\tDog id: ";
		std::getline(std::cin, str);
		try {
			id = std::stoi(str);
			r1 = 0;
		}
		catch (std::invalid_argument) {
			std::cout << "Invalid numerical value!\n";
		}

	}
	std::cout << "\tBreed: ";
	std::getline(std::cin, breed);
	std::cout << "\tName: ";
	std::getline(std::cin, name);
	//std::cout << "\tDog age: ";
	while (r2 == -1)
	{
		std::cout << "\tDog age: ";
		std::getline(std::cin, str);
		try {
			age = std::stoi(str);
			r2 = 0;
		}
		catch (std::invalid_argument) {
			std::cout << "Invalid numerical value!\n";
		}
	}
	//std::cin >> age;
	//std::cin.get();
	std::cout << "\tPhotograph(link): ";
	std::getline(std::cin, photo);
	try {
		int added = this->serv.addDog(id, breed, name, age, photo);
		if (added == 1)
			std::cout << "Dog successfully added!\n";
	}
	catch (ValidationException& ve) {
		std::cout << ve.str();
	}
	catch (RepositoryException& re) {
		std::cout << re.str() << std::endl;
	}
}

void UI::removeDogUi()
{
	int dog_id = 0, r = -1;
	std::string str;
	while (r == -1)
	{
		std::cout << "\tInsert the id of the dog you want to remove: ";
		std::getline(std::cin, str);
		try {
			dog_id = std::stoi(str);
			r = 0;
		}
		catch (std::invalid_argument) {
			std::cout << "Invalid numerical value!\n";
		}
	}
	try {
		this->serv.removeDog(dog_id);
		std::cout << "Dog successfully removed!\n";
	}
	catch (RepositoryException& re) {
		std::cout << re.str();
	}
}

void UI::updateDogUi()
{
	int dog_id = 0, age = 0, r1 = -1, r2 = -1;
	std::string photo, str;
	while (r1 == -1)
	{
		std::cout << "\tInsert the id of the dog you want to update: ";
		std::getline(std::cin, str);
		try {
			dog_id = std::stoi(str);
			r1 = 0;
		}
		catch (std::invalid_argument) {
			std::cout << "Invalid numerical value!\n";
		}
	}

	Repository* dogs = this->serv.getAll();

	int pos = 0;
	try {
		pos = dogs->search_by_id(dog_id);
	}
	catch(RepositoryException &re) {
		std::cout << re.str() << std::endl;
		return;
	}
	Dog& dog = dogs->getDog(pos);

	while (r2 == -1)
	{
		std::cout << "\tUpdated dog age: ";
		std::getline(std::cin, str);
		try {
			age = std::stoi(str);
			r2 = 0;
		}
		catch (std::invalid_argument) {
			std::cout << "Invalid numerical value!\n";
		}
	}
	std::cout << "\tUpdated photograph(link): ";
	std::getline(std::cin, photo);
	try {
		this->serv.updateDog(dog_id, dog.get_breed(), dog.get_name(), age, photo);
	}
	catch (ValidationException& ve) {
		std::cout << ve.str();
		return;
	}
	catch (RepositoryException& re) {
		std::cout << re.str() << std::endl;
		return;
	}
	//std::cout << dogs.getDog(pos).toStr() <<  std::endl;
	std::cout << "Dog successfully updated!\n";
}

void UI::displayDogs()
{
	Repository* dogs = this->serv.getAll();
	std::vector<Dog> doggies = dogs->getList();
	if (dogs->getSize() == 0)
	{
		std::cout << "There is no dog in the shelter in this moment!\n";
		return;
	}
	for (auto dog : doggies)
	{
		std::cout << dog.toStr();
	}
}

void UI::displayAdoptionList()
{
	Repository* adoptionList = this->serv.getAdoptionList();
	std::vector<Dog> adopted = adoptionList->getList();
	if (adoptionList->getSize() == 0)
	{
		std::cout << "There is no dog on the adoption list!\n";
		return;
	}
	for (auto dog : adopted)
	{
		std::cout << dog.toStr();
	}
}

void UI::dogsOfBreed()
{
	std::string breed, str;
	int age = 0, r = -1, found = 0;
	std::cout << "\tInsert a breed: ";
	std::getline(std::cin, breed);
	while (r == -1)
	{
		std::cout << "\tInsert a number (age in years): ";
		std::getline(std::cin, str);
		try {
			age = std::stoi(str);
			r = 0;
		}
		catch (std::invalid_argument) {
			std::cout << "Invalid numerical value!\n";
		}
	}
	Repository* doggies = this->serv.filteredDogs(breed, age);
	if (doggies->getSize() == 0)
		std::cout << "No dog with of breed " << breed << " with age less than " << age << " is in the shelter\n";
	else
		this->adoptDogUi(doggies);
	delete doggies;
}

void UI::adoptDogUi(Repository* dogs)
{
	int position = 0, find_dog = 0;
	std::string adopt;
	while (!find_dog)
	{
		Dog& dog = dogs->getDog(position);
		std::cout << "\n" << dog.toStr();
		adoptionMenu();
		std::cout << ">>> ";
		std::getline(std::cin, adopt);
		while (adopt.compare("adopt") != 0 && adopt.compare("next") != 0 && adopt.compare("0") != 0)
		{
			std::cout << "Invalid option!\n";
			std::cout << ">>> ";
			std::getline(std::cin, adopt);
		}
		if (adopt.compare("adopt") == 0)
		{
			std::cout << dog.get_name() << " was adopted!\n";
			this->serv.adoptDog(dogs, position);
			find_dog = 1;
		}
		else if (adopt.compare("next") == 0)
		{
			position++;
			if (position == dogs->getSize())
				position = 0;
		}
		else if (adopt.compare("0") == 0)
		{
			find_dog = 1;
			std::cout << "Back to the User mode!\n";
		}
	}
}

void UI::adminMenu()
{
	std::cout << "\n\t1 -> add a dog\n";
	std::cout << "\t2 -> remove a dog\n";
	std::cout << "\t3 -> update data for a dog\n";
	std::cout << "\t4 -> display all dogs from the shelter\n";
	std::cout << "\t0 -> exit Administrator mode\n\n";
}

void UI::userMenu()
{
	std::cout << "\n\t1 -> see the dogs in the shelter (one by one)\n";
	std::cout << "\t2 -> see all the dogs of a given breed, having the age less than a number\n";
	std::cout << "\t3 -> see the adoption list\n";
	std::cout << "\t4 -> open the file with the adoption list\n";
	std::cout << "\t0 -> exit User mode\n\n";
}

void UI::adoptionMenu()
{
	std::cout << "\n\tadopt -> adopt this dog   \tnext -> see the next dog\n";
	std::cout << "\t\t    0 -> end looking for a dog\n\n";
}

void UI::print_menu()
{
	std::cout << "\n\t    ~'Adopt a Pet' Shelter~\n";
	std::cout << "\tadmin -> enter Administrator mode\n";
	std::cout << "\tuser -> enter User mode\n";
	std::cout << "\t0 -> exit the application\n\n";
}


void UI::start()
{
	int not_end = 1;
	std::string cmd;
	//this->serv.create_Doglist();
	while (not_end)
	{
		print_menu();
		std::cout << "Input one of the above commands: ";
		std::getline(std::cin, cmd);
		if (cmd.compare("admin") == 0)
		{
			int end_admin = 1;
			std::string cmdAd;
			std::cout << "You entered Administrator mode\n";
			while (end_admin)
			{
				adminMenu();
				std::cout << "Input one of the above commands: ";
				std::getline(std::cin, cmdAd);
				if (cmdAd.compare("1") == 0)
					this->addDogUi();
				else if (cmdAd.compare("2") == 0)
					this->removeDogUi();
				else if (cmdAd.compare("3") == 0)
					this->updateDogUi();
				else if (cmdAd.compare("4") == 0)
					this->displayDogs();
				else if (cmdAd.compare("0") == 0)
				{
					end_admin = 0;
					std::cout << "Back to the main menu!\n";
				}
				else
					std::cout << "Invalid command!\n";
			}
		}
		else if (cmd.compare("user") == 0)
		{
			int end_user = 1;
			std::string cmdUs;
			Repository* dogs = this->serv.getAll();
			std::cout << "You entered User mode!\n";
			while (end_user)
			{
				userMenu();
				std::cout << "Input one of the above commands: ";
				std::getline(std::cin, cmdUs);
				if (cmdUs.compare("0") == 0)
				{
					end_user = 0;
					std::cout << "Back to the main menu!\n";
				}
				else if (cmdUs.compare("1") == 0)
					this->adoptDogUi(dogs);
				else if (cmdUs.compare("2") == 0)
					this->dogsOfBreed();
				else if (cmdUs.compare("3") == 0)
					this->displayAdoptionList();
				else if (cmdUs.compare("4") == 0)
				{
					Repository* dogs = this->serv.getAdoptionList();
					std::string file = dogs->get_fileName();
					std::string command;
					if (file.find(".htm") != -1)
					{
						command = "start \"C:\\Program Files(x86)\\Google\\Chrome\\Application\\chrome\" ";
						command += file;
						system(command.c_str());
						std::cout << "File successfully opened!\n";
					}
					else if (file.find(".csv") != -1)
					{
						command = "start excel ";
						command += file;
						system(command.c_str());
						std::cout << "File successfully opened!\n";
					}
					else
						std::cout << "No file provided or invalid type of file!\n";
				}
				else
					std::cout << "Invalid command!\n";
			}
		}
		else if (cmd.compare("0") == 0)
			not_end = 0;
		else
			std::cout << "Invalid command!\n";
	}
}