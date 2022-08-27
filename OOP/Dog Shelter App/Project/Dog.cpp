#include "Dog.h"

Dog::Dog(const int id, const std::string& breed, const std::string& name, const int age, const std::string& link) : dog_id{ id }, breed{ breed }, name{ name }, age{ age }, photograph{ link }
{
}

int Dog::get_dogId() const
{
	return this->dog_id;
}

std::string Dog::get_breed() const
{
	return this->breed;
}

std::string Dog::get_name() const
{
	return this->name;
}

int Dog::get_age() const
{
	return this->age;
}

std::string Dog::get_photo() const
{
	return this->photograph;
}

void Dog::set_age(const int& new_age)
{
	this->age = new_age;
}

void Dog::set_photo(const std::string& new_photo)
{
	this->photograph = new_photo;
}

bool Dog::valid()
{
	if (this->get_age() < 0 || this->get_dogId() <= 0 || this->name.length() < 2 || this->breed.length() < 2 || this->photograph.length() < 3)
		return false;

	return true;
}

std::string Dog::toStr()
{
	std::stringstream ss;
	ss << "Dog " << get_dogId() << ", named " << get_name() << ", breed: " << get_breed() << ", age: " << get_age() << " years;  Photo: " << get_photo() << "\n";
	std::string str = ss.str();
	return str;
}


///.........................................

void strip(std::string& str)
{
    while (!str.empty() && (str.back() == ' ' || str.back() == '\t' || str.back() == '\n'))
        str.pop_back();
    reverse(str.begin(), str.end());
    while (!str.empty() && (str.back() == ' ' || str.back() == '\t' || str.back() == '\n'))
        str.pop_back();
    reverse(str.begin(), str.end());
}

std::istream& operator>>(std::istream& reader, Dog& dog)
{
    std::string line;
    std::getline(reader, line);
    if (line.empty())
        return reader;
    strip(line);
    std::stringstream ss(line);

    int id = 0, age = 0;
    std::string name, breed, link, sid, sage;

    std::getline(ss, sid, ',');
    strip(sid);
    std::getline(ss, breed, ',');
    strip(breed);
    std::getline(ss, name, ',');
    strip(name);
    std::getline(ss, sage, ',');
    strip(sage);
    std::getline(ss, link, ',');
    strip(link);

    for (auto ch : sid)
        id = id * 10 + ch - '0';

    for (auto ch : sage)
        age = age * 10 + ch - '0';

    dog = Dog(id, breed, name, age, link);
    return reader;
}

std::ostream& operator<<(std::ostream& writer, Dog& dog)
{
    writer << dog.get_dogId() << ',';
    writer << dog.get_breed() << ',';
    writer << dog.get_name() << ',';
    writer << dog.get_age() << ',';
    writer << dog.get_photo();
    
    return writer;
}