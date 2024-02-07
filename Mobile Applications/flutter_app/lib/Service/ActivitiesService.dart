
import 'package:flutter/material.dart';
import 'package:flutter_app/Repo/Repository.dart';

import '../Domain/Activity.dart';

class ActivitiesService {
  Repository activitiesRepo;

  ActivitiesService(this.activitiesRepo);

  List<Activity> getActivities() {
    return activitiesRepo.getActivities();
  }

  Activity getActivity(int id) {
    return activitiesRepo.getActivity(id);
  }

  bool isOverlapping(TimeOfDay newStartTime, TimeOfDay newEndTime, TimeOfDay existingStartTime, TimeOfDay existingEndTime) {
    final newStartMinutes = newStartTime.hour * 60 + newStartTime.minute;
    final newEndMinutes = newEndTime.hour * 60 + newEndTime.minute;

    final existingStartMinutes = existingStartTime.hour * 60 + existingStartTime.minute;
    final existingEndMinutes = existingEndTime.hour * 60 + existingEndTime.minute;

    return newStartMinutes < existingEndMinutes && newEndMinutes > existingStartMinutes;
  }

  void checkOverlap(Activity newActivity) {
      for (Activity activity in getActivities()) {
        if (newActivity.date == activity.date && newActivity.id != activity.id) {
          if (isOverlapping(newActivity.startTime, newActivity.endTime, activity.startTime, activity.endTime)) {
            throw Exception("Activity overlaps with an existing activity");
          }
        }
      }
  }

  void checkInvalidTime(Activity activity) {
    final startTimeMins = activity.startTime.hour * 60 + activity.startTime.minute;
    final endTimeMins = activity.endTime.hour * 60 + activity.endTime.minute;
    if (startTimeMins > endTimeMins) {
      throw Exception("Activity's start time is after end time");
    }
  }

  void addActivity(Activity activity) {
    if (activity.title == "") {
      throw Exception("Insert a title for this activity");
    }
    checkOverlap(activity);
    checkInvalidTime(activity);
    activitiesRepo.addActivity(activity);
  }

  void updateActivity(Activity activity) {
    if (activity.title == "") {
      throw Exception("Insert a title for this activity");
    }
    checkOverlap(activity);
    checkInvalidTime(activity);
    activitiesRepo.updateActivity(activity);
  }

  void removeActivity(Activity activity) {
    activitiesRepo.removeActivity(activity);
  }
}