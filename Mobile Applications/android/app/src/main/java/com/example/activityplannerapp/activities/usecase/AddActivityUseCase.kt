package com.example.activityplannerapp.activities.usecase

import com.example.activityplannerapp.activities.domain.Activity
import com.example.activityplannerapp.activities.service.ActivitiesService


interface AddActivityUseCase {
    suspend operator fun invoke(activity: Activity)
}

class AddActivityUseCaseImpl(private val service: ActivitiesService) : AddActivityUseCase {
    override suspend fun invoke(activity: Activity) {
        service.addActivity(activity)
    }
}