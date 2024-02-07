
class ActivityType {
  String name;
  String picture;

  ActivityType(this.name, this.picture);
}

class Types {
  static final List<ActivityType> typesList = [
    ActivityType("sport", "assets/sports.jpg"),
    ActivityType("social", "assets/social.jpg"),
    ActivityType("work", "assets/work.jpg"),
    ActivityType("leisure", "assets/leisure.png"),
    ActivityType("health", "assets/health.png"),
    ActivityType("education", "assets/education.png"),
    ActivityType("entertainment", "assets/entertainment.jpg"),
    ActivityType("other", "assets/other_stuff.png"),
  ];
}