#include "HTMLRepository.h"

HTMLRepository::HTMLRepository(std::string file_name)
{
	this->_fileName = file_name;
	this->load_file();
}

void HTMLRepository::save_file()
{
	std::ofstream fout(this->_fileName);
	std::vector<Dog> adoptList = this->getList();

	fout << "<!DOCTYPE html>\n\n";
	fout << "<HTML>\n";
	fout << "<HEAD>\n\n";
	fout << "<TITLE>Adopt a Pet</TITLE>\n";
	fout << "</HEAD>\n";
	fout << "<BODY>\n";
	fout << "\t<table border=\"1\">\n";
	fout << "\t\t<tr>\n";
	fout << "\t\t\t<td>Dog id</td>\n";
	fout << "\t\t\t<td>Breed</td>\n";
	fout << "\t\t\t<td>Name</td>\n";
	fout << "\t\t\t<td>Age</td>\n";
	fout << "\t\t\t<td>Photograph</td>\n";
	fout << "\t\t</tr>\n\n";
	
	for (auto& dog : adoptList)
	{
		fout << "\t\t<tr>\n";
		fout << "\t\t\t<td>" << dog.get_dogId() << "</td>\n";
		fout << "\t\t\t<td>" << dog.get_breed() << "</td>\n";
		fout << "\t\t\t<td>" << dog.get_name() << "</td>\n";
		fout << "\t\t\t<td>" << dog.get_age() << "</td>\n";
		fout << "\t\t\t<td><a href=\"" << dog.get_photo() << "\">Link</a></td>\n";
		fout << "\t\t</tr>\n";
	}

	fout << "\t</table>\n";
	fout << "</BODY>\n";
	fout << "</HTML>";
	fout.close();
}

void HTMLRepository::load_file()
{
	std::ifstream fin(this->_fileName);
	std::string line;
	for (int i = 0; i < 17; i++)
		std::getline(fin, line);

	while (true)
	{
		std::getline(fin, line);
		strip(line);
		if (line != "<tr>")
			break;

		int id = 0, age = 0;
		std::string name, breed, link, sid, sage;
		std::getline(fin, sid);
		strip_html_line(sid);
		std::getline(fin, breed);
		strip_html_line(breed);
		std::getline(fin, name);
		strip_html_line(name);
		std::getline(fin, sage);
		strip_html_line(sage);
		std::getline(fin, link);
		strip_html_link(link);

		for (auto ch : sid)
			id = id * 10 + ch - '0';

		for (auto ch : sage)
			age = age * 10 + ch - '0';

		Dog dog(id, breed, name, age, link);
		this->add(dog);

		std::getline(fin, line);
	}
	fin.close();
}

void HTMLRepository::add(const Dog& dog)
{
	FileRepository::add(dog);
	this->save_file();
}

void HTMLRepository::remove_by_id(int id)
{
	FileRepository::remove_by_id(id);
	this->save_file();
}

void HTMLRepository::modify_dog(Dog& d, const int& new_age, const std::string& new_photo)
{
	FileRepository::modify_dog(d, new_age, new_photo);
	this->save_file();
}

std::string HTMLRepository::get_fileName()
{
	return this->_fileName;
}



void HTMLRepository::strip_html_line(std::string& str)
{
	strip(str);
	str = str.substr(0, str.size() - 5);
	reverse(str.begin(), str.end());
	str = str.substr(0, str.size() - 4);
	reverse(str.begin(), str.end());
	strip(str);
}

void HTMLRepository::strip_html_link(std::string& str)
{
	strip(str);
	str = str.substr(0, str.size() - 15);
	reverse(str.begin(), str.end());
	str = str.substr(0, str.size() - 13);
	reverse(str.begin(), str.end());
	strip(str);
}
