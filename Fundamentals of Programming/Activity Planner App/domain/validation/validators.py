
from domain.validation.exceptions import ValidationException


class ValidatorPerson:

    def validate(self, person):
        error = ""
        if person.get_person_id()<0:
            error += "invalid id!\n"
        if person.get_name() == "":
            error += "invalid name!\n"
        if len(person.get_phone_number())!=10 or person.get_phone_number()[0]!='0':
            error+= "invalid phone number!\n"
        if len(error) > 0:
            raise ValidationException(error)


class ValidatorActivity:

    def validate_time(self, time):
        if len(time) != 5:
            return False
        if time[2] !=':':
            return False
        times = time.split(':')
        if times[0].isnumeric() == False or times[1].isnumeric() == False or int(times[0])<0 or int(times[0])>23 or int(times[1])<0 or int(times[1])>59:
            return False
        return True

    def validate(self, activity):
        error = ""
        if activity.get_activity_id() < 0:
            error += "invalid id!\n"
        if self.validate_time(activity.get_time()) == False:
            error += "invalid time!\n"
        if activity.get_description() == "":
            error += "invalid description!\n"
        if len(error) > 0:
            raise ValidationException(error)


