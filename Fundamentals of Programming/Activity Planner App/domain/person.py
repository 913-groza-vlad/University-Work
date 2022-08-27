
class Person:

    def __init__(self, person_id, name, phone_number):
        """
        Constructor that builds the instance of class Person
        :param person_id: a positive integer, the id of a person
        :param name: a non-empty string
        :param phone_number: a string of 10 digits
        """
        self.__person_id = person_id
        self.__name = name
        self.__phone_number = phone_number

    def get_person_id(self):
        """
        Function that returns the id of a person
        :return:
        """
        return self.__person_id

    def get_name(self):
        """
        Function that returns the name of a person
        :return:
        """
        return self.__name

    def get_phone_number(self):
        """
        Function that returns the phone number of a person
        :return:
        """
        return self.__phone_number

    def set_name(self, value):
        """
        Function that sets the name of a person to a new value
        :param value: a non-empty string
        :return:
        """
        self.__name = value

    def set_phone_number(self, value):
        """
        Function that sets the phone number of a person to a new value
        :param value: a string of 10 digits
        :return:
        """
        self.__phone_number = value

    def __eq__(self, other):
        """
        Method that modifies the builtin function __eq__
        :param other: an entity of class Person
        """
        return self.__person_id == other.__person_id

    def __str__(self):
        """
        Method that modifies the builtin function __str__
        """
        return str(self.get_person_id()) + ". " + self.get_name() + " -> tel: " + self.get_phone_number()

