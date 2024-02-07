package com.example.activityplannerapp.activities.usecase

import com.example.activityplannerapp.activities.domain.Activity
import com.example.activityplannerapp.activities.service.ActivitiesService

interface UpdateActivityUseCase {
    suspend operator fun invoke(activity: Activity)
}

class UpdateActivityUseCaseImpl(private val service: ActivitiesService) : UpdateActivityUseCase {
    override suspend fun invoke(activity: Activity) {
        service.updateActivity(activity)
    }
}