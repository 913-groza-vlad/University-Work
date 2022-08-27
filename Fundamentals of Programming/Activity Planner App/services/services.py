
from domain.person import Person
from domain.activity import Activity
from domain.validation.exceptions import UndoRedoException
import random
import datetime


class ServicePeople:

    def __init__(self, valid_person, repo_people):
        self.__valid_person = valid_person
        self.__repo_people = repo_people

    def no_of_people(self):
        """
        Function that returns number of people in the repo
        (length of the list)
        :return: length
        """
        return len(self.__repo_people)

    def add_person(self, person_id, name, phone_number):
        """
        Function that validates a person an adds it to
        the list if it is valid
        :param person_id: a positive integer, the id of a person
        :param name: a non-empty string
        :param phone_number: a string of 10 digits
        """
        person = Person(person_id, name, phone_number)
        self.__valid_person.validate(person)
        self.__repo_people.add_person(person)

    def remove_person(self, person_id):
        """
        Function that removes the person with 'person_id'
        from the list
        :param person_id: a positive integer, the id of a person
        :return:
        """
        return self.__repo_people.remove_by_id(person_id)

    def get_all_people(self):
        """
        Function that returns the list of people
        """
        return self.__repo_people.get_all()

    def get_all_people_ids(self):
        """
        Function that returns a list of people's ids
        :return:
        """
        return self.__repo_people.get_all_ids()

    def remove_id(self, idp):
        """
        Function that removes the idp from the list of people's ids
        :param idp: an id
        :return:
        """
        self.get_all_people_ids.remove(idp)

    def person_in_list(self, person_id):
        """
        Function that returns the value of the function 'search_by_id'
        """
        return self.__repo_people.search_by_id(person_id)

    def update_person(self, person_id, new_name, new_phone):
        """
        Function that checks if a person is in the list,
        validates it if the new atributes of the person are valid
        and modify their values in the list
        :param person_id: a positive integer, the id of a person
        :param new_name: a non-empty string
        :param new_phone: a string of 10 digits
        :return:
        """
        person = self.person_in_list(person_id)
        person1 = Person(person_id, new_name, new_phone)
        self.__valid_person.validate(person1)
        self.__repo_people.modify_person(person, new_name, new_phone)

    def generate_person(self, l_of_ids):
        l_of_names = ['Mike', 'Ian', 'Jordan', 'John', 'Kevin', 'Dani', 'Drake', 'Kobe', 'Joe', 'Harry', 'Jose', 'Leo', 'Ionut',
                      'Kim', 'Maria', 'Alice', 'Andrei', 'Liz', 'Oscar', 'Kate', 'John S', 'Steve', 'Mike Hunt', 'Harry Kane', 'Kevin P']
        n = 9
        string_of_digits = ''.join(["{}".format(random.randint(0, 9)) for num in range(0, n)])

        id_person = random.choice(l_of_ids)
        name = random.choice(l_of_names)
        l_of_ids.remove(id_person)
        phone = '0' + string_of_digits

        return id_person, name, phone

    def generate_people(self):
        l_of_ids = list(range(1, 100))
        n = random.randint(15, 20)
        for i in range(0, n):
            id_person, name, phone_number = self.generate_person(l_of_ids)
            self.add_person(id_person, name, phone_number)


class ServiceActivities:

    def __init__(self, valid_activity, repo_activities, repo_people):
        self.__valid_activity = valid_activity
        self.__repo_activities = repo_activities
        self.__repo_people = repo_people

    def no_of_activities(self):
        """
        Function that returns number of activities in the repo
        (length of the list)
        :return: length
        """
        return len(self.__repo_activities)

    def add_activity(self, activity_id, person_id, date, time, description):
        """
        Function that validates an activity and add it to the list of activities
        if it is valid
        :param activity_id: a positive integer, id of an activity
        :param person_id: list of people ids
        :param date: a string in form dd-mm-yyyy
        :param time: a string in form h:m
        :param description: a non-empty string
        :return:
        """
        activity = Activity(activity_id, person_id, date, time, description)
        self.__valid_activity.validate(activity)
        self.__repo_activities.add_activity(activity)

    def remove_activity(self, activity_id):
        """
        Function that removes the activity with id 'activity_id'
        from the list
        :param activity_id: a positive integer, the id of an activity
        """
        return self.__repo_activities.remove_by_id(activity_id)

    def get_all_activities(self):
        """
        Function that returns the list of people
        :return: list of activities
        """
        return self.__repo_activities.get_all()

    def activity_in_list(self, activity_id):
        """
        Function that returns the value of the function 'search_by_id'
        """
        return self.__repo_activities.search_by_id(activity_id)

    def update_activity(self, activity_id, person_id, new_date, new_time, new_description):
        """
        Function that checks if an activity is in the list,
        validates it if the new atributes of the activity are valid
        and modify their values in the list
        :param activity_id: a positive integer, id of an activity
        :param person_id: list of people ids
        :param new_date: a string in form dd-mm-yyyy
        :param new_time: a string in form h:m
        :param new_description: a non-empty string
        """
        activity = self.activity_in_list(activity_id)
        activity1 = Activity(activity_id, person_id, new_date, new_time, new_description)
        self.__valid_activity.validate(activity1)
        self.__repo_activities.modify_activity(activity, new_date, new_time, new_description)

    def removed_person(self):
        """
        Function that removes the id of a person from an activity
        if the person was removed from the list of people
        """
        activities = self.get_all_activities()
        ids = self.__repo_people.get_all_ids()

        def accept(id):
            if id in ids:
                return True
            return False

        for _act in activities:
            person_ids = _act.get_person_id()
            changed_ids = self.__repo_activities.filter(person_ids, accept)
            self.__repo_activities.modify_id(_act, changed_ids)

        self.__repo_activities._save_file()

    def modify_person_ids(self, activity_id, person_id):
        activity = self.activity_in_list(activity_id)
        self.__repo_activities.modify_id(activity, person_id)

    def create_list_of_time(self, times):
        activities = self.get_all_activities()
        for _act in activities:
            date_time = _act.get_date_time()
            times.append(date_time)

    def generate_activity(self, l_of_ids, l_times):
        l_of_descriptions = ['basketball', 'rap', 'dance', 'football', 'tennis', 'Video games', 'film', 'shopping',
                             'watching TV', 'running', 'party', 'dinner', 'music', 'concert', 'football match', 'coding']
        start_date = datetime.date(2021, 12, 17)
        end_date = datetime.date(2021, 12, 22)

        id_activity = random.choice(l_of_ids)
        l_of_ids.remove(id_activity)
        id_person = []
        people_ids = self.__repo_people.get_all_ids()
        nr = random.randint(1, 5)
        for i in range(0, nr):
            _id = random.choice(people_ids)
            id_person.append(_id)
            people_ids.remove(_id)

        time_between_dates = end_date - start_date
        days_between_dates = time_between_dates.days
        random_number_of_days = random.randrange(days_between_dates)
        date = start_date + datetime.timedelta(days=random_number_of_days)
        time = ''
        available_time = 0
        while available_time == 0:
            ok = 1
            time = generate_time()
            for i in range(len(l_times)):
                if abs(to_float_time(time) - to_float_time(l_times[i])) < 1:
                    ok = 0
            if ok == 1:
                available_time = 1
                l_times.append(time)

        description = random.choice(l_of_descriptions)

        return id_activity, id_person, date, time, description

    def generate_activities(self):
        l_of_ids = list(range(1, 25))
        l_times = []
        n = random.randint(6, 9)
        for i in range(0, n):
            id_activity, id_person, date, time, description = self.generate_activity(l_of_ids, l_times)
            self.add_activity(id_activity, id_person, date, time, description)

    def activities_on_date(self, date):
        activities = self.get_all_activities()

        def accept(a):
            return a.get_date() == date

        return self.__repo_activities.filter(activities, accept)

    def sort_activities_on_date(self, date):
        activities = self.activities_on_date(date)

        def compare(x, y):
            return x.get_time() >= y.get_time()

        return self.__repo_activities.sort(activities, compare)

    def get_upcoming_dates(self):
        up_dates = []
        current_date = datetime.datetime.now()
        activities = self.get_all_activities()

        for a in activities:
            if current_date.strftime("%d-%m-%Y") <= a.get_date().strftime("%d-%m-%Y") and a.get_date() not in up_dates:
                up_dates.append(a.get_date())

        return up_dates

    def sort_upcoming_dates(self):
        sorted_dates = []
        dates = self.get_upcoming_dates()
        for date in dates:
            activities = self.activities_on_date(date)
            nr_activities = len(activities)
            sorted_dates.append([nr_activities, date])

        def compare(x, y):
            return x[0] <= y[0]

        return self.__repo_activities.sort(sorted_dates, compare)


def to_float_time(time):
    parts = time.split(":")
    parts[0] = int(parts[0])
    parts[1] = int(parts[1])
    parts[1] = parts[1] / 100
    return parts[0]+parts[1]


def generate_time():
    rtime = int(random.random() * 86400)
    hours = int(rtime / 3600)
    minutes = int((rtime - hours * 3600) / 60)
    time = '%02d:%02d' % (hours, minutes)

    return time


class UndoService:

    def __init__(self):
        self._history = []
        self._index = -1

    def record(self, operation):
        self._index += 1
        self._history.insert(self._index, operation)
        del self._history[self._index+1:]

    def undo(self):
        if self._index == -1:
            raise UndoRedoException("No operation to undo!")
        self._history[self._index].undo()
        self._index -= 1

    def redo(self):
        if self._index == len(self._history)-1:
            raise UndoRedoException("No operation to redo!")
        self._history[self._index+1].redo()
        self._index += 1


class Call:
    def __init__(self, function_name, *function_params):
        self._function_name = function_name
        self._function_params = function_params

    def call(self):
        self._function_name(*self._function_params)


class Operation:
    def __init__(self, undo_call, redo_call):
        self._undo_call = undo_call
        self._redo_call = redo_call

    def undo(self):
        self._undo_call.call()

    def redo(self):
        self._redo_call.call()


class CascadedOperation:
    def __init__(self):
        self._operations = []

    def add(self, operation):
        self._operations.append(operation)

    def undo(self):
        for oper in self._operations:
            oper.undo()

    def redo(self):
        for oper in self._operations:
            oper.redo()