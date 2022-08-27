#pragma once
#include "Quiz.h"
#include "QWidget"
#include "QVBoxLayout"
#include "QPushButton"
#include "QListWidget"
#include "QGridLayout"
#include "QLineEdit"
#include "QLabel"
#include "QMessageBox"

class ParticipantWindow : public QWidget, public Observer
{
private:
	Quiz& serv;
	Participant& part;

	QListWidget* questionsList;
	QLineEdit* answerLine;
	QPushButton* answerButton;
	QListWidget* scoreList;
	void populateList();
	void updateScoreList();
	void answerHandler();
public:
	ParticipantWindow(Quiz& serv, Participant& part);

	void update() override;

};

