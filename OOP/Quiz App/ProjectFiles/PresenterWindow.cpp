#include "PresenterWindow.h"

void PresenterWindow::populateList()
{
	this->questionsList->clear();
	std::vector<Question> questions = this->serv.getAllQuestions();
	for (auto q : questions)
		this->questionsList->addItem(QString::fromStdString(q.toPresenter()));
}

void PresenterWindow::addHandler()
{
	QString text = this->textLine->text();
	QString id = this->idLine->text();
	QString answer = this->answerLine->text();
	if (text.isEmpty())
	{
		QMessageBox::critical(this, "Error", "Text is empty");
		return;
	}
	int id1 = stoi(id.toStdString());
	try {
		int score = rand() % 20 + 5;
		this->serv.addQuestion(id1, text.toStdString(), answer.toStdString(), score);
	}
	catch (std::exception& e) {
		QMessageBox::critical(this, "Error", e.what());
		return;
	}
}

PresenterWindow::PresenterWindow(Quiz& serv) : serv{ serv }
{
	this->setWindowTitle("Presenter");
	QVBoxLayout* windowLay = new QVBoxLayout{ this };
	this->questionsList = new QListWidget{};

	serv.addObserver(this);

	QWidget* actions = new QWidget{};
	QGridLayout* actionsLay = new QGridLayout{ actions };
	QLabel* id = new QLabel{ "Id" };
	this->idLine = new QLineEdit{};
	actionsLay->addWidget(id, 0, 0);
	actionsLay->addWidget(idLine, 0, 1);
	QLabel* text = new QLabel{ "Text" };
	this->textLine = new QLineEdit{};
	actionsLay->addWidget(text, 1, 0);
	actionsLay->addWidget(textLine, 1, 1);
	QLabel* answer = new QLabel{ "Answer" };
	this->answerLine = new QLineEdit{};
	actionsLay->addWidget(answer, 2, 0);
	actionsLay->addWidget(answerLine, 2, 1);
	
	this->addButton = new QPushButton{ "Add" };
	actionsLay->addWidget(addButton, 3, 1);


	windowLay->addWidget(this->questionsList);
	windowLay->addWidget(actions);
	this->populateList();

	QObject::connect(this->addButton, &QPushButton::clicked, this, &PresenterWindow::addHandler);
}

void PresenterWindow::update()
{
	this->populateList();
}
