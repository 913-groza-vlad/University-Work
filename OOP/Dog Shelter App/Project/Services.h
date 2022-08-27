#pragma once
#include "Repository.h"
#include "FileRepository.h"
#include "CSVRepository.h"
#include "HTMLRepository.h"
#include <algorithm>
#include "Validation.h"
#include "Action.h"

class Services
{
private:
	Repository* repo;
	Repository* dogsList;
	Validator valid;
	std::vector < std::unique_ptr<Action>> undoStack;
	std::vector < std::unique_ptr<Action>> redoStack;

public:
	// Constructor of an instance of class Services
	Services(Repository* r, Repository* list, Validator v);

	/// <summary>
	/// Method that adds a Dog in the list and returns
	/// an integer that suggests if it was successfully added or not
	/// </summary>
	/// <param name="id_dog"> an integer, the id of a dog </param>
	/// <param name="breed"> a string, the breed of a dog </param>
	/// <param name="name"> a string, the name of a dog </param>
	/// <param name="age"> an integer, the age (in years) of a dog</param>
	/// <param name="link"> a string, a link to the photograph of a dog</param>
	/// <returns> 0, if one of the params is not valid; 
	///			  1, if the dog was successfully added
	///			 -1, if the dog with this id already exists </returns>
	int addDog(int id_dog, std::string breed, std::string name, int age, std::string link);

	/// <summary>
	/// Method that removes a dog by returning the method 'remove_by_id'
	/// </summary>
	/// <param name="id"> an int, the id of the dog to be removed </param>
	/// <returns></returns>
	void removeDog(int id);

	/// <summary>
	/// Method for updating the information about a Dog and return the updated dog
	/// </summary>
	/// <param name="id_dog"> an integer, the id of a dog </param>
	/// <param name="breed"> a string, the breed of a dog </param>
	/// <param name="name"> a string, the name of a dog </param>
	/// <param name="new_age"> an integer, the updated age (in years) of a dog</param>
	/// <param name="new_link"> a string, a updated link to the photograph of a dog</param>
	/// <returns> doggie, a Dog </returns>
	void updateDog(const int& id_dog, const std::string& breed, const std::string& name, const int& new_age, const std::string& new_link);

	// Method that returns a reference to the repository stored in Service class
	Repository* getAll();
	// Creates ten entries in order to be available at the start of the program
	void create_Doglist();

	// Method that returns a reference to the adoption list (a repository) 
	Repository* getAdoptionList();

	/// <summary>
	/// Method which adds a dog on position pos in the repository
	/// to the adoption list and removes it from the repo
	/// </summary>
	/// <param name="pos"> an integer, the position of the dog to be adopted </param>
	void adoptDog(Repository* dogs, int pos);

	/// <summary>
	/// Method that filter the repo of dogs by returning those dogs
	/// of the given breed with the age less than number 'age'
	/// </summary>
	/// <param name="breed"> a string, the breed of dog </param>
	/// <param name="age"> an integer, the age of a dog </param>
	/// <returns> a Repository with the dogs having the required properties </returns>
	Repository* filteredDogs(std::string breed, int age);

	void undo();
	void redo();

	~Services();
};
