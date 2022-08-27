#include "GUI.h"


GUI::GUI(Services& _serv) : serv{ _serv }
{
	this->setWindowTitle("<Adopt a Pet> App");

	auto* parentLayout = new QVBoxLayout{ this };

	QWidget* titleWidget = new QWidget{};
	QVBoxLayout* titleLay = new QVBoxLayout{};
	QLabel* title = new QLabel("~Adopt a Pet~");
	title->setAlignment(Qt::AlignCenter);
	QFont titleFont = title->font();
	titleFont.setPointSize(15);
	title->setFont(titleFont);
	titleLay->addWidget(title);
	titleWidget->setLayout(titleLay);
	parentLayout->addWidget(titleWidget);
	titleWidget->setFixedHeight(titleWidget->sizeHint().height());

	QWidget* menu = new QWidget{};
	QVBoxLayout* vLay = new QVBoxLayout{};
	vLay->addWidget(this->adminButton);
	vLay->addWidget(this->userButton);
	vLay->addWidget(this->tableViewButton);
	vLay->addWidget(this->exitButton);
	menu->setLayout(vLay);
	parentLayout->addWidget(menu);

	this->adoptionList = new QListWidget{};
	this->createAdoptionList();
	this->populateAdoptionList();
	this->dogsList = new QListWidget{};
	this->createDogsList();
	this->populateDogsList();

	this->connectSignalsAndSlots();
}

void GUI::exitHandler()
{
	GUI::close();
	GUI::exitAdminHandler();
	GUI::exitUserHandler();
}

void GUI::tableViewHandler()
{
	TableModel* dogsModel = new TableModel{ this->serv.getAdoptionList() };
	TableViewWidget* tableWnd = new TableViewWidget{ dogsModel };
	tableWnd->show();
}

void GUI::adminHandler()
{
	this->adminWnd = new QWidget{};
	this->adminWnd->setWindowTitle("<Admin Mode>");
	QVBoxLayout* adminLayout = new QVBoxLayout{};
	this->adminWnd->setLayout(adminLayout);

	QWidget* titleWidget = new QWidget{};
	QVBoxLayout* titleLay = new QVBoxLayout{};
	QLabel* title = new QLabel("~Admininstrator Mode~");
	title->setAlignment(Qt::AlignCenter);
	QFont titleFont = title->font();
	titleFont.setPointSize(11);
	title->setFont(titleFont);
	titleLay->addWidget(title);
	titleWidget->setLayout(titleLay);
	titleWidget->setFixedHeight(titleWidget->sizeHint().height());
	adminLayout->addWidget(titleWidget);

	QWidget* admin = new QWidget{};
	QHBoxLayout* adminLay = new QHBoxLayout{};
	admin->setLayout(adminLay);

	QWidget* adminMenu = new QWidget{};
	QWidget* adminList = new QWidget{};
	QGridLayout* menuLayout = new QGridLayout{};
	QVBoxLayout* listLayout = new QVBoxLayout{};
	adminMenu->setLayout(menuLayout);
	adminList->setLayout(listLayout);
	this->addButton = new QPushButton{ "Add Dog" };
	this->removeButton = new QPushButton{ "Remove Dog" };
	this->updateButton = new QPushButton{ "Update Dog" };
	this->exitAdminButton = new QPushButton{ "Exit Admin Mode" };
	this->undoButton = new QPushButton{ "Undo" };
	this->redoButton = new QPushButton{ "Redo" };
	this->undoShortcut = new QShortcut{ QKeySequence("Ctrl+Z"), this->adminWnd };
	this->redoShortcut = new QShortcut{ QKeySequence("Ctrl+Y"), this->adminWnd };
	menuLayout->addWidget(this->addButton, 0, 1);
	menuLayout->addWidget(this->removeButton, 1, 1);
	menuLayout->addWidget(this->updateButton, 2, 1);
	menuLayout->addWidget(this->undoButton, 3, 0);
	menuLayout->addWidget(this->redoButton, 3, 2);
	menuLayout->addWidget(this->exitAdminButton, 4, 1);
	
	this->filter = new QWidget{};
	QFormLayout* filterLay = new QFormLayout{};
	this->filter->setLayout(filterLay);
	this->editTextFilter = new QLineEdit{};
	filterLay->addRow(editTextFilter);
	listLayout->addWidget(filter);

	this->listGradient();
	listLayout->addWidget(this->dogsList);
	adminLay->addWidget(adminMenu);
	adminLay->addWidget(adminList);

	adminLayout->addWidget(admin);

	this->adminWnd->show();
	this->connectAdminSignalsAndSlots();
}

void GUI::listGradient()
{
	QLinearGradient listGrd(0, 150, 0, this->dogsList->height());
	listGrd.setColorAt(1, Qt::green);
	listGrd.setColorAt(0, Qt::yellow);
	QPalette listPallete = this->dogsList->palette();
	listPallete.setBrush(QPalette::ColorRole::Base, QBrush(listGrd));
	this->dogsList->setPalette(listPallete);
	/*QLinearGradient itemGrd(0, 0, 0, 1);
	for (int i = 0; i < this->uiDogs.size(); i++)
	{
		if (this->uiDogs[i].get_age() == 1)
		{
			itemGrd.setColorAt(i, Qt::red);
			this->dogsList->item(i)->setBackground(QBrush(itemGrd));
			this->dogsList->item(i)->setForeground(Qt::white);
		}
	}*/
}

void GUI::userHandler()
{
	this->userWnd = new QWidget{};
	this->userWnd->setWindowTitle("<User Mode>");
	QVBoxLayout* userLayout = new QVBoxLayout{};
	this->userWnd->setLayout(userLayout);

	QWidget* titleWidget = new QWidget{};
	QVBoxLayout* titleLay = new QVBoxLayout{};
	QLabel* title = new QLabel("~User Mode~");
	title->setAlignment(Qt::AlignCenter);
	QFont titleFont = title->font();
	titleFont.setPointSize(11);
	title->setFont(titleFont);
	titleLay->addWidget(title);
	titleWidget->setLayout(titleLay);
	titleWidget->setFixedHeight(titleWidget->sizeHint().height());
	userLayout->addWidget(titleWidget);

	QWidget* user = new QWidget{};
	QHBoxLayout* userLay = new QHBoxLayout{};
	user->setLayout(userLay);

	QWidget* userMenu = new QWidget{};
	QWidget* userList = new QWidget{};
	QGridLayout* menuLayout = new QGridLayout{};
	QVBoxLayout* listLayout = new QVBoxLayout{};
	userMenu->setLayout(menuLayout);
	userList->setLayout(listLayout);
	this->seachButton = new QPushButton{ "Look for a dog" };
	this->breedButton = new QPushButton{ "Dogs of a breed" };
	this->openListButton = new QPushButton{ "Open Adoption List" };
	this->exitUserButton = new QPushButton{ "Exit User Mode" };
	menuLayout->addWidget(this->seachButton, 0, 0);
	menuLayout->addWidget(this->breedButton, 1, 0);
	menuLayout->addWidget(this->openListButton, 2, 0);
	menuLayout->addWidget(this->exitUserButton, 3, 0);
	userLay->addWidget(userMenu);
	userLay->addWidget(userList);
	
	listLayout->addWidget(this->adoptionList);
	userLayout->addWidget(user);

	this->userWnd->show();
	this->connectUserSignalsAndSlots();
}

void GUI::exitAdminHandler()
{
	this->adminWnd->close();
	this->exitAddHandler();
	this->exitRemoveHandler();
	this->exitUpdateHandler();
}

void GUI::exitUserHandler()
{
	this->userWnd->close();
	this->exitAdoptHandler();
	this->exitAdoptBreedHandler();
}

void GUI::filterHandler()
{
	QString text = this->editTextFilter->text();
	this->dogsList->clear();
	for (auto dog : this->uiDogs)
		if (dog.toStr().find(text.toStdString()) != -1)
			this->dogsList->addItem(QString::fromStdString(dog.toStr()));
}

void GUI::addHandler()
{
	this->addWindow = new QWidget{};
	this->addWindow->setWindowTitle("<Add Dog>");
	QVBoxLayout* addLayout = new QVBoxLayout{};
	this->addWindow->setLayout(addLayout);

	QWidget* data = new QWidget{};
	QFormLayout* formLay = new QFormLayout{};
	data->setLayout(formLay);

	this->idTextBox = new QLineEdit{};
	QLabel* idLabel = new QLabel{ "&Dog id:" };
	idLabel->setBuddy(idTextBox);

	this->breedTextBox = new QLineEdit{};
	QLabel* breedLabel = new QLabel{ "&Breed:" };
	breedLabel->setBuddy(breedTextBox);

	this->nameTextBox = new QLineEdit{};
	QLabel* nameLabel = new QLabel{ "&Name:" };
	nameLabel->setBuddy(nameTextBox);

	this->ageTextBox = new QLineEdit{};
	QLabel* ageLabel = new QLabel{ "&Age:" };
	ageLabel->setBuddy(ageTextBox);

	this->photoTextBox = new QLineEdit{};
	QLabel* photoLabel = new QLabel{ "&Photograph(link):" };
	photoLabel->setBuddy(photoTextBox);

	formLay->addRow(idLabel, idTextBox);
	formLay->addRow(breedLabel, breedTextBox);
	formLay->addRow(nameLabel, nameTextBox);
	formLay->addRow(ageLabel, ageTextBox);
	formLay->addRow(photoLabel, photoTextBox);

	QWidget* actions = new QWidget{};
	QHBoxLayout* hLay = new QHBoxLayout{};
	this->saveAdd = new QPushButton{ "&Save" };
	this->cancelAdd = new QPushButton{ "&Cancel" };
	hLay->addWidget(this->saveAdd);
	hLay->addWidget(this->cancelAdd);
	actions->setLayout(hLay);

	addLayout->addWidget(data);
	addLayout->addLayout(formLay);
	addLayout->addWidget(actions);

	this->addWindow->show();
	QWidget::connect(this->cancelAdd, &QPushButton::clicked, this, &GUI::exitAddHandler);
	QWidget::connect(this->saveAdd, &QPushButton::clicked, this, &GUI::addDogHandler);
}

void GUI::addDogHandler()
{
	QString id = this->idTextBox->text();
	QString breed = this->breedTextBox->text();
	QString name = this->nameTextBox->text();
	QString age = this->ageTextBox->text();
	QString photo = this->photoTextBox->text();
	if (id.isEmpty())
	{
		QMessageBox::critical(this->adminWnd, "Error", "Id is not a number!");
		return;
	}
	for (auto character : id.toStdString())
		if (character < '0' || character > '9') 
		{
			QMessageBox::critical(this->adminWnd, "Error", "Id is not a number!");
			return;
		}
	if (age.isEmpty())
	{
		QMessageBox::critical(this->adminWnd, "Error", "Age is not a number!");
		return;
	}
	for (auto character : age.toStdString())
		if (character < '0' || character > '9')
		{
			QMessageBox::critical(this->adminWnd, "Error", "Age is not a number!");
			return;
		}

	try {
		this->serv.addDog(stoi(id.toStdString()), breed.toStdString(), name.toStdString(), stoi(age.toStdString()), photo.toStdString());
		Dog dog{ stoi(id.toStdString()), breed.toStdString(), name.toStdString(), stoi(age.toStdString()), photo.toStdString() };
		this->dogsList->addItem(QString::fromStdString(dog.toStr()));
		this->uiDogs.push_back(dog);
		QMessageBox::information(this->adminWnd, "Success", "Dog successfully added");
	}
	catch (ValidationException& ve) {
		QMessageBox::critical(this->adminWnd, "Error", ve.str().c_str());
	}
	catch (RepositoryException& re) {
		QMessageBox::critical(this->adminWnd, "Error", re.str().c_str());
	}
	GUI::exitAddHandler();
}

void GUI::removeHandler()
{
	this->removeWindow = new QWidget{};
	this->removeWindow->setWindowTitle("<Remove Dog>");
	QVBoxLayout* removeLayout = new QVBoxLayout{};
	this->removeWindow->setLayout(removeLayout);

	QWidget* data = new QWidget{};
	QFormLayout* formLay = new QFormLayout{};
	data->setLayout(formLay);

	this->idRmTextBox = new QLineEdit{};
	QLabel* idLabel = new QLabel{ "&Id of dog to be removed:" };
	idLabel->setBuddy(idRmTextBox);
	formLay->addRow(idLabel, idRmTextBox);
	
	QWidget* actions = new QWidget{};
	QHBoxLayout* hLay = new QHBoxLayout{};
	this->saveRemove = new QPushButton{ "&Remove" };
	this->cancelRemove = new QPushButton{ "&Cancel" };
	hLay->addWidget(this->saveRemove);
	hLay->addWidget(this->cancelRemove);
	actions->setLayout(hLay);

	removeLayout->addWidget(data);
	removeLayout->addLayout(formLay);
	removeLayout->addWidget(actions);

	this->removeWindow->show();
	QWidget::connect(this->cancelRemove, &QPushButton::clicked, this, &GUI::exitRemoveHandler);
	QWidget::connect(this->saveRemove, &QPushButton::clicked, this, &GUI::removeDogHandler);
}

void GUI::removeDogHandler()
{
	QString idStr = this->idRmTextBox->text();
	if (idStr.isEmpty())
	{
		QMessageBox::critical(this->adminWnd, "Error", "Id is not a number!");
		return;
	}
	for (auto character : idStr.toStdString())
		if (character < '0' || character > '9')
		{
			QMessageBox::critical(this->adminWnd, "Error", "Id is not a number!");
			return;
		}
	int id = stoi(idStr.toStdString());
	try {
		int pos = this->serv.getAll()->search_by_id(id);
		this->serv.removeDog(id);
		this->dogsList->takeItem(pos);
		this->uiDogs.erase(uiDogs.begin() + pos);
		QMessageBox::information(this->adminWnd, "Success", "Dog successfully removed!");
	}
	catch (RepositoryException& re) {
		QMessageBox::critical(this->adminWnd, "Error", re.str().c_str());
	}
	GUI::exitRemoveHandler();
}


void GUI::updateHandler()
{
	this->updateWindow = new QWidget{};
	this->updateWindow->setWindowTitle("<Update Dog>");
	QVBoxLayout* updateLayout = new QVBoxLayout{};
	this->updateWindow->setLayout(updateLayout);

	QWidget* data = new QWidget{};
	QFormLayout* formLay = new QFormLayout{};
	data->setLayout(formLay);

	this->idUpTextBox = new QLineEdit{};
	QLabel* idLabel = new QLabel{ "&Id of dog to be updated:" };
	idLabel->setBuddy(idUpTextBox);

	this->ageUpTextBox = new QLineEdit{};
	QLabel* ageLabel = new QLabel{ "\t-> New Age:" };
	ageLabel->setBuddy(ageUpTextBox);

	this->photoUpTextBox = new QLineEdit{};
	QLabel* photoLabel = new QLabel{ "\t-> New Photograph(link):" };
	photoLabel->setBuddy(photoUpTextBox);

	formLay->addRow(idLabel, idUpTextBox);
	formLay->addRow(ageLabel, ageUpTextBox);
	formLay->addRow(photoLabel, photoUpTextBox);

	QWidget* actions = new QWidget{};
	QHBoxLayout* hLay = new QHBoxLayout{};
	this->saveUpdate = new QPushButton{ "&Update" };
	this->cancelUpdate = new QPushButton{ "&Cancel" };
	hLay->addWidget(this->saveUpdate);
	hLay->addWidget(this->cancelUpdate);
	actions->setLayout(hLay);

	updateLayout->addWidget(data);
	updateLayout->addLayout(formLay);
	updateLayout->addWidget(actions);

	this->updateWindow->show();
	QWidget::connect(this->cancelUpdate, &QPushButton::clicked, this, &GUI::exitUpdateHandler);
	QWidget::connect(this->saveUpdate, &QPushButton::clicked, this, &GUI::updateDogHandler);
}

void GUI::updateDogHandler()
{
	QString idStr = this->idUpTextBox->text();
	if (idStr.isEmpty())
	{
		QMessageBox::critical(this->adminWnd, "Error", "Id is not a number!");
		return;
	}
	for (auto character : idStr.toStdString())
		if (character < '0' || character > '9')
		{
			QMessageBox::critical(this->adminWnd, "Error", "Id is not a number!");
			return;
		}
	int id = stoi(idStr.toStdString());
	int pos;
	try {
		pos = this->serv.getAll()->search_by_id(id);
	}
	catch (RepositoryException& re) {
		QMessageBox::critical(this->adminWnd, "Error", re.str().c_str());
		return;
	}

	QString ageStr = this->ageUpTextBox->text();
	QString photo = this->photoUpTextBox->text();
	if (ageStr.isEmpty())
	{
		QMessageBox::critical(this->adminWnd, "Error", "Age is not a number!");
		return;
	}
	for (auto character : ageStr.toStdString())
		if (character < '0' || character > '9')
		{
			QMessageBox::critical(this->adminWnd, "Error", "Age is not a number!");
			return;
		}
	int age = stoi(ageStr.toStdString());
	try {
		Dog dog = this->serv.getAll()->getDog(pos);
		this->serv.updateDog(id, dog.get_breed(), dog.get_name(), age, photo.toStdString());
		this->createDogsList();
		this->populateDogsList();
		QMessageBox::information(this->adminWnd, "Success", "Dog successfully updated!");
	}
	catch (ValidationException& ve) {
		QMessageBox::critical(this->adminWnd, "Error", ve.str().c_str());
	}
	catch (RepositoryException& re) {
		QMessageBox::critical(this->adminWnd, "Error", re.str().c_str());
	}

	GUI::exitUpdateHandler();
}

void GUI::undoHandler()
{
	try {
		this->serv.undo();
		this->createDogsList();
		this->populateDogsList();
		QMessageBox::information(this->adminWnd, "Success", "Successful undo");
	}
	catch (RepositoryException& re) {
		QMessageBox::critical(this->adminWnd, "Error", re.str().c_str());
	}
}

void GUI::redoHandler()
{
	try {
		this->serv.redo();
		this->createDogsList();
		this->populateDogsList();
		QMessageBox::information(this->adminWnd, "Success", "Successful redo");
	}
	catch (RepositoryException& re) {
		QMessageBox::critical(this->adminWnd, "Error", re.str().c_str());
	}
}

void GUI::openListHandler()
{
	Repository* dogs = this->serv.getAdoptionList();
	std::string file = dogs->get_fileName();
	std::string command;
	if (file.find(".htm") != -1)
	{
		command = "start \"C:\\Program Files(x86)\\Google\\Chrome\\Application\\chrome\" ";
		command += file;
		system(command.c_str());
	}
	else if (file.find(".csv") != -1)
	{
		command = "start excel ";
		command += file;
		system(command.c_str());
	}
}

void GUI::adoptDogHandler()
{
	this->adoptWindow = new QWidget{};
	this->adoptWindow->setWindowTitle("<Adopt Dog>");
	QVBoxLayout* adoptLayout = new QVBoxLayout{};
	this->adoptWindow->setLayout(adoptLayout);

	QWidget* data = new QWidget{};
	QVBoxLayout* dataLay = new QVBoxLayout{};
	data->setLayout(dataLay);
	this->selectedDog = new QListWidget{};
	this->pos = 0;
	this->selectedDog->addItem(QString::fromStdString(this->uiDogs[pos].toStr()));
	dataLay->addWidget(this->selectedDog);

	QWidget* actions = new QWidget{};
	QGridLayout* gridLay = new QGridLayout{};
	this->adoptButton = new QPushButton{ "adopt" };
	this->nextButton = new QPushButton{ "next" };
	this->exitAdoptionButton = new QPushButton{ "close" };
	gridLay->addWidget(this->adoptButton, 0, 0);
	gridLay->addWidget(this->nextButton, 0, 2);
	gridLay->addWidget(this->exitAdoptionButton, 1, 1);
	actions->setLayout(gridLay);

	adoptLayout->addWidget(data);
	adoptLayout->addLayout(dataLay);
	adoptLayout->addWidget(actions);

	this->adoptWindow->show();
	QWidget::connect(this->exitAdoptionButton, &QPushButton::clicked, this, &GUI::exitAdoptHandler);
	QWidget::connect(this->adoptButton, &QPushButton::clicked, this, &GUI::adoptHandler);
	QWidget::connect(this->nextButton, &QPushButton::clicked, this, &GUI::nextHandler);
}

void GUI::adoptHandler()
{
	Repository* repoDogs = this->serv.getAll();
	Dog dog = repoDogs->getDog(pos);
	this->serv.adoptDog(repoDogs, pos);
	this->createAdoptionList();
	this->populateAdoptionList();
	this->createDogsList();
	this->populateDogsList();
	std::string message = dog.get_name() + " was adopted!";
	QMessageBox::information(this->userWnd, "Success", message.c_str());
	GUI::exitAdoptHandler();
}

void GUI::nextHandler()
{
	this->selectedDog->clear();
	pos++;
	if (pos == this->uiDogs.size())
		pos = 0;
	this->selectedDog->addItem(QString::fromStdString(this->uiDogs[pos].toStr()));
}

void GUI::adoptByBreedHandler()
{
	this->adoptBreedWindow = new QWidget{};
	this->adoptBreedWindow->setWindowTitle("<Adopt Dog>");
	QVBoxLayout* adoptLayout = new QVBoxLayout{};
	this->adoptBreedWindow->setLayout(adoptLayout);

	QWidget* data = new QWidget{};
	QVBoxLayout* dataLay = new QVBoxLayout{};
	data->setLayout(dataLay);
	this->selectedDog = new QListWidget{};
	dataLay->addWidget(this->selectedDog);

	QWidget* actions = new QWidget{};
	QGridLayout* gridLay = new QGridLayout{};
	this->adoptButton2 = new QPushButton{ "adopt" };
	this->nextButton2 = new QPushButton{ "next" };
	this->exitAdoptionButton2 = new QPushButton{ "close" };
	gridLay->addWidget(this->adoptButton2, 0, 0);
	gridLay->addWidget(this->nextButton2, 0, 2);
	gridLay->addWidget(this->exitAdoptionButton2, 1, 1);
	actions->setLayout(gridLay);

	QWidget* textFilter = new QWidget{};
	QFormLayout* textLay = new QFormLayout{};
	textFilter->setLayout(textLay);
	this->breedFilterText = new QLineEdit{};
	QLabel* breedLabel = new QLabel{ "&Breed:" };
	breedLabel->setBuddy(breedFilterText);
	this->ageFilterText = new QLineEdit{};
	QLabel* ageLabel = new QLabel{ "&Age:" };
	ageLabel->setBuddy(ageFilterText);
	this->searchByBreedButton = new QPushButton{ "&Search" };
	textLay->addRow(breedLabel, breedFilterText);
	textLay->addRow(ageLabel, ageFilterText);
	textLay->addRow(searchByBreedButton);

	adoptLayout->addWidget(textFilter);
	adoptLayout->addLayout(textLay);
	adoptLayout->addWidget(data);
	adoptLayout->addLayout(dataLay);
	adoptLayout->addWidget(actions);

	this->adoptBreedWindow->show();
	QWidget::connect(this->exitAdoptionButton2, &QPushButton::clicked, this, &GUI::exitAdoptBreedHandler);
	QWidget::connect(this->adoptButton2, &QPushButton::clicked, this, &GUI::adoptBreedHandler);
	QWidget::connect(this->nextButton2, &QPushButton::clicked, this, &GUI::nextBreedHandler);
	QWidget::connect(this->searchByBreedButton, &QPushButton::clicked, this, &GUI::searchHandler);
}

void GUI::searchHandler()
{
	this->selectedDog->clear();
	QString breed = this->breedFilterText->text();
	QString ageStr = this->ageFilterText->text();
	if (ageStr.isEmpty())
	{
		QMessageBox::critical(this->userWnd, "Error", "Provide a number(age)!");
		return;
	}
	for (auto character : ageStr.toStdString())
		if (character < '0' || character > '9')
		{
			QMessageBox::critical(this->userWnd, "Error", "Age is not a number!");
			return;
		}
	int age = stoi(ageStr.toStdString());
	this->pos = 0;
	this->uifilteredDogs = this->serv.filteredDogs(breed.toStdString(), age)->getList();
	if (this->uifilteredDogs.size() == 0)
	{
		std::string message = "No dog with of breed " + breed.toStdString() + " with age less than " + ageStr.toStdString() + " is in the shelter";
		QMessageBox::critical(this->userWnd, "Error", message.c_str());
		return;
	}
	this->selectedDog->addItem(QString::fromStdString(this->uifilteredDogs[pos].toStr()));
}

void GUI::adoptBreedHandler()
{
	QString breed = this->breedFilterText->text();
	QString ageStr = this->ageFilterText->text();
	if (ageStr.isEmpty())
	{
		QMessageBox::critical(this->userWnd, "Error", "No dog found!");
		return;
	}
	for (auto character : ageStr.toStdString())
		if (character < '0' || character > '9')
		{
			QMessageBox::critical(this->userWnd, "Error", "Age is not a number!");
			return;
		}
	int age = stoi(ageStr.toStdString());
	Repository* filterDogs = this->serv.filteredDogs(breed.toStdString(), age);
	Dog dog = filterDogs->getDog(pos);
	this->serv.adoptDog(filterDogs, pos);
	this->createAdoptionList();
	this->populateAdoptionList();
	this->createDogsList();
	this->populateDogsList();
	std::string message = dog.get_name() + " was adopted!";
	QMessageBox::information(this->userWnd, "Success", message.c_str());
	GUI::exitAdoptBreedHandler();
}

void GUI::nextBreedHandler()
{
	if (!this->ageFilterText->text().isEmpty()) {
		this->selectedDog->clear();
		pos++;
		if (pos == this->uifilteredDogs.size())
			pos = 0;
		this->selectedDog->addItem(QString::fromStdString(this->uifilteredDogs[pos].toStr()));
	}
}

void GUI::connectSignalsAndSlots()
{
	QObject::connect(this->exitButton, &QPushButton::clicked, this, &GUI::exitHandler);
	QObject::connect(this->adminButton, &QPushButton::clicked, this, &GUI::adminHandler);
	QObject::connect(this->userButton, &QPushButton::clicked, this, &GUI::userHandler);
	QObject::connect(this->tableViewButton, &QPushButton::clicked, this, &GUI::tableViewHandler);
}

void GUI::connectAdminSignalsAndSlots()
{
	QWidget::connect(this->exitAdminButton, &QPushButton::clicked, this, &GUI::exitAdminHandler);
	QWidget::connect(this->addButton, &QPushButton::clicked, this, &GUI::addHandler);
	QWidget::connect(this->removeButton, &QPushButton::clicked, this, &GUI::removeHandler);
	QWidget::connect(this->updateButton, &QPushButton::clicked, this, &GUI::updateHandler);
	QObject::connect(this->editTextFilter, &QLineEdit::textChanged, this, &GUI::filterHandler);
	QObject::connect(this->undoButton, &QPushButton::clicked, this, &GUI::undoHandler);
	QObject::connect(this->undoShortcut, &QShortcut::activated, this, &GUI::undoHandler);
	QObject::connect(this->redoButton, &QPushButton::clicked, this, &GUI::redoHandler);
	QObject::connect(this->redoShortcut, &QShortcut::activated, this, &GUI::redoHandler);
}

void GUI::connectUserSignalsAndSlots()
{
	QWidget::connect(this->exitUserButton, &QPushButton::clicked, this, &GUI::exitUserHandler);
	QWidget::connect(this->openListButton, &QPushButton::clicked, this, &GUI::openListHandler);
	QWidget::connect(this->seachButton, &QPushButton::clicked, this, &GUI::adoptDogHandler);
	QWidget::connect(this->breedButton, &QPushButton::clicked, this, &GUI::adoptByBreedHandler);
}


void GUI::createDogsList()
{
	//this->dogsList->clear();
	this->uiDogs.clear();
	std::vector<Dog> dogs = this->serv.getAll()->getList();
	for (auto dog : dogs)
		this->uiDogs.push_back(dog);
}

void GUI::populateDogsList()
{
	this->dogsList->clear();
	for (auto dog : this->uiDogs)
		this->dogsList->addItem(QString::fromStdString(dog.toStr()));
}

void GUI::createAdoptionList()
{
	//this->adoptionList->clear();
	this->uiAdoptedDogs.clear();
	std::vector<Dog> dogs = this->serv.getAdoptionList()->getList();
	for (auto dog : dogs)
		this->uiAdoptedDogs.push_back(dog);
}

void GUI::populateAdoptionList()
{
	this->adoptionList->clear();
	for (auto dog : this->uiAdoptedDogs)
		this->adoptionList->addItem(QString::fromStdString(dog.toStr()));
}
