package com.example.server.Model;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "activities")
public class Activity {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @Column(name = "title")
    private String title;
    @Column(name = "type")
    private String type;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "startTime")
    private LocalTime startTime;
    @Column(name = "endTime")
    private LocalTime endTime;
    @Column(name = "description")
    private String description;

    public Activity() {
    }

    public Activity(String title, String type, LocalDate date, LocalTime startTime, LocalTime endTime, String description) {
        this.title = title;
        this.type = type;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString(){
       return "Activity{ " +
               "id=" + id +
               ", title='" + title + '\'' +
               ", type='" + type + '\'' +
               ", date='" + date + '\'' +
               ", startTime='" + startTime + '\'' +
               ", endTime='" + endTime + '\'' +
               ", description='" + description + '\'' +
               " }";
    }
}
