

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_app/Domain/Activity.dart';
import 'package:flutter_app/Domain/ActivityTypes.dart';
import 'package:flutter_app/Service/ActivitiesService.dart';

class AddActivityScreen extends StatefulWidget {
  final ActivitiesService service;
  final Function(Activity activity) onAdd;
  final Function() onCancel;

  const AddActivityScreen({super.key, required this.service, required this.onAdd, required this.onCancel});

  @override
  _AddActivityScreenState createState() => _AddActivityScreenState();
}

class _AddActivityScreenState extends State<AddActivityScreen> {
  late Activity activity;
  late ActivityType selectedType;
  late DateTime selectedDate;
  late TimeOfDay selectedStartTime;
  late TimeOfDay selectedEndTime;

  final GlobalKey<ScaffoldState> _scaffoldKey = GlobalKey<ScaffoldState>();

  @override
  void initState() {
    super.initState();
    activity = Activity("", Types.typesList[0].name, DateTime.now(), TimeOfDay.now(), TimeOfDay.now(), "");
    selectedType = Types.typesList[0];
    selectedDate = DateTime.now();
    selectedStartTime = TimeOfDay.now();
    selectedEndTime = TimeOfDay.now();
  }

  Future<void> _selectDate() async {
    DateTime? pickedDate = await showDatePicker(
      context: context,
      initialDate: selectedDate,
      firstDate: DateTime(2000),
      lastDate: DateTime(2101),
    );

    if (pickedDate != null && pickedDate != selectedDate) {
      setState(() {
        selectedDate = pickedDate;
        activity.date = selectedDate;
      });
    }
  }

  Future<void> _selectStartTime() async {
    TimeOfDay? pickedTime = await showTimePicker(
      context: context,
      initialTime: selectedStartTime,
    );

    if (pickedTime != null && pickedTime != selectedStartTime) {
      setState(() {
        selectedStartTime = pickedTime;
        activity.startTime = selectedStartTime;
      });
    }
  }

  Future<void> _selectEndTime() async {
    TimeOfDay? pickedTime = await showTimePicker(
      context: context,
      initialTime: selectedEndTime,
    );

    if (pickedTime != null && pickedTime != selectedEndTime) {
      setState(() {
        selectedEndTime = pickedTime;
        activity.endTime = selectedEndTime;
      });
    }
  }


  @override
  Widget build(BuildContext context) {
      return Scaffold(
        key: _scaffoldKey,
        appBar: AppBar(
          title: const Text('Add Activity'),
          backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        ),
        body: Padding(
          padding: const EdgeInsets.only(top: 20, left: 60, right: 60),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              TextField(
                onChanged: (newTitle) {
                    activity.title = newTitle;
                },
                decoration: const InputDecoration(labelText: 'Title'),
              ),

              const SizedBox(height: 10),

              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  const Text("Type: ", style: TextStyle(fontSize: 18)),
                  const SizedBox(width: 10),
                  DropdownButton<ActivityType>(
                    value: selectedType,
                    onChanged: (ActivityType? newValue) {
                      setState(() {
                        selectedType = newValue!;
                        activity.type = selectedType.name;
                      });
                    },
                    items: Types.typesList.map((ActivityType type) {
                      return DropdownMenuItem<ActivityType>(
                        value: type,
                        child: Text(type.name, style: const TextStyle(fontSize: 16)),
                      );
                    }).toList(),
                  ),
                ],
              ),

              const SizedBox(height: 10),

              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  const Text("Date: ", style: TextStyle(fontSize: 18)),
                  const SizedBox(width: 15),
                  IconButton(
                    onPressed: () {
                      // Show date picker
                      _selectDate();
                    },
                    icon: Image.asset("assets/calendar_icon.png", width: 35, height: 35),
                  ),
                  const SizedBox(width: 10),
                  Text("${selectedDate.day}-${selectedDate.month}-${selectedDate.year}", style: const TextStyle(fontSize: 18)),
                ],
              ),


              const SizedBox(height: 10),

              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  const Text("Start Time: ", style: TextStyle(fontSize: 18)),
                  const SizedBox(width: 15),
                  IconButton(
                    onPressed: () {
                      // Show time picker
                      _selectStartTime();
                    },
                    icon: Image.asset("assets/clock_icon.png", width: 35, height: 35),
                  ),
                  const SizedBox(width: 10),
                  Text(selectedStartTime.format(context), style: const TextStyle(fontSize: 18)),
                ],
              ),

              const SizedBox(height: 10),

              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  const Text("End Time: ", style: TextStyle(fontSize: 18)),
                  const SizedBox(width: 15),
                  IconButton(
                    onPressed: () {
                      // Show time picker
                      _selectEndTime();
                    },
                    icon: Image.asset("assets/clock_icon.png", width: 35, height: 35),
                  ),
                  const SizedBox(width: 10),
                  Text(selectedEndTime.format(context), style: const TextStyle(fontSize: 18)),
                ],
              ),

              const SizedBox(height: 10),

              TextField(
                onChanged: (newDescription) {
                    activity.description = newDescription;
                },
                maxLines: 2,
                decoration: const InputDecoration(labelText: 'Description'),
              ),

              const SizedBox(height: 30),

              Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  ElevatedButton(
                    onPressed: () {
                      widget.onAdd(activity);
                    },
                    child: Text('Confirm'),
                  ),
                  SizedBox(width: 20),
                  ElevatedButton(
                    onPressed: widget.onCancel,
                    child: Text('Cancel'),
                  ),
                ],
              ),
            ],
          ),
        ),
      );
  }
}