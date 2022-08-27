#include "ParticipantWindow.h"

void ParticipantWindow::populateList()
{
	this->questionsList->clear();
	std::vector<Question> questions = this->serv.getAllQuestions();
	for (auto q : questions)
		this->questionsList->addItem(QString::fromStdString(q.toParticipant()));
}

void ParticipantWindow::updateScoreList()
{
	this->scoreList->clear();
	std::stringstream ss;
	ss << "Score: " << this->part.getScore();
	this->scoreList->addItem(QString::fromStdString(ss.str()));
	QFont font1{ "30" };
	this->scoreList->item(0)->setFont(font1);
}

void ParticipantWindow::answerHandler()
{
	QString question = this->questionsList->currentItem()->text();
	QString ans = this->answerLine->text();
	if (ans.isEmpty())
	{
		QMessageBox::critical(this, "Error", "No answer inserted");
		return;
	}
	std::vector<std::string> tokens = tokenize(question.toStdString(), '|');
	int id1 = stoi(tokens[0]);
	try {
		Question q = this->serv.get_ques(id1);
		int score = 0;
		if (q.getAnswer().compare(ans.toStdString()) == 0)
		{
			score = q.getScore() + this->part.getScore();
			QMessageBox::information(this, "OK", "Question answered correctly!");
			this->serv.updatePartScore(this->part, score);
			//this->questionsList->currentItem()->setBackground(Qt::green);
		}
		else {
			QMessageBox::information(this, "OK", "Wrong answer!");
			this->questionsList->currentItem()->setBackground(Qt::green);
		}
	}
	catch (std::exception& e) {
		QMessageBox::critical(this, "Error", e.what());
		return;
	}
	this->updateScoreList();
}

ParticipantWindow::ParticipantWindow(Quiz& serv, Participant& part) : serv{ serv }, part{ part }
{
	this->setWindowTitle(QString::fromStdString(part.getName()));
	QVBoxLayout* windowLay = new QVBoxLayout{ this };
	this->questionsList = new QListWidget{};
	
	serv.addObserver(this);

	this->scoreList = new QListWidget{};
	this->updateScoreList();

	QWidget* actions = new QWidget{};
	QGridLayout* actionsLay = new QGridLayout{ actions };
	//QLabel* answer = new QLabel{ "Answer" };
	this->answerLine = new QLineEdit{};
	this->answerButton = new QPushButton{ "Answer" };
	//actionsLay->addWidget(answer, 0, 0);
	actionsLay->addWidget(answerLine , 0, 0);
	actionsLay->addWidget(answerButton, 0, 1);

	windowLay->addWidget(this->scoreList);
	windowLay->addWidget(this->questionsList);
	windowLay->addWidget(actions);

	this->populateList();
	QObject::connect(this->answerButton, &QPushButton::clicked, this, &ParticipantWindow::answerHandler);
}

void ParticipantWindow::update()
{
	this->populateList();
}
