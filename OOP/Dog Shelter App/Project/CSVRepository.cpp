#include "CSVRepository.h"


CSVRepository::CSVRepository(std::string file_name): _fileName { file_name }
{
	this->load_file();
}

void CSVRepository::load_file()
{
	std::ifstream fin(this->_fileName);
	Dog dog;
	while (fin >> dog)
		this->add(dog);
	fin.close();
}

void CSVRepository::save_file()
{
	std::ofstream fout(this->_fileName);
	std::vector<Dog> adoptList = this->getList();
	for (auto& adoptedDog : adoptList)
		fout << adoptedDog << std::endl;
	fout.close();
}

void CSVRepository::add(const Dog& dog)
{
	FileRepository::add(dog);
	this->save_file();
}

void CSVRepository::remove_by_id(int id)
{
	FileRepository::remove_by_id(id);
	this->save_file();
}

void CSVRepository::modify_dog(Dog& d, const int& new_age, const std::string& new_photo)
{
	FileRepository::modify_dog(d, new_age, new_photo);
	this->save_file();
}

std::string CSVRepository::get_fileName()
{
	return this->_fileName;
}
