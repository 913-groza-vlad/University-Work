#pragma once
#include "FileRepository.h"

class CSVRepository : public FileRepository
{
private:
	std::string _fileName;

	void load_file() override;
	void save_file() override;
public:
	CSVRepository(std::string file_name);
	~CSVRepository() override {};

	void add(const Dog& dog) override;

	void remove_by_id(int id) override;
	void modify_dog(Dog& d, const int& new_age, const std::string& new_photo) override;

	std::string get_fileName() override;
};

