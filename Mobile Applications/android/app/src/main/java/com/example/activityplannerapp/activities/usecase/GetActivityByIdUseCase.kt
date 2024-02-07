package com.example.activityplannerapp.activities.usecase

import com.example.activityplannerapp.activities.domain.Activity
import com.example.activityplannerapp.activities.service.ActivitiesService

interface GetActivityByIdUseCase {
    suspend operator fun invoke(activityId: Int?): Activity
}

class GetActivityByIdUseCaseImpl(private val service: ActivitiesService) : GetActivityByIdUseCase {
    override suspend fun invoke(activityId: Int?): Activity {
        return service.getActivityById(activityId)
    }
}