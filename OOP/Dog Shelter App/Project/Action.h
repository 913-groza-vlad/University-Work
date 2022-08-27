#pragma once
#include "Repository.h"

class Action
{
public:
	Action() {};
	Action(const Action&) = default;
	Action& operator=(const Action&) = default;

	virtual void executeUndo() = 0;
	virtual void executeRedo() = 0;

	virtual ~Action() {};
};

class ActionAdd : public Action
{
private:
	Dog addedDog;
	Repository* repo;
public:
	ActionAdd(Dog _dog, Repository* _repo);
	ActionAdd(const ActionAdd&) = default;
	ActionAdd& operator=(const ActionAdd&) = default;
	void executeUndo() override;
	void executeRedo() override;

	~ActionAdd() {};
};

class ActionRemove : public Action
{
private:
	Dog deletedDog;
	Repository* repo;
public:
	ActionRemove(Dog _dog, Repository* _repo);
	ActionRemove(const ActionRemove&) = default;
	ActionRemove& operator=(const ActionRemove&) = default;
	void executeUndo() override;
	void executeRedo() override;

	~ActionRemove() {};
};

class ActionUpdate : public Action
{
private:
	Dog& updatedDog;
	Repository* repo;
	int oldAge, newAge;
	std::string oldPhoto, newPhoto;
public:
	ActionUpdate(Dog& udog, Repository* _repo, int _oldAge,int _newAge, std::string oldPhoto, std::string newPhoto);
	ActionUpdate(const ActionUpdate&) = default;
	ActionUpdate& operator=(const ActionUpdate&) = default;
	void executeUndo() override;
	void executeRedo() override;

	~ActionUpdate() {};
};

