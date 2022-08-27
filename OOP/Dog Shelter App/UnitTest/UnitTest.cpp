#include "pch.h"
#include "CppUnitTest.h"
#include "../ProjectCpp/Dog.h"
#include "../ProjectCpp/Repository.h"
#include "../ProjectCpp/Services.h"
#include "../ProjectCpp/Dog.cpp"
#include "../ProjectCpp/Repository.cpp"
#include "../ProjectCpp/FileRepository.cpp"
#include "../ProjectCpp/Services.cpp"
#include "../ProjectCpp/Validation.cpp"
#include "../ProjectCpp/Exceptions.cpp"

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace UnitTest
{
	TEST_CLASS(UnitTest)
	{
	public:

		TEST_METHOD(TestDog)
		{
			Dog d{ 4, "Husky", "Aki", 0, "https://dogtime.com/dog-breeds/huskita" };
			Assert::AreEqual(d.valid(), true);
			Assert::AreEqual(d.get_dogId(), 4);
			Assert::AreEqual(d.get_breed().compare("Husky"), 0);
			Assert::AreEqual(d.get_name().compare("Aki"), 0);
			Assert::AreEqual(d.get_age(), 0);
			Assert::AreEqual(d.get_photo().compare("https://dogtime.com/dog-breeds/huskita"), 0);

			d.set_age(1);
			Assert::AreEqual(d.get_age(), 1);
			d.set_photo("https://www.doggie.com");
			Assert::AreEqual(d.get_photo().compare("https://www.doggie.com"), 0);

			Dog dog{ -4, "H", "", -1, "f" };
			Assert::AreEqual(dog.valid(), false);
			Assert::IsFalse(dog.valid());

			std::string str = d.toStr();
			Assert::AreEqual(str.compare("Dog 4, named Aki, breed: Husky, age: 1 years;  Photo: https://www.doggie.com\n"), 0);
		}

		TEST_METHOD(TestVector)
		{
			std::vector<int> numbers{1, 4, 3};
			std::vector<int>::iterator it;
			Assert::AreEqual(numbers.size(), size_t(3));

			numbers.push_back(12);
			Assert::AreEqual(numbers[3], 12);
			Assert::AreEqual(numbers.size(), size_t(4));
			it = numbers.begin() + 2;
			numbers.erase(it);
			Assert::AreEqual(numbers.size(), size_t(3));

			int i = 0;
			for (it = numbers.begin(); it != numbers.end(); it++)
			{
				Assert::AreEqual(numbers[i], *it);
				i++;
			}

		}

		TEST_METHOD(TestRepo)
		{
			Repository r;
			Dog d{ 4, "Husky", "Aki", 0, "https://dogtime.com/dog-breeds/huskita" };
			Dog d1{ 5, "Bulldog", "Jack", 3, "https://dogtime.com/dog-breeds/bulldog" };

			Assert::AreEqual(r.getSize(), 0);
			r.add(d);
			r.add(d1);
			Assert::AreEqual(r.getSize(), 2);

			Dog dog = r.getDog(0);
			Assert::AreEqual(dog.get_dogId(), 4);

			try {
				int found = r.search_by_id(7);
			}
			catch (RepositoryException& re)
			{
				Assert::AreEqual(re.str().compare("No dog with this id in the shelter!"), 0);
			}

			int found = r.search_by_id(5);
			Assert::AreEqual(found, 1);

			int i = 0;
			for (auto dog : r.getList())
			{
				Dog doggie = r.getDog(i);
				Assert::AreEqual(doggie.get_dogId(), dog.get_dogId());
				i++;
			}

			try {
				r.remove_by_id(123);
			}
			catch (RepositoryException& re) {
				Assert::AreEqual(re.str().compare("No dog with this id in the shelter!\n"), 0);
			}
			Assert::AreEqual(r.getSize(), 2);
			r.remove_by_id(4);
			Assert::AreEqual(r.getSize(), 1);

			r.modify_dog(d, 3, "www.what.com");
			Assert::AreEqual(d.get_age(), 3);
			Assert::AreEqual(d.get_photo().compare("www.what.com"), 0);
		}

		TEST_METHOD(TestService)
		{
			Repository list;
			FileRepository r("testDogs.txt"), repo("dogs.txt");
			Validator valid;
			Services serv{ r, list, valid };
			serv.create_Doglist();

			try {
				serv.addDog(-12, "", "", -7, "");
			}
			catch (ValidationException& ve) {
				Assert::AreEqual(ve.str().compare("Invalid dog id!\nInvalid age!\nInvalid name!\nInvalid breed!\nInvalid link of photograph!\n"), 0);
			}

			try {
				serv.addDog(1, "Husky", "Dodge", 2, "https://dogtime.com/dog-breeds/huskita");
			}
			catch (RepositoryException& re) {
				Assert::AreEqual(re.str().compare("There exists another dog having this id!"), 0);
			}
			
			serv.addDog(13, "Bichon", "Coco", 2, "https://dogtime.com/dog-breeds/bich");

			Repository& dogs = serv.getAll();
			Assert::AreEqual(dogs.getSize(), 11);

			serv.removeDog(7);
			Assert::AreEqual(r.getSize(), 10);

			serv.updateDog(13, "Bichon", "Coco", 3, "https://dogtime.com/dog-breeds/bichon");
			Dog& dog = dogs.getDog(9);
			Assert::AreEqual(dog.get_age(), 3);
			Assert::AreEqual(dog.get_photo().compare("https://dogtime.com/dog-breeds/bichon"), 0);

			try {
				serv.updateDog(25, "Bichon", "Coco", 3, "https://dogtime.com/dog-breeds/bichon");
			}
			catch (RepositoryException& re) {
				Assert::AreEqual(re.str().compare("No dog with this id in the shelter!"), 0);
			}
			//Assert::AreEqual(updated_dog.get_dogId(), -1);

			try {
				serv.updateDog(13, "Bichon", "Coco", -1, "https://dogtime.com/dog-breeds/bichon");
			}
			catch (ValidationException& ve) {
				Assert::AreEqual(ve.str().compare("Invalid age!\n"), 0);
			}
			//Assert::AreEqual(updated_dog.get_dogId(), -1);

			Repository& adoption_list = serv.getAdoptionList();
			Assert::AreEqual(adoption_list.getSize(), 0);

			serv.adoptDog(dogs, 3);
			Assert::AreEqual(dogs.getSize(), 9);
			Assert::AreEqual(adoption_list.getSize(), 1);
			Assert::AreEqual(adoption_list.getDog(0).get_dogId(), 4);

			Repository filtered = serv.filteredDogs("Bulldog", 6);
			Assert::AreEqual(filtered.getSize(), 3);

			serv.removeDog(1);
			serv.removeDog(2);
			serv.removeDog(3);
			serv.removeDog(5);
			serv.removeDog(6);
			serv.removeDog(8);
			serv.removeDog(9);
			serv.removeDog(10);
			serv.removeDog(13);
		}
	};
}
