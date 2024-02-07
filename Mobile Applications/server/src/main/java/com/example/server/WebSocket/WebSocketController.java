package com.example.server.WebSocket;

import com.example.server.Model.Activity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class WebSocketController {
    @MessageMapping("/getAll")
    @SendTo("/topic/activities")
    public List<Activity> getActivities(List<Activity> activities) {
        return activities;
    }

    @MessageMapping("/update/{id}")
    @SendTo("/topic/activityUpdates")
    public Activity updateActivity(Activity activity) {
        return activity;
    }

    @MessageMapping("/get/{id}")
    @SendTo("/topic/activity")
    public Activity getActivityById(Activity activity) {
        return activity;
    }

    @MessageMapping("/add")
    @SendTo("/topic/activityAdd")
    public Activity addActivity(Activity activity) {
        return activity;
    }

    @MessageMapping("/delete/{id}")
    @SendTo("/topic/delete")
    public Activity deleteActivity(Activity activity) {
        return activity;
    }

}
