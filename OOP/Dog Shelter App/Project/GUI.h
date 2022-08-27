#pragma once
#include "Services.h"
#include "TableViewWidget.h"
#include <QtWidgets/QWidget>
#include <QtWidgets/QListWidget>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QTextEdit>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QLabel>
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QFormLayout>
#include <QtWidgets/QMessageBox>
#include <QShortcut>


class GUI : public QWidget
{
	Q_OBJECT
private:
	Services& serv;
	QPushButton* adminButton = new QPushButton{ "Administrator Mode" };
	QPushButton* userButton = new QPushButton{ "User Mode" };
	QPushButton* tableViewButton = new QPushButton{ "AdoptionList (TableView)" };
	QPushButton* exitButton = new QPushButton{ "Exit" };

	QWidget* adminWnd;
	QWidget* filter;
	QLineEdit* editTextFilter;
	QPushButton* addButton;
	QPushButton* removeButton;
	QPushButton* updateButton;
	QPushButton* exitAdminButton;
	QPushButton* undoButton;
	QPushButton* redoButton;
	QShortcut* undoShortcut;
	QShortcut* redoShortcut;

	QWidget* userWnd;
	QPushButton* seachButton;
	QPushButton* breedButton;
	QPushButton* openListButton;
	QPushButton* exitUserButton;

	QWidget* addWindow = new QWidget{};
	QPushButton* saveAdd;
	QPushButton* cancelAdd;
	QLineEdit* idTextBox;
	QLineEdit* breedTextBox;
	QLineEdit* nameTextBox;
	QLineEdit* ageTextBox;
	QLineEdit* photoTextBox;
	QWidget* removeWindow = new QWidget{};
	QPushButton* saveRemove;
	QPushButton* cancelRemove;
	QLineEdit* idRmTextBox;
	QWidget* updateWindow = new QWidget{};
	QPushButton* saveUpdate;
	QPushButton* cancelUpdate;
	QLineEdit* idUpTextBox;
	QLineEdit* ageUpTextBox;
	QLineEdit* photoUpTextBox;

	QWidget* adoptWindow = new QWidget{};
	QPushButton* nextButton;
	QPushButton* adoptButton;
	QPushButton* exitAdoptionButton;
	QWidget* adoptBreedWindow = new QWidget{};
	QPushButton* nextButton2;
	QPushButton* adoptButton2;
	QPushButton* exitAdoptionButton2;
	QLineEdit* breedFilterText;
	QLineEdit* ageFilterText;
	QPushButton* searchByBreedButton;

	QListWidget* dogsList;
	QListWidget* adoptionList;
	QListWidget* selectedDog;
	std::vector<Dog> uiDogs;
	std::vector<Dog> uiAdoptedDogs;
	std::vector<Dog> uifilteredDogs;

	void exitHandler();
	void adminHandler();
	void userHandler();
	void tableViewHandler();
	void exitAdminHandler();
	void exitUserHandler();
	void addHandler();
	void addDogHandler();
	void removeHandler();
	void removeDogHandler();
	void updateHandler();
	void updateDogHandler();
	void filterHandler();
	void undoHandler();
	void redoHandler();
	void exitAddHandler() { this->addWindow->close(); };
	void exitRemoveHandler() { this->removeWindow->close(); };
	void exitUpdateHandler() { this->updateWindow->close(); };
	void openListHandler();
	void adoptDogHandler();
	void adoptHandler();
	void nextHandler();
	void exitAdoptHandler() { this->adoptWindow->close(); };
	void adoptByBreedHandler();
	void adoptBreedHandler();
	void nextBreedHandler();
	void searchHandler();
	void exitAdoptBreedHandler() { this->adoptBreedWindow->close(); };

	void connectSignalsAndSlots();
	void connectAdminSignalsAndSlots();
	void connectUserSignalsAndSlots();

	void createDogsList();
	void populateDogsList();
	void createAdoptionList();
	void populateAdoptionList();
	void listGradient();

	int pos;

public:
	GUI(Services& _serv);
	~GUI() {};
};

