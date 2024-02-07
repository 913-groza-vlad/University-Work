package com.example.server.Controller;

import com.example.server.Model.Activity;
import com.example.server.Service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    private ActivityService service;

    @GetMapping("/getAll")
    public List<Activity> getAllActivities(){
        return service.getActivities();
    }

    @GetMapping("/get/{id}")
    public Optional<Activity> getActivityById(@PathVariable Long id){
        return service.getActivityById(id);
    }

    @PostMapping("/add")
    public Activity addActivity(@RequestBody Activity activity){
        return service.saveActivity(activity);
    }

    @PutMapping("/update/{id}")
    public Activity updateActivity(@PathVariable Long id, @RequestBody Activity activity){
        Optional<Activity> oldActivityOptional = service.getActivityById(id);
        if(oldActivityOptional.isEmpty())
            return null;
        Activity oldActivity = oldActivityOptional.get();
        if (activity.getTitle() != null) oldActivity.setTitle(activity.getTitle());
        if (activity.getType() != null) oldActivity.setType(activity.getType());
        if (activity.getDate() != null) oldActivity.setDate(activity.getDate());
        if (activity.getStartTime() != null) oldActivity.setStartTime(activity.getStartTime());
        if (activity.getEndTime() != null) oldActivity.setEndTime(activity.getEndTime());
        oldActivity.setDescription(activity.getDescription());

        return service.saveActivity(oldActivity);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteActivity(@PathVariable Long id){
        if (service.existsById(id)) {
            service.deleteActivity(id);
        }
    }

}
