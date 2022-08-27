
class Activity:

    def __init__(self, activity_id, person_id, date, time, description):
        """
        Constructor that builds the instance of class Activity
        :param activity_id: a positive integer
        :param person_id: a list of positive integers
        :param date: a string in the form dd-mm-yyyy
        :param time: a string in the form h:m
        :param description: a non-empty string
        """
        self.__activty_id = activity_id
        self.__person_id = person_id
        self.__date = date
        self.__time = time
        self.__description = description

    def get_activity_id(self):
        """
        Function that returns the id of an activity
        :return:
        """
        return self.__activty_id

    def get_person_id(self):
        """
        Function that returns the list of person ids of an activity
        :return:
        """
        return self.__person_id

    def get_date(self):
        """
        Function that returns the date of an activity
        :return:
        """
        return self.__date

    def get_time(self):
        """
        Function that returns the time of an activity
        :return:
        """
        return self.__time

    def get_description(self):
        """
        Function that returns the description of an activity
        """
        return self.__description

    def set_date(self, value):
        """
        Function that sets the date of an activity to a new value
        :param value: a string in the form dd-mm-yyyy
        :return:
        """
        self.__date = value

    def set_time(self, value):
        """
        Function that sets the time of an activity to a new value
        :param value: a string in h:m form
        :return:
        """
        self.__time = value

    def set_description(self, value):
        """
        Function that sets the description of an activity to a new value
        :param value: a non-empty string
        :return:
        """
        self.__description = value

    def get_date_time(self):
        return [self.get_date().strftime("%d-%m-%Y"), self.get_time()]

    def set_person_id(self, value):
        self.__person_id = value

    def __str__(self):
        """
        Method that modifies the builtin function __str__
        """
        return "Activity " + str(self.get_activity_id()) + ". on " + self.get_date().strftime("%d-%m-%Y") + " at " + self.get_time() + " -> " + self.get_description() + ";   Participants ids: " + str(self.get_person_id())

