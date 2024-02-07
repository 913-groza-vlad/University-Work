import 'package:flutter/material.dart';
import 'package:flutter_app/Screens/UpdateActivityScreen.dart';

import 'Domain/Activity.dart';
import 'Screens/ActivitiesScreen.dart';
import 'Screens/AddActivityScreen.dart';
import 'Screens/DeleteActivityScreen.dart';
import 'Service/ActivitiesService.dart';

class AppRoutes {
  final ActivitiesService service;
  final GlobalKey<NavigatorState> navigatorKey;
  final Function(int) onActivityDelete;
  final Function(Activity) onActivityAdd;
  final Function(Activity) onActivityUpdate;

  AppRoutes(this.service, this.navigatorKey, this.onActivityDelete, this.onActivityAdd, this.onActivityUpdate);

  static const String activities = '/activities';
  static const String deleteActivity = '/deleteActivity';
  static const String addActivity = '/addActivity';
  static const String updateActivity = '/updateActivity';

  Route<dynamic>? generateRoute(RouteSettings settings) {
    switch (settings.name) {
      case activities:
        return MaterialPageRoute(builder: (_) => ActivitiesScreen(
          service: service,
          onActivityEditClick: (activity) {
              navigatorKey.currentState?.pushNamed(
              updateActivity,
              arguments: activity.id,
            );
          },
          onActivityDeleteClick: (activity) {
            navigatorKey.currentState?.pushNamed(
              deleteActivity,
              arguments: activity.id,
            );
          },
          onActivityAddClick: () {
            navigatorKey.currentState?.pushNamed(
              addActivity,
            );
          },
        ));
      case deleteActivity:
        int? activityId = settings.arguments as int?;
        return MaterialPageRoute(
          builder: (_) => DeleteActivityScreen(
              service: service,
              activityId: activityId,
              onConfirmDelete: () {
                onActivityDelete(activityId!);
                navigatorKey.currentState?.pop();
              },
              onCancel: () {
                navigatorKey.currentState?.pop();
              }
            ),
        );
      case addActivity:
        return MaterialPageRoute(
          builder: (_) => AddActivityScreen(
            service: service,
            onAdd: (activity) {
              String error = onActivityAdd(activity);
              if (error != "") {
                ScaffoldMessenger.of(navigatorKey.currentContext!).showSnackBar(
                  SnackBar(
                    content: Text(error),
                    duration: const Duration(seconds: 2),
                    backgroundColor: Theme.of(navigatorKey.currentContext!).colorScheme.secondary,
                  ),
                );
              }
              else {
                navigatorKey.currentState?.pop();
              }
            },
            onCancel: () {
              navigatorKey.currentState?.pop();
            },
          ),
        );
      case updateActivity:
        int? activityId = settings.arguments as int?;
        return MaterialPageRoute(
          builder: (_) => UpdateActivityScreen(
              service: service,
              activityId: activityId,
              onUpdate: (activity) {
                String error = onActivityUpdate(activity);
                if (error != "") {
                  ScaffoldMessenger.of(navigatorKey.currentContext!).showSnackBar(
                    SnackBar(
                      content: Text(error),
                      duration: const Duration(seconds: 2),
                      backgroundColor: Theme.of(navigatorKey.currentContext!).colorScheme.secondary,
                    ),
                  );
                }
                else {
                  navigatorKey.currentState?.pop();
                }
              },
              onCancel: () {
                navigatorKey.currentState?.pop();
              }
              ),
        );
      default:
        return null;
    }
  }
}