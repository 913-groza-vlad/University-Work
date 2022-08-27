
from domain.validation.exceptions import RepositoryException
from structure import Data


class RepoPeople:

    def __init__(self):
        """
        Constructor of the instance of class RepoPeople
        """
        self._people = Data()

    def __len__(self):
        return len(self._people)

    def add_person(self, person):
        """
        Function that adds a person in the repository or raise an exception
        if a person with the same id has already been in the list
        :param person: a person
        """
        for _person in self._people:
            if _person.get_person_id() == person.get_person_id():
                raise RepositoryException("existing id!")
        self._people.add(person)

    def search_by_id(self, person_id):
        """
        Function that search a person by its id and returns it
        if the person is in the list; otherwise an exception is raised
        :param person_id: a positive integer, id of a person
        """
        ok = True
        for _person in self._people:
            if _person.get_person_id() == person_id:
                return _person
        if ok:
            raise RepositoryException("inexisting id!")

    def remove_by_id(self, person_id):
        """
        Function that removes the person having the id 'person_id'
        from the list if there exists one; otherwise an exception is raised
        :param person_id:  a positive integer, id of a person
        """
        ok = True
        i = 0
        while ok and i < len(self._people):
            if self._people[i].get_person_id() == person_id:
                ok = False
                self._people.__delitem__(i)
            i += 1
        if ok:
            raise RepositoryException("inexisting id!")

    def modify_person(self, person, new_name, new_phone):
        """
        Function that sets new values for the atributes
        'name' and 'phone number' of a person
        :param person: a person
        :param new_name: a non-empty string
        :param new_phone: a string of 10 digits
        """
        person.set_name(new_name)
        person.set_phone_number(new_phone)

    def get_all(self):
        """
        Function that returns a copy of the list of people
        """
        return self._people

    def get_all_ids(self):
        """
        Function that creates and returns a list of ids of every person
        """
        ids = []
        for _person in self._people:
            ids.append(_person.get_person_id())
        return ids


class RepoActivities:

    def __init__(self):
        """
        Constructor of the instance of class RepoActivities
        """
        self._activities = Data()

    def __len__(self):
        return len(self._activities)

    def add_activity(self, activity):
        """
        Function that adds an activity in the repository or raise an exception
        if an activity with the same id has already been in the list
        :param activity: an activity
        :return:
        """
        for _act in self._activities:
            if _act.get_activity_id() == activity.get_activity_id():
                raise RepositoryException("existing id!")
        self._activities.add(activity)

    def search_by_id(self, activity_id):
        """
        Function that search a person by its id and returns it
        if the person is in the list; otherwise an exception is raised
        :param activity_id: positive integer, id of an activity
        """
        ok = True
        for _act in self._activities:
            if _act.get_activity_id() == activity_id:
                return _act
        if ok:
            raise RepositoryException("inexisting id!")

    def remove_by_id(self, activity_id):
        """
        Function that removes the activity having the id 'activity_id'
        from the list if there exists one; otherwise an exception is raised
        :param activity_id:  a positive integer, id of an activity
        """
        ok = True
        i = 0
        while ok and i < len(self._activities):
            if self._activities[i].get_activity_id() == activity_id:
                ok = False
                self._activities.__delitem__(i)
            i += 1
        if ok:
            raise RepositoryException("inexisting id!")

    def modify_activity(self, activity, new_date, new_time, new_description):
        """
        Function that sets new values for the atributes
        'date', 'time' and 'description' of activity
        :param activity: an activity
        :param new_date: a string in form dd-mm-yyyy
        :param new_time: a string in form h:m
        :param new_description: a non-empty string
        """
        activity.set_date(new_date)
        activity.set_time(new_time)
        activity.set_description(new_description)

    def modify_id(self, activity, person_id):
        activity.set_person_id(person_id)

    def get_all(self):
        """
        Function that returns a copy of the list of activities
        :return:
        """
        return self._activities

    def _save_file(self):
        pass

    def filter(self, l, accept):
        return Data.filter(self, l, accept)

    def sort(self, l, compare):
        return Data.gnomeSort(self, l, compare)


