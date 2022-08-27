#include "QtWidgetsApplication.h"
#include <QtWidgets/QApplication>
#include <crtdbg.h>
#include "UI.h"
#include "GUI.h"

using namespace std;

int main(int argc, char* argv[])
{
	QApplication a(argc, argv);
	//QtWidgetsApplication w;

	{
		std::string line, repository, type;
		Repository* repo = nullptr;
		Repository* dogsList = nullptr;
		Validator valid;
		repo = new FileRepository("dogs.txt");

		ifstream fin("settings.properties");
		getline(fin, line);
		stringstream parser(line);
		getline(parser, repository, '=');
		if (repository == "adoptionList")
		{
			getline(parser, type);
			if (type == "csv")
				dogsList = new CSVRepository("dogs.csv");
			else if (type == "html")
				dogsList = new HTMLRepository("dogs.htm");
			else
			{
				cout << "Invalid type of file!\n";
				return -1;
			}
		}
		fin.close();

		Services serv{ repo, dogsList, valid };
		GUI gui{ serv };
		gui.show();
		return a.exec();
	}

	_CrtDumpMemoryLeaks();
}