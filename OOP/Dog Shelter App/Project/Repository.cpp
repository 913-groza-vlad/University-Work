#include "Repository.h"

void Repository::add(const Dog& elem)
{
	this->data.push_back(elem);
}

int Repository::getSize() const
{
	return this->data.size();
}

Dog& Repository::getDog(int pos)
{
	return this->data[pos];
}

void Repository::remove_by_id(int id)
{
	bool ok = false;
	int i = 0;
	while (i < this->getSize() && !ok)
	{
		Dog d = getDog(i);
		if (d.get_dogId() == id)
		{
			ok = true;
			this->it = this->data.begin() + i;
			this->data.erase(it);
		}
		else
			i++;
	}
	if (!ok)
		throw RepositoryException("No dog with this id in the shelter!\n");
}

int Repository::search_by_id(int id)
{
	int i = 0;
	this->it = this->data.begin();
	while (this->it != this->data.end())
	{
		Dog d = getDog(i);
		if (d.get_dogId() == id)
			return i;
		else
		{
			i++;
			it++;
		}
	}
	throw RepositoryException("No dog with this id in the shelter!");
}


void Repository::modify_dog(Dog& d, const int& new_age, const std::string& new_photo)
{
	d.set_age(new_age);
	d.set_photo(new_photo);
}

std::vector<Dog> Repository::getList()
{
	return this->data;
}

void Repository::set_data(std::vector<Dog> new_data)
{
	this->data = new_data;
}

