import 'package:flutter/material.dart';
import 'package:flutter_app/Domain/Activity.dart';
import 'package:flutter_app/Repo/Repository.dart';
import 'package:flutter_app/Service/ActivitiesService.dart';
import 'package:intl/intl.dart';
import 'Routes.dart';
import 'Screens/ActivitiesScreen.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  _MyAppState createState() => _MyAppState();

}

class _MyAppState extends State<MyApp> {
  late final Repository repo;
  late final ActivitiesService service;
  late final AppRoutes appRoutes;
  final GlobalKey<NavigatorState> navigatorKey = GlobalKey<NavigatorState>();

  @override
  void initState() {
    super.initState();
    repo = Repository();
    service = ActivitiesService(repo);
    appRoutes = AppRoutes(service, navigatorKey, onActivityDelete, onActivityAdd, onActivityUpdate);
  }

  void onActivityDelete(int activityId) {
    setState(() {
      service.removeActivity(service.getActivity(activityId));
    });
  }
  
  String onActivityAdd(Activity activity) {
    String error = "";

    setState(() {
      try {
        service.addActivity(activity);
      }
      catch (e) {
        error = e.toString().replaceFirst('Exception: ', '');
      }
    });
    return error;
  }
  
  String onActivityUpdate(activity) {
    String error = "";

    setState(() {
      try {
        service.updateActivity(activity);
      }
      catch (e) {
        error = e.toString().replaceFirst('Exception: ', '');
      }
    });
    return error;
  }

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Activity Planner',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      navigatorKey: navigatorKey,
      builder: (context, child) {
        return ActivitiesProvider(
          service: service,
          child: child!,
        );
      },
      onGenerateRoute: appRoutes.generateRoute,
      initialRoute: AppRoutes.activities,
    );
  }
}

class ActivitiesProvider extends InheritedWidget {
  final ActivitiesService service;

  const ActivitiesProvider({
    Key? key,
    required this.service,
    required Widget child,
  }) : super(key: key, child: child);

  static ActivitiesProvider? of(BuildContext context) {
    return context.dependOnInheritedWidgetOfExactType<ActivitiesProvider>();
  }

  @override
  bool updateShouldNotify(covariant InheritedWidget oldWidget) {
    return false;
  }
}
