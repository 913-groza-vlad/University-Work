#include "Exceptions.h"

ValidationException::ValidationException(std::string message)
{
	this->_message = message;
}

std::string ValidationException::str() const
{
	return this->_message;
}


RepositoryException::RepositoryException(std::string message)
{
	this->_message = message;
}

std::string RepositoryException::str() const
{
	return this->_message;
}


