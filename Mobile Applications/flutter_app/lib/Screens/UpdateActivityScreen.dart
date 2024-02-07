import 'package:flutter/material.dart';

import '../Domain/Activity.dart';
import '../Domain/ActivityTypes.dart';
import '../Service/ActivitiesService.dart';

class UpdateActivityScreen extends StatefulWidget {
  final int? activityId;
  final ActivitiesService service;
  final Function(Activity) onUpdate;
  final Function() onCancel;

  const UpdateActivityScreen({super.key, this.activityId, required this.service, required this.onUpdate, required this.onCancel});

  @override
  _AddActivityScreenState createState() => _AddActivityScreenState();
}

class _AddActivityScreenState extends State<UpdateActivityScreen> {
  late Activity activity, prevActivity;
  late ActivityType selectedType;
  late DateTime selectedDate;
  late TimeOfDay selectedStartTime;
  late TimeOfDay selectedEndTime;

  TextEditingController _titleController = TextEditingController();
  TextEditingController _descriptionController = TextEditingController();

  @override
  void initState() {
    super.initState();
    prevActivity = widget.service.getActivity(widget.activityId!);
    activity = Activity.namedConstructor(prevActivity.id, prevActivity.title, prevActivity.type, prevActivity.date, prevActivity.startTime, prevActivity.endTime, prevActivity.description);
    selectedType = Types.typesList.firstWhere((type) => type.name == activity.type);
    selectedDate = activity.date;
    selectedStartTime = activity.startTime;
    selectedEndTime = activity.endTime;
    _titleController.text = activity.title;
    _descriptionController.text = activity.description;
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
      appBar: AppBar(
        title: const Text('Update Activity'),
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
      ),
      body: Padding(
        padding: const EdgeInsets.only(top: 20, left: 60, right: 60),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            TextField(
              controller: _titleController,
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
              controller: _descriptionController,
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
                    widget.onUpdate(activity);
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