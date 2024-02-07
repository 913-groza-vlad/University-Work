package com.example.activityplannerapp.activities.service

import com.example.activityplannerapp.activities.domain.Activity
import com.example.activityplannerapp.activities.repo.ActivitiesRepository
import com.example.activityplannerapp.activities.repo.ActivitiesRepositoryImpl
import com.example.activityplannerapp.activities.server.ActivityAPI.isConnectionOn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList

class ActivitiesService (
    private val activityRepo: ActivitiesRepository
) {
    suspend fun getActivities() = activityRepo.getActivities()

    suspend fun getActivityById(id: Int?): Activity {
        if (isConnectionOn)
            return activityRepo.getActivityById(id)
        return activityRepo.getLocalActivityById(id)
    }

//    suspend fun checkOverlap(newActivity: Activity) {
//        var activities: List<Activity> = emptyList()
//
//        getActivities().collect {
//            activities = it
//        }
//        for (activity in activities) {
//            if (newActivity.date == activity.date && newActivity.id != activity.id) {
//                // Check if the new activity's startTime and endTime overlap with existing activity
//                if (newActivity.startTime.isBefore(activity.endTime) && newActivity.endTime.isAfter(activity.startTime)) {
//                    throw IllegalArgumentException("Activity overlaps with an existing activity")
//                }
//            }
//        }
//    }

    private fun checkInvalidTimes(newActivity: Activity) {
        if (newActivity.startTime.isAfter(newActivity.endTime)) {
            throw IllegalArgumentException("Activity's start time is after end time")
        }
    }

    suspend fun addActivity(newActivity: Activity) {
        if (newActivity.title == "") {
            throw IllegalArgumentException("Insert a title for this activity")
        }
        checkInvalidTimes(newActivity)
        // checkOverlap(newActivity)
        if (isConnectionOn) {
            activityRepo.addActivity(newActivity)
        } else {
            activityRepo.addLocalActivity(newActivity)
        }
    }

    suspend fun updateActivity(activity: Activity) {
        if (activity.title == "") {
            throw IllegalArgumentException("Insert a title for this activity")
        }
        checkInvalidTimes(activity)
        // checkOverlap(activity)
        if (isConnectionOn) {
            activityRepo.updateActivity(activity)
        } else {
            activityRepo.updateLocalActivity(activity)
        }
    }

    suspend fun deleteActivity(activity: Activity) = activityRepo.deleteActivity(activity)
}