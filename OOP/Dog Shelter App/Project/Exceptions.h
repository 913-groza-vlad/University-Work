#pragma once

#include <exception>
#include <string>

class ValidationException : public std::exception {
private:
	std::string _message;
public:
	ValidationException(std::string message);
	//const char *what() const noexcept override;
	std::string str() const;
	~ValidationException() {}
};

class RepositoryException : public std::exception {
private:
	std::string _message;
public:
	RepositoryException(std::string message);
	//const char* what() const noexcept override;
	std::string str() const;
	~RepositoryException() {}
};