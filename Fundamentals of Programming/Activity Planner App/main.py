
import configparser
from ui.user_interface import UI
from services.services import ServicePeople, ServiceActivities, UndoService
from domain.validation.validators import ValidatorPerson, ValidatorActivity
from repository.repositories import RepoPeople, RepoActivities
from repository.file_repositories import PeopleTextFileRepository, ActivitiesTextFileRepository, PeopleBinFileRepository, ActivitiesBinFileRepository

if __name__ == '__main__':

    config = configparser.RawConfigParser()
    config.read('settings.properties')
    options_dict = dict(config.items('OPTIONS'))

    repo_people = None
    repo_activities = None

    valid_person = ValidatorPerson()
    valid_activity = ValidatorActivity()

    if options_dict['repository'] == "inmemory":
        repo_people = RepoPeople()
        repo_activities = RepoActivities()
    elif options_dict['repository'] == "textfiles":
        repo_people = PeopleTextFileRepository(options_dict['people'])
        repo_activities = ActivitiesTextFileRepository(options_dict['activities'])
    elif options_dict['repository'] == "binaryfiles":
        repo_people = PeopleBinFileRepository(options_dict['people'])
        repo_activities = ActivitiesBinFileRepository(options_dict['activities'])

    undo_service = UndoService()
    srv_people = ServicePeople(valid_person, repo_people)
    srv_activities = ServiceActivities(valid_activity, repo_activities, repo_people)

    if options_dict['generate'] == "True":
        srv_people.generate_people()
        srv_activities.generate_activities()

    ui = UI(srv_people, srv_activities, undo_service)
    ui.run()
