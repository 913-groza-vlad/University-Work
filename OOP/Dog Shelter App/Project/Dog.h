#pragma once
#include <string>
#include <sstream>
#include <iostream>


class Dog
{
private:
	int dog_id;
	std::string breed;
	std::string name;
	int age;
	std::string photograph;

public:
	Dog() : dog_id{ 0 }, breed{ "" }, name{ "" }, age{ 0 }, photograph{ "" } {}
	// constructor for an istance of class Dog
	Dog(const int id, const std::string& breed, const std::string& name, const int age, const std::string& link);

	// Getter that returns the id (an integer) of an object of class Dog
	int get_dogId() const;
	// Getter that returns the breed (a string) of an object of class Dog
	std::string get_breed() const;
	// Getter that returns the name (a string) of an object of class Dog
	std::string get_name() const;
	// Getter that returns the age (an integer) of an object of class Dog
	int get_age() const;
	// Getter that returns the link to a photograph (a string) of an object of class Dog
	std::string get_photo() const;

	// Setter that modifies the age of a Dog with new_age
	void set_age(const int& new_age);
	//Setter that modifies the photo of a Dog with new_photo
	void set_photo(const std::string& new_photo);

	/// <summary>
	/// Function that checks if each atribute of a Dog object is valid
	/// </summary>
	/// <returns> true, if Dog's data is valid, false otherwise</returns>
	bool valid();
	// Method that trasnfer the atributes of a Dog into a string a returns it 
	std::string toStr();

	friend std::istream &operator>>(std::istream& reader, Dog& dog);
	friend std::ostream &operator<<(std::ostream& writer, Dog& dog);

};

void strip(std::string& str);