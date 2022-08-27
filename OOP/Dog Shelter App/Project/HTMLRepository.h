#pragma once
#include "FileRepository.h"

class HTMLRepository : public FileRepository
{
private:
	std::string _fileName;

	void save_file() override;
	void load_file() override;

	void strip_html_line(std::string& str);
	void strip_html_link(std::string& str);
public:
	HTMLRepository(std::string file_name);
	~HTMLRepository() override {};

	void add(const Dog& dog) override;

	void remove_by_id(int id) override;
	void modify_dog(Dog& d, const int& new_age, const std::string& new_photo) override;

	std::string get_fileName() override;
};

