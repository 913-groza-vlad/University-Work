
import 'package:flutter/material.dart';

import '../Domain/Activity.dart';

class Repository {
  late List<Activity> activities;

  Repository() {
    activities = [
      Activity("Jogging", "sport", DateTime(2023, 12, 27), TimeOfDay(hour: 12, minute: 15), TimeOfDay(hour: 13, minute: 30), "jogging in the Central Park"),
      Activity("Weekly Meeting", "work", DateTime(2023, 10, 31), TimeOfDay(hour: 9, minute: 30), TimeOfDay(hour: 10, minute: 0), "call with the clients"),
      Activity("Hip Hop Party", "social", DateTime(2023, 11, 3), TimeOfDay(hour: 22, minute: 0), TimeOfDay(hour: 23, minute: 59), "party with friends"),
      Activity("AI Project", "education", DateTime(2023, 11, 11), TimeOfDay(hour: 8, minute: 20), TimeOfDay(hour: 11, minute: 30), "working on the AI assignment"),
      Activity("Dinner", "social", DateTime(2023, 11, 15), TimeOfDay(hour: 19, minute: 0), TimeOfDay(hour: 20, minute: 0), "dinner with family"),
      Activity("Dentist Appointment", "health", DateTime(2023, 11, 30), TimeOfDay(hour: 14, minute: 0), TimeOfDay(hour: 16, minute: 30), "solving a tooth decay problem")
    ];
  }

  void addActivity(Activity activity) {
    activities.add(activity);
  }

  void removeActivity(Activity activity) {
    activities.remove(activity);
  }

  void updateActivity(Activity activity) {
    int index = activities.indexWhere((element) => element.id == activity.id);
    activities[index] = activity;
  }

  List<Activity> getActivities() {
    return activities;
  }

  Activity getActivity(int id) {
    return activities.firstWhere((element) => element.id == id, orElse: () => Activity("","",DateTime.now(),TimeOfDay.now(),TimeOfDay.now(),""));
  }
}