#include "Validation.h"

void Validator::validateDog(Dog dog)
{
	std::string error = "";
	if (dog.get_dogId() <= 0)
		error += "Invalid dog id!\n";
	if (dog.get_age() < 0 || dog.get_age() > 20)
		error += "Invalid age!\n";
	if (dog.get_name().length() < 2)
		error += "Invalid name!\n";
	if (dog.get_breed().length() < 2)
		error += "Invalid breed!\n";
	if (dog.get_photo().length() < 5)
		error += "Invalid link of photograph!\n";
	if (error.length() > 0)
		throw ValidationException(error);
}
