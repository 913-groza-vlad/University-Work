package com.example.server.Service;

import com.example.server.Model.Activity;
import com.example.server.Repository.ActivityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepo repository;

    public List<Activity> getActivities(){
        return repository.findAll();
    }

    public Optional<Activity> getActivityById(Long Id) {
        return repository.findById(Id);
    }

    public Activity saveActivity(Activity activity) {
        return repository.save(activity);
    }

    public void deleteActivity(Long Id) {
        repository.deleteById(Id);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
