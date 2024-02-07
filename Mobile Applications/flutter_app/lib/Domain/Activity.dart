

import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

class Activity {
  static int currentId = 0;
  late int id;
  String title;
  String type;
  DateTime date;
  TimeOfDay startTime;
  TimeOfDay endTime;
  String description;

  Activity(this.title, this.type, this.date, this.startTime, this.endTime, this.description) {
    id = ++currentId;
  }

  Activity.namedConstructor(this.id, this.title, this.type, this.date, this.startTime, this.endTime, this.description);

  String formatDate(DateTime date) {
    return DateFormat('dd/MM/yyyy').format(date);
  }

  String formatTime(TimeOfDay time) {
    final now = DateTime.now();
    final dateTime = DateTime(now.year, now.month, now.day, time.hour, time.minute);
    final formatter = DateFormat('HH:mm');
    return formatter.format(dateTime);
  }

  @override
  String toString() {
    return "$title\nDate: ${formatDate(date)}\nStart Time: ${formatTime(startTime)}\nEndTime: ${formatTime(endTime)}\nDetails: $description";
  }
}