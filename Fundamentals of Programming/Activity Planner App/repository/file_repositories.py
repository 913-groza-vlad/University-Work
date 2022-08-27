
import datetime
import pickle
from domain.person import Person
from domain.activity import Activity
from repository.repositories import RepoPeople, RepoActivities, RepositoryException


class PeopleTextFileRepository(RepoPeople):

    def __init__(self, file_name):
        RepoPeople.__init__(self)
        self._file_name = file_name
        self._load_file()

    def _load_file(self):
        try:
            f = open(self._file_name, "r")
        except IOError:
            raise RepositoryException("Input file not found")
        line = f.readline().strip()
        while line != "":
            atr = line.split(";")
            person = Person(int(atr[0]), atr[1], atr[2])
            RepoPeople.add_person(self, person)
            line = f.readline().strip()
        f.close()

    def _save_file(self):
        f = open(self._file_name, 'w')
        people = RepoPeople.get_all(self)
        for p in people:
            person = str(p.get_person_id()) + ";" + p.get_name() + ";" + p.get_phone_number()
            person += "\n"
            f.write(person)
        f.close()

    def add_person(self, person):
        RepoPeople.add_person(self, person)
        self._save_file()

    def search_by_id(self, person_id):
        return RepoPeople.search_by_id(self, person_id)

    def remove_by_id(self, person_id):
        RepoPeople.remove_by_id(self, person_id)
        self._save_file()

    def modify_person(self, person, new_name, new_phone):
        RepoPeople.modify_person(self, person, new_name, new_phone)
        self._save_file()

    def get_all(self):
        return RepoPeople.get_all(self)

    def get_all_ids(self):
        return RepoPeople.get_all_ids(self)

    def __len__(self):
        return RepoPeople.__len__(self)


class ActivitiesTextFileRepository(RepoActivities):

    def __init__(self, file_name):
        RepoActivities.__init__(self)
        self._file_name = file_name
        self._load_file()

    def _load_file(self):
        try:
            f = open(self._file_name, "r")
        except IOError:
            raise RepositoryException("Input file not found")
        line = f.readline().strip()
        while line != "":
            atr = line.split(";")
            id_list = atr[1].split(",")
            for i in range(len(id_list)):
                id_list[i] = int(id_list[i])
            dates = atr[2].split("-")
            date = datetime.date(int(dates[2]), int(dates[1]), int(dates[0]))
            activity = Activity(int(atr[0]), id_list, date, atr[3], atr[4])
            RepoActivities.add_activity(self, activity)
            line = f.readline().strip()
        f.close()

    def _save_file(self):
        f = open(self._file_name, 'w')
        activities = RepoActivities.get_all(self)
        for a in activities:
            person_ids = a.get_person_id()
            string_person_ids = [str(id) for id in person_ids]
            str_person_ids = ",".join(string_person_ids)
            activity = str(a.get_activity_id()) + ";" + str_person_ids + ";" + a.get_date().strftime("%d-%m-%Y") + ";" + a.get_time() + ";" + a.get_description()
            activity += "\n"
            f.write(activity)
        f.close()

    def add_activity(self, activity):
        RepoActivities.add_activity(self, activity)
        self._save_file()

    def search_by_id(self, activity_id):
        return RepoActivities.search_by_id(self, activity_id)

    def remove_by_id(self, activity_id):
        RepoActivities.remove_by_id(self, activity_id)
        self._save_file()

    def modify_activity(self, activity, new_date, new_time, new_description):
        RepoActivities.modify_activity(self, activity, new_date, new_time, new_description)
        self._save_file()

    def get_all(self):
        return RepoActivities.get_all(self)

    def modify_id(self, activity, person_id):
        RepoActivities.modify_id(self, activity, person_id)
        self._save_file()

    def __len__(self):
        return RepoActivities.__len__(self)


class PeopleBinFileRepository(RepoPeople):

    def __init__(self, file_name):
        RepoPeople.__init__(self)
        self._file_name = file_name
        self._read_from_file()

    def _read_from_file(self):
        with open(self._file_name, "rb") as f:
            try:
                self._people = pickle.load(f)
            except EOFError:
                pass

    def _save_file(self):
        with open(self._file_name, "wb") as f:
            pickle.dump(self._people, f)

    def _write_to_file(self):
        with open(self._file_name, "wb") as f:
            for p in self._people:
                person = str(p.get_person_id()) + ";" + p.get_name() + ";" + p.get_phone_number()
                person += "\n"
                pickle.dump(self._people, f)

    def add_person(self, person):
        RepoPeople.add_person(self, person)
        self._save_file()

    def search_by_id(self, person_id):
        return RepoPeople.search_by_id(self, person_id)

    def remove_by_id(self, person_id):
        RepoPeople.remove_by_id(self, person_id)
        self._save_file()

    def modify_person(self, person, new_name, new_phone):
        RepoPeople.modify_person(self, person, new_name, new_phone)
        self._save_file()

    def get_all(self):
        return RepoPeople.get_all(self)

    def get_all_ids(self):
        return RepoPeople.get_all_ids(self)

    def __len__(self):
        return RepoPeople.__len__(self)


class ActivitiesBinFileRepository(RepoActivities):

    def __init__(self, file_name):
        RepoActivities.__init__(self)
        self._file_name = file_name
        self._read_from_file()

    def _read_from_file(self):
        with open(self._file_name, "rb") as f:
            try:
                self._activities = pickle.load(f)
            except EOFError:
                pass

    def _save_file(self):
        with open(self._file_name, "wb") as f:
            pickle.dump(self._activities, f)

    def _write_to_file(self):
        with open(self._file_name, "wb") as f:
            for a in self._activities:
                activity = str(a.get_activity_id()) + ";" + str(a.get_person_id()) + ";" + a.get_date().strftime("%d-%m-%Y") + ";" + a.get_time() + ";" + a.get_description()
                activity += "\n"
                pickle.dump(self._activities, f)

    def add_activity(self, activity):
        RepoActivities.add_activity(self, activity)
        self._save_file()

    def search_by_id(self, activity_id):
        return RepoActivities.search_by_id(self, activity_id)

    def remove_by_id(self, activity_id):
        RepoActivities.remove_by_id(self, activity_id)
        self._save_file()

    def modify_activity(self, activity, new_date, new_time, new_description):
        RepoActivities.modify_activity(self, activity, new_date, new_time, new_description)
        self._save_file()

    def get_all(self):
        return RepoActivities.get_all(self)

    def modify_id(self, activity, person_id):
        RepoActivities.modify_id(self, activity, person_id)
        self._save_file()

    def __len__(self):
        return RepoActivities.__len__(self)