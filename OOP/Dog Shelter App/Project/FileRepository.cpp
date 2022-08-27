#include "FileRepository.h"

FileRepository::FileRepository(std::string file_name)
{
	this->fileName = file_name;
	this->load_file();
}

void FileRepository::load_file()
{
	std::ifstream fin(this->fileName);
	Dog dog;
	while (fin >> dog)
		this->add(dog);
	fin.close();
}

void FileRepository::save_file()
{
	std::ofstream fout(this->fileName);
	std::vector<Dog> dogs = this->getList();
	for (auto dog : dogs)
		fout << dog << std::endl;
	fout.close();
}

void FileRepository::add(const Dog& elem)
{
	Repository::add(elem);
	this->save_file();
}

int FileRepository::getSize() const
{
	return Repository::getSize();
}

void FileRepository::remove_by_id(int id)
{
	Repository::remove_by_id(id);
	this->save_file();
}

int FileRepository::search_by_id(int id)
{
	return Repository::search_by_id(id);
}

void FileRepository::modify_dog(Dog& d, const int& new_age, const std::string& new_photo)
{
	Repository::modify_dog(d, new_age, new_photo);
	this->save_file();
}

std::string FileRepository::get_fileName()
{
	return this->fileName;
}
