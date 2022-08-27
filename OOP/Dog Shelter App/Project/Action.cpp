#include "Action.h"

ActionAdd::ActionAdd(Dog _song, Repository* _repo) : addedDog{ _song }, repo{ _repo }
{
}

void ActionAdd::executeUndo()
{
	this->repo->remove_by_id(this->addedDog.get_dogId());
}

void ActionAdd::executeRedo()
{
	this->repo->add(this->addedDog);
}

ActionRemove::ActionRemove(Dog _dog, Repository* _repo) : deletedDog{ _dog }, repo{ _repo }
{
}

void ActionRemove::executeUndo()
{
	this->repo->add(this->deletedDog);
} 

void ActionRemove::executeRedo()
{
	this->repo->remove_by_id(this->deletedDog.get_dogId());
}

ActionUpdate::ActionUpdate(Dog& udog, Repository* _repo, int _oldAge, int _newAge, std::string _oldPhoto, std::string _newPhoto) : updatedDog{ udog }, repo{ _repo }, oldAge{ _oldAge }, oldPhoto{ _oldPhoto }, newAge{ _newAge }, newPhoto{ _newPhoto }
{
}

void ActionUpdate::executeRedo()
{
	this->repo->modify_dog(this->updatedDog, this->newAge, this->newPhoto);
}

void ActionUpdate::executeUndo()
{
	this->repo->modify_dog(this->updatedDog, this->oldAge, this->oldPhoto);
}