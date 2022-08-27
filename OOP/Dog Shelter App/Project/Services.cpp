#include "Services.h"

Services::Services(Repository* r, Repository* list, Validator v) : repo{ r }, dogsList{ list }, valid{ v }
{

}

int Services::addDog(int id_dog, std::string breed, std::string name, int age, std::string link)
{
	Dog d{ id_dog, breed, name, age, link };
	this->valid.validateDog(d);
	//for (int i = 0; i < this->repo.getSize(); i++)
	for (auto dog : this->repo->getList())
	{
		//Dog dog = this->repo.getDog(i);
		if (d.get_dogId() == dog.get_dogId())
			throw RepositoryException("There exists another dog having this id!");
	}
	this->repo->add(d);
	std::unique_ptr<Action> a = std::make_unique<ActionAdd>(d, this->repo);
	this->undoStack.push_back(std::move(a));
	return 1;
}

void Services::removeDog(int id)
{
	Dog d = this->repo->getDog(this->repo->search_by_id(id));
	this->repo->remove_by_id(id);
	std::unique_ptr<Action> a = std::make_unique<ActionRemove>(d, this->repo);
	this->undoStack.push_back(std::move(a));
}

void Services::updateDog(const int& id_dog, const std::string& breed, const std::string& name, const int& new_age, const std::string& new_link)
{
	int position = this->repo->search_by_id(id_dog);
	Dog updated_dog { id_dog, breed, name, new_age, new_link };
	this->valid.validateDog(updated_dog);
	Dog& doggie = this->repo->getDog(position);
	int old_age = doggie.get_age();
	std::string old_link = doggie.get_photo();
	this->repo->modify_dog(doggie, new_age, new_link);
	std::unique_ptr<Action> a = std::make_unique<ActionUpdate>(doggie, this->repo, old_age, new_age, old_link, new_link);
	this->undoStack.push_back(std::move(a));
}

Repository* Services::getAll()
{
	return this->repo;
}

void Services::create_Doglist()
{
	Dog d{ 1, "Bulldog", "Mikey", 3, "https://www.dogtime.com/assets/uploads/2011/01/file_23140_bulldog-460x290.jpg" };
	this->repo->add(d);
	Dog d1{ 2, "Chihuahua", "Pinky", 1, "https://supervetsolutions.ro/wp-content/uploads/2020/06/3027438-1920x1920.jpg" };
	this->repo->add(d1);
	//Dog d2{ 3, "French Bulldog", "Chelutu", 4, "https://wp-content/uploads/2021/08/Chelutu.jpg" };
	//this->repo->add(d2);
	//Dog d3{ 4, "Husky", "Aki", 0, "https://dogtime.com/dog-breeds/huskita" };
	//this->repo->add(d3);
	Dog d4{ 5, "Golden Retriever", "Jack", 7, "https://www.dogtime.com/assets/uploads/2011/01/file_22980_golden-retriever-300x189.jpg" };
	this->repo->add(d4);
	//Dog d5{ 6, "Chihuahua", "Leia", 3, "https://dogtime.com/dog-breeds/chihuahua1" };
	//this->repo->add(d5);
	Dog d6{ 7, "Rottweiler", "Max", 2, "https://www.dogtime.com/assets/uploads/2011/01/file_22942_rottweiler-300x189.jpg" };
	this->repo->add(d6);
	Dog d7{ 8, "Bulldog", "Spike", 5, "https://dogsbestlife.com/wp-content/uploads/2020/05/English-bulldog-scaled.jpeg" };
	this->repo->add(d7);
	Dog d8{ 9, "Shiba Inu", "Jackie", 11, "https://www.dogtime.com/assets/uploads/2011/01/file_23160_shiba-inu-300x189.jpg" };
	this->repo->add(d8);
	Dog d9{ 10, "Bichon", "Labus", 3, "https://www.dogtime.com/assets/uploads/2011/01/file_23136_bichon-frise-300x189.jpg" };
	this->repo->add(d9);
	Dog d10{ 11, "Saint Bernard", "Bernie", 9, "https://www.dogtime.com/assets/uploads/2011/01/file_22944_saint-bernard-300x189.jpg" };
	this->repo->add(d10);
	Dog d11{ 12, "Husky", "Bolt", 6, "https://extrucan.ro/wp-content/uploads/2021/08/Siberian-Husky.jpg" };
	this->repo->add(d11);
	Dog d12{ 13, "Collie", "Candy", 1, "https://www.dogtime.com/assets/uploads/2011/01/file_23186_collie-300x189.jpg" };
	this->repo->add(d12);
	Dog d13{ 14, "Maltese", "Sindy", 4, "https://www.dogtime.com/assets/uploads/2011/01/file_23114_maltese-300x189.jpg" };
	this->repo->add(d13);
	Dog d14{ 15, "Husky", "Stormi", 2, "https://thehappypuppysite.com/wp-content/uploads/2015/09/The-Siberian-Husky-HP-long.jpg" };
	this->repo->add(d14);
}

Repository* Services::getAdoptionList()
{
	return this->dogsList;
}

void Services::adoptDog(Repository* dogs, int pos)
{
	Dog d = dogs->getDog(pos);
	this->dogsList->add(d);
	this->repo->remove_by_id(d.get_dogId());
}

Repository* Services::filteredDogs(std::string breed, int age)
{
	Repository* filtered = new Repository();
	Repository* dogs = this->getAll();
	std::vector<Dog> doggies(dogs->getSize()), copy = dogs->getList();
	/*for (int i = 0; i < dogs.getSize(); i++)
	{
		Dog& dog = dogs.getDog(i);
		if ((dog.get_breed().find(breed) != -1 || breed.length() == 0) && dog.get_age() < age)
		{
			filtered.add(dog);
		}
	}*/
	auto it = std::copy_if(copy.begin(), copy.end(), doggies.begin(), [breed, age](const Dog& dog) { return (dog.get_breed().find(breed) != -1 || breed.length() == 0) && dog.get_age() < age; });
	doggies.resize(std::distance(doggies.begin(), it));
	filtered->set_data(doggies);
	return filtered;
}

void Services::undo()
{
	if (this->undoStack.size() == 0)
		throw RepositoryException("Nothing to undo!");
	std::unique_ptr<Action> action = std::move(undoStack.back());
	action->executeUndo();
	this->redoStack.push_back(std::move(action));
	this->undoStack.erase(undoStack.end() - 1);
}

void Services::redo()
{
	if (this->redoStack.size() == 0)
		throw RepositoryException("Nothing to redo!");
	std::unique_ptr<Action> action = std::move(redoStack.back());
	action->executeRedo();
	this->undoStack.push_back(std::move(action));
	this->redoStack.erase(redoStack.end() - 1);
}

Services::~Services()
{
	delete this->repo;
	delete this->dogsList;
}


