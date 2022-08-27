#include "QtWidgetsApplication1.h"
#include <QtWidgets/QApplication>
#include "PresenterWindow.h"
#include "ParticipantWindow.h"

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
   
    {
        Repository* partRepo = new Repository{ "participants.txt", "questions.txt", false };
        Quiz serv{ partRepo };
        PresenterWindow* pw = new PresenterWindow{ serv };
        pw->show();

        std::vector<Participant> participants = serv.getAllParticipants();
        for (auto p : participants)
        {
            ParticipantWindow* partw = new ParticipantWindow{ serv, p };
            partw->show();
        }

        return a.exec();
    }

}
