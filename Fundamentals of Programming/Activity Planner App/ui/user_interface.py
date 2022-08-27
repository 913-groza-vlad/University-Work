
from domain.validation.exceptions import ValidationException, RepositoryException, UndoRedoException
import datetime
import copy
from services.services import to_float_time
from services.services import Call, CascadedOperation, Operation


class UI():

    def _main_menu(self):
        print("\n\t\t\033[3;34; m ~Application menu~ \033[0;0m\n")
        print("\tdisplay -> display people / activities")
        print("\tadd -> add a person / activity")
        print("\tremove -> remove a person / activity")
        print("\tupdate -> person / activity")
        print("\tsearch_p -> search people")
        print("\tsearch_a -> search activities")
        print("\tstats -> create statistics")
        print("\tundo -> undo the last operation performed")
        print("\tredo -> redo the last operation performed\n")

    def _display_menu(self):
        print("\n\t1 -> display all people")
        print("\t2 -> display all activities\n")

    def _add_menu(self):
        print("\n\t1 -> add a person")
        print("\t2 -> add an activity\n")

    def _remove_menu(self):
        print("\n\t1 -> remove a person")
        print("\t2 -> remove an activity\n")

    def _update_menu(self):
        print("\n\t1 -> update a person")
        print("\t2 -> update an activity\n")

    def _search_people_menu(self):
        print("\n\t1 -> search person by name")
        print("\t2 -> search person by phone number\n")

    def _search_activities_menu(self):
        print("\n\t1 -> search activity by date/time")
        print("\t2 -> search activity by description\n")

    def _statistics_menu(self):
        print("\n\t1 -> activities for a given date")
        print("\t2 -> busiest days")
        print("\t3 -> activities for a given person\n")

    def __init__(self, srv_people, srv_activities, undo_service):
        self.__srv_people = srv_people
        self.__srv_activities = srv_activities
        self.__srv_undo = undo_service

    def __ui_add_person(self):
        try:
            person_id = int(input("\tperson id: "))
        except ValueError:
            print("invalid numerical id!")
            return
        name = input("\tname: ")
        phone_number = input("\tphone number: ")
        self.__srv_people.add_person(person_id, name, phone_number)

        undo_call = Call(self.__srv_people.remove_person, person_id)
        redo_call = Call(self.__srv_people.add_person, person_id, name, phone_number)
        cope = CascadedOperation()
        cope.add(Operation(undo_call, redo_call))
        self.__srv_undo.record(cope)

        print("Person added successfully")

    def __print_people(self):
        nr = self.__srv_people.no_of_people()
        if nr == 0:
            print("There are no people in the list!")
            return
        all_people = self.__srv_people.get_all_people()
        #all_ids = self.__srv_people.get_all_people_ids()
        for _person in all_people:
            print(_person)

    def __ui_add_activity(self, times):
        try:
            activity_id = int(input("\tactivity id: "))
        except ValueError:
            print("invalid numerical id!")
            return
        person_id = []
        all_ids = self.__srv_people.get_all_people_ids()
        cmd = input("\tperson_ids: ")
        ids = cmd.split(",")
        for i in range(len(ids)):
            ids[i] = ids[i].strip()
            try:
                if int(ids[i]) not in all_ids:
                    raise RepositoryException("inexistent person id!")
                else:
                    person_id.append(int(ids[i]))
            except ValueError:
                print("invalid numerical id!")
                return
        try:
            date_entry = input("\tdate (in YYYY-MM-DD format): ")
            day, month, year = map(int, date_entry.split('-'))
            date1 = datetime.date(day, month, year)
        except ValueError:
            print("invalid date!")
            return
        time = input("\ttime: ")
        try:
            f_time = to_float_time(time)
        except ValueError:
            print("invalid time!")
            return
        description = input("\tdescription: ")
        for i in range(len(times)):
            if date1.strftime("%d-%m-%Y") == times[i][0] and abs(f_time-to_float_time(times[i][1])) < 1:
                raise RepositoryException("There is another ongoing activity at this time!")

        self.__srv_activities.add_activity(activity_id, person_id, date1, time, description)

        undo_call = Call(self.__srv_activities.remove_activity, activity_id)
        redo_call = Call(self.__srv_activities.add_activity, activity_id, person_id, date1, time, description)
        cope = CascadedOperation()
        cope.add(Operation(undo_call, redo_call))
        self.__srv_undo.record(cope)

        print("Activity added successfully")

    def __print_activties(self):
        nr = self.__srv_activities.no_of_activities()
        if nr == 0:
            print("There are no activities in the list!")
            return
        all_activities = self.__srv_activities.get_all_activities()
        for _activity in all_activities:
            print(_activity)

    def __ui_remove_person(self):
        try:
            person_id = int(input("\tperson id: "))
        except ValueError:
            print("invalid numerical id!")
            return
        person = self.__srv_people.person_in_list(person_id)
        name = person.get_name()
        phone_number = person.get_phone_number()

        self.__srv_people.remove_person(person_id)

        activities = self.__srv_activities.get_all_activities()
        l_of_person_ids = []
        for act in activities:
            l_of_person_ids.append(act.get_person_id())

        previous_ids = copy.deepcopy(l_of_person_ids)

        self.__srv_activities.removed_person()

        l_of_removed_ids = []
        for act in activities:
            l_of_removed_ids.append(act.get_person_id())

        undo_call = Call(self.__srv_people.add_person, person_id, name, phone_number)
        redo_call = Call(self.__srv_people.remove_person, person_id)
        cope = CascadedOperation()
        cope.add(Operation(undo_call, redo_call))

        for i in range(len(activities)):
            undo_call = Call(self.__srv_activities.modify_person_ids, activities[i].get_activity_id(), previous_ids[i])
            redo_call = Call(self.__srv_activities.modify_person_ids, activities[i].get_activity_id(), l_of_removed_ids[i])
            cope.add(Operation(undo_call, redo_call))

        self.__srv_undo.record(cope)

        print("Person removed successfully")

    def __ui_remove_activity(self):
        try:
            activity_id = int(input("\tactivity id: "))
        except ValueError:
            print("invalid numerical id!")
            return

        activity = self.__srv_activities.activity_in_list(activity_id)
        person_id = activity.get_person_id()
        date = activity.get_date()
        time = activity.get_time()
        description = activity.get_description()

        self.__srv_activities.remove_activity(activity_id)

        undo_call = Call(self.__srv_activities.add_activity, activity_id, person_id, date, time, description)
        redo_call = Call(self.__srv_activities.remove_activity, activity_id)
        cope = CascadedOperation()
        cope.add(Operation(undo_call, redo_call))
        self.__srv_undo.record(cope)

        print("Activity removed successfully")

    def __ui_update_person(self):
        ok = False
        while not ok:
            try:
                ps_id = int(input("\tid of the person you want to update: "))
            except ValueError:
                print("invalid numerical id!")
                return
            try:
                self.__srv_people.person_in_list(ps_id)
                ok = True
            except RepositoryException as re:
                print(re)
                return
            person = self.__srv_people.person_in_list(ps_id)
            name = person.get_name()
            phone_number = person.get_phone_number()

            new_name = input("\tUpdate name: ")
            new_phone = input("\tUpdate phone number: ")
            self.__srv_people.update_person(ps_id, new_name, new_phone)

            undo_call = Call(self.__srv_people.update_person, ps_id, name, phone_number)
            redo_call = Call(self.__srv_people.update_person, ps_id, new_name, new_phone)
            cope = CascadedOperation()
            cope.add(Operation(undo_call, redo_call))
            self.__srv_undo.record(cope)

            print("Person updated successfully")

    def __ui_update_activity(self, times):
        ok = False
        while not ok:
            try:
                act_id = int(input("\tid of the activity you want to update: "))
            except ValueError:
                print("invalid numerical id!")
                return
            try:
                self.__srv_activities.activity_in_list(act_id)
                ok = True
            except RepositoryException as re:
                print(re)
                return

            activity = self.__srv_activities.activity_in_list(act_id)
            date = activity.get_date()
            time = activity.get_time()
            description = activity.get_description()

            person_id = []
            try:
                date_entry = input("\tdate (in YYYY-MM-DD format): ")
                day, month, year = map(int, date_entry.split('-'))
                new_date = datetime.date(day, month, year)
            except ValueError:
                print("invalid date!")
                return
            new_time = input("\ttime: ")
            new_description = input("\tdescription: ")
            try:
                f_time = to_float_time(new_time)
            except ValueError:
                print("invalid time!")
                return
            for i in range(len(times)):
                if new_date.strftime("%d-%m-%Y") == times[i][0] and abs(f_time - to_float_time(times[i][1])) < 1:
                    raise RepositoryException("There is another ongoing activity at this time!")
            self.__srv_activities.update_activity(act_id, person_id, new_date, new_time, new_description)

            undo_call = Call(self.__srv_activities.update_activity, act_id, person_id, date, time, description)
            redo_call = Call(self.__srv_activities.update_activity, act_id, person_id, new_date, new_time, new_description)
            cope = CascadedOperation()
            cope.add(Operation(undo_call, redo_call))
            self.__srv_undo.record(cope)

            print("Activity updated successfully")

    def search_person_by_name(self):
        people = self.__srv_people.get_all_people()
        name = input("\tname: ")
        ok = 0
        for p in people:
            if name.lower() in p.get_name().lower() and name != "":
                print(p)
                ok = 1
        if ok == 0:
            print("Person not found!")

    def search_person_by_phone(self):
        people = self.__srv_people.get_all_people()
        phone = input("\tphone number: ")
        ok = 0
        for p in people:
            if phone in p.get_phone_number() and phone != "":
                print(p)
                ok = 1
        if ok == 0:
            print("Person not found!")

    def search_activity_by_date_time(self):
        activities = self.__srv_activities.get_all_activities()
        date = input("\tdate: ")
        time = input("\ttime: ")
        ok = 0
        for a in activities:
            if date != "" and (date in a.get_date().strftime("%d-%m-%Y") or date in a.get_date().strftime("%Y-%m-%d")) and time in a.get_time():
                print(a)
                ok = 1
        if ok == 0:
            print("Activity not found!")

    def search_activity_by_description(self):
        activities = self.__srv_activities.get_all_activities()
        description = input("\tdescription: ")
        ok = 0
        for a in activities:
            if description.lower() in a.get_description().lower() and description != "":
                print(a)
                ok = 1
        if ok == 0:
            print("Activity not found!")

    def activities_in_a_day(self):
        try:
            date_entry = input("\tdate (in YYYY-MM-DD format): ")
            day, month, year = map(int, date_entry.split('-'))
            date = datetime.date(day, month, year)
        except ValueError:
            print("invalid date!")
            return
        activities = self.__srv_activities.sort_activities_on_date(date)
        if len(activities) == 0:
            print("No activities on this date")
        for a in activities:
            print(a)

    def upcoming_activities(self):
        ids = self.__srv_people.get_all_people_ids()
        activities = self.__srv_activities.get_all_activities()
        try:
            person_id = int(input("\tperson id: "))
        except ValueError:
            print("invalid numerical id!")
            return
        if person_id not in ids:
            raise RepositoryException("inexisting id!")
        else:
            ok = 0
            current_date = datetime.datetime.now()
            for a in activities:
                if (person_id in a.get_person_id() and current_date.strftime("%d-%m-%Y") < a.get_date().strftime("%d-%m-%Y")) or (current_date.strftime("%d-%m-%Y") == a.get_date().strftime("%d-%m-%Y") and current_date.strftime("%H:%M") < a.get_time()):
                    print(a)
                    ok = 1
            if ok == 0:
                print("No upcoming activity for the person with id", person_id)

    def busiest_days(self):
        dates = self.__srv_activities.sort_upcoming_dates()
        for d in dates:
            print(d[0], "activities on date", d[1].strftime("%d-%m-%Y"))

    def run(self):
        self._main_menu()
        while True:
            times = []
            self.__srv_activities.create_list_of_time(times)
            cmd = input(">>>")
            if cmd == "exit":
                return
            if cmd == "":
                continue
            if cmd == "add":
                self._add_menu()
                cmd_add = input("Option: ")
                if cmd_add == "1":
                    try:
                        self.__ui_add_person()
                    except ValidationException as ve:
                        print(ve)
                    except RepositoryException as re:
                        print(re)
                elif cmd_add == "2":
                    try:
                        self.__ui_add_activity(times)
                    except ValidationException as ve:
                        print(ve)
                    except RepositoryException as re:
                        print(re)
                else:
                    print("invalid option!")
            elif cmd == "display":
                self._display_menu()
                cmd_display = input("Option: ")
                if cmd_display == "1":
                    self.__print_people()
                elif cmd_display == "2":
                    self.__print_activties()
                else:
                    print("invalid option!")
            elif cmd == "remove":
                self._remove_menu()
                cmd_remove = input("Option: ")
                if cmd_remove == "1":
                    try:
                        self.__ui_remove_person()
                    except RepositoryException as re:
                        print(re)
                elif cmd_remove == "2":
                    try:
                        self.__ui_remove_activity()
                    except RepositoryException as re:
                        print(re)
                else:
                    print("invalid option!")
            elif cmd == "update":
                self._update_menu()
                cmd_update = input("Option: ")
                if cmd_update == "1":
                    try:
                        self.__ui_update_person()
                    except ValidationException as ve:
                        print(ve)
                    except RepositoryException as re:
                        print(re)
                elif cmd_update == "2":
                    try:
                        self.__ui_update_activity(times)
                    except ValidationException as ve:
                        print(ve)
                    except RepositoryException as re:
                        print(re)
                else:
                    print("invalid option!")
            elif cmd == "search_p":
                self._search_people_menu()
                cmd_search = input("Option: ")
                if cmd_search == "1":
                    self.search_person_by_name()
                elif cmd_search == "2":
                    self.search_person_by_phone()
                else:
                    print("invalid option!")
            elif cmd == "search_a":
                self._search_activities_menu()
                cmd_search = input("Option: ")
                if cmd_search == "1":
                    self.search_activity_by_date_time()
                elif cmd_search == "2":
                    self.search_activity_by_description()
                else:
                    print("invalid option!")
            elif cmd == "stats":
                self._statistics_menu()
                cmd_stats = input("Option: ")
                if cmd_stats == "1":
                    self.activities_in_a_day()
                elif cmd_stats == "2":
                    self.busiest_days()
                elif cmd_stats == "3":
                    try:
                        self.upcoming_activities()
                    except RepositoryException as re:
                        print(re)
                else:
                    print("invalid option!")
            elif cmd == "undo":
                try:
                    self.__srv_undo.undo()
                    print("Successfully undo!")
                except UndoRedoException as ure:
                    print(ure)
            elif cmd == "redo":
                try:
                    self.__srv_undo.redo()
                    print("Successfully redo!")
                except UndoRedoException as ure:
                    print(ure)
            else:
                print("invalid command!")