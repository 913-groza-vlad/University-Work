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

class PresenterWindow : public QWidget, public Observer
{
private:
	Quiz& serv;
	QListWidget* questionsList;
	QPushButton* addButton;
	QLineEdit* idLine;
	QLineEdit* textLine;
	QLineEdit* answerLine;

	void populateList();
	void addHandler();
public:
	PresenterWindow(Quiz& serv);

	void update() override;

};

