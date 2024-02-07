

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import '../Domain/Activity.dart';
import '../Service/ActivitiesService.dart';

class DeleteActivityScreen extends StatelessWidget {
  final int? activityId;
  final ActivitiesService service;
  final Function() onConfirmDelete;
  final Function() onCancel;

  const DeleteActivityScreen({Key? key, required this.service, required this.activityId, required this.onConfirmDelete, required this.onCancel}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    // Use the activityId to fetch the activity details or perform deletion
    // ...

    Activity activity = service.getActivity(activityId!);

    return Scaffold(
      appBar: AppBar(
        title: Text('Delete Activity'),
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
              Text("Activity Info:\n$activity"),
              SizedBox(height: 100),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  ElevatedButton(
                    onPressed: onConfirmDelete,
                    child: Text('Confirm Deletion'),
                  ),
                  SizedBox(width: 20),
                  ElevatedButton(
                    onPressed: onCancel,
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