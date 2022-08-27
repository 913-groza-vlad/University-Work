#pragma once

#include "Repository.h"
#include <fstream>

class FileRepository : public Repository {

private:
	std::string fileName;

	void load_file() override;
	void save_file() override;
public:
	FileRepository() {};
	FileRepository(std::string file_name);

	~FileRepository() override {};

	/// Method that add an element of class Dog to the repository
	void add(const Dog& elem) override;

	// Method that returns the length (an integer) of the repository 
	int getSize() const override;

	void remove_by_id(int id) override;

	int search_by_id(int id) override;
	void modify_dog(Dog& d, const int& new_age, const std::string& new_photo) override;

	std::string get_fileName() override;

};
