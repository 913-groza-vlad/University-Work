#pragma once
#include "Dog.h"
#include <vector>
#include "Exceptions.h"

class Repository
{
protected:
	std::vector<Dog> data;
	std::vector<Dog>::iterator it;

	virtual void load_file() {};
	virtual void save_file() {};

public:
	Repository() {};

	/// Method that add an element of class Dog to the repository
	virtual void add(const Dog& elem);

	// Method that returns the length (an integer) of the repository 
	virtual int getSize() const;

	// Method that returns a reference to the Dog 
	//from the position pos in the repository  
	virtual Dog& getDog(int pos);
	virtual Dog get_dog(int pos) { return this->data[pos]; };

	// Method that removes the element with the specified id
	// id - an integer, the id of a dog
	// returns: 1, if the element was removed; 0, otherwise (the dog with id 'id' is not in the repo)
	virtual void remove_by_id(int id);

	/// <summary>
	/// Method which search the dog with the id 'id' in the repo
	/// and returns its position in the list or -1 if the dog does
	/// not appper in the list
	/// </summary>
	virtual int search_by_id(int id);
	virtual void modify_dog(Dog& d, const int& new_age, const std::string& new_photo);

	virtual std::vector<Dog> getList();
	virtual void set_data(std::vector<Dog> new_data);

	virtual std::string get_fileName() { return ""; };

	virtual ~Repository() = default;

};
