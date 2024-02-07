

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_app/Service/ActivitiesService.dart';

import '../Domain/Activity.dart';
import '../Domain/ActivityTypes.dart';

class ActivitiesScreen extends StatefulWidget {
  final ActivitiesService service;
  final Function(Activity) onActivityEditClick;
  final Function(Activity) onActivityDeleteClick;
  final Function() onActivityAddClick;

  const ActivitiesScreen({
    Key? key,
    required this.service,
    required this.onActivityEditClick,
    required this.onActivityDeleteClick,
    required this.onActivityAddClick,
  }) : super(key: key);

  @override
  State<StatefulWidget> createState() => _ActivitiesScreenState();
}

class _ActivitiesScreenState extends State<ActivitiesScreen> {

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Theme.of(context).colorScheme.inversePrimary,
          // Here we take the value from the MyHomePage object that was created by
          // the App.build method, and use it to set our appbar title.
          title: Text("Activity Planner App"),
        ),
        body: Center(
        child: Padding(
          padding: EdgeInsets.only(top: 16.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              const Text(
                "My Activities",
                style: TextStyle(fontSize: 26),
              ),
              const SizedBox(height: 20.0),
              Expanded(
                child: Padding(
                  padding: EdgeInsets.only(left: 46.0),
                  child: ListView.builder(
                    itemCount: widget.service.getActivities().length,
                    itemBuilder: (context, index) {
                      return ActivityItem(
                        activity: widget.service.getActivities()[index],
                        onActivityEditClick: widget.onActivityEditClick,
                        onActivityDeleteClick: widget.onActivityDeleteClick,
                      );
                    },
                  ),
                ),
              ),
              Padding(
                padding: EdgeInsets.only(bottom: 40.0),
                child: IconButton(
                    onPressed: widget.onActivityAddClick,
                    icon: Image.asset("assets/add_icon.png", width: 50, height: 50)
                ),
              )
            ],
          )
      )
      )
    );
  }
}

class ActivityItem extends StatefulWidget {
  final Activity activity;
  final Function(Activity) onActivityEditClick;
  final Function(Activity) onActivityDeleteClick;

  ActivityItem({
    required this.activity,
    required this.onActivityEditClick,
    required this.onActivityDeleteClick,
  });

  @override
  State<StatefulWidget> createState() => _ActivityItemState();
}

class _ActivityItemState extends State<ActivityItem> {
  bool areDetailsDisplayed = false;

  Widget buildTypeImage(String type) {
    final ActivityType activityType = Types.typesList.firstWhere(
          (element) => element.name == type,
      orElse: () => ActivityType("other", "assets/other_stuff.png"),
    );

    return CircleAvatar(
      radius: 17.0,
      backgroundImage: AssetImage(activityType.picture),
      // backgroundColor: Theme.of(context).colorScheme.secondary,
    );
  }

  @override
  Widget build(BuildContext context) {

    return Padding(
      padding: EdgeInsets.all(2.0),
      child: Row(
        children: [
          Container(
            decoration: BoxDecoration(
              shape: BoxShape.circle,
              border: Border.all(
                color: Theme.of(context).colorScheme.secondary, // Set the border color
                width: 1.0, // Set the border width
              ),
            ),
            child: buildTypeImage(widget.activity.type)
          ),
          const SizedBox(width: 8.0),
          GestureDetector(
            onTap: () {
              setState(() {
                areDetailsDisplayed = !areDetailsDisplayed;
              });
            },
            child: Container(
              padding: EdgeInsets.all(2.0),
              decoration: BoxDecoration(
                shape: BoxShape.rectangle,
                borderRadius: BorderRadius.circular(8.0),
                color: areDetailsDisplayed
                    ? Theme.of(context).colorScheme.inversePrimary
                    : Theme.of(context).scaffoldBackgroundColor,
              ),
              child: AnimatedSize(
                duration: const Duration(milliseconds: 300),
                child: Container(
                  width: 180.0,
                  padding: EdgeInsets.all(8.0),
                  child: Text(
                    widget.activity.toString(),
                    maxLines: areDetailsDisplayed ? 10000 : 1,
                    style: Theme.of(context).textTheme.bodyText2,
                  ),
                ),
              ),
            ),
          ),
  
          Row(
            mainAxisAlignment: MainAxisAlignment.end,
            children: [
              IconButton(
                onPressed: () => widget.onActivityEditClick(widget.activity),
                icon: Image.asset("assets/edit_icon.png", width: 24, height: 24),
              ),
              IconButton(
                onPressed: () => widget.onActivityDeleteClick(widget.activity),
                icon: Image.asset("assets/delete_icon.png", width: 24, height: 24),
              ),
            ],
          ),
        ],
      ),
    );
  }
}