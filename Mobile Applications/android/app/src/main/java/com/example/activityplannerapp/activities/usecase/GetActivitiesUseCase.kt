package com.example.activityplannerapp.activities.usecase

import com.example.activityplannerapp.activities.domain.Activity
import com.example.activityplannerapp.activities.service.ActivitiesService
import kotlinx.coroutines.flow.Flow

interface GetActivitiesUseCase {
    suspend operator fun invoke(): Flow<List<Activity>>
}


class GetActivitiesUseCaseImpl (private val service: ActivitiesService) : GetActivitiesUseCase {
    override suspend fun invoke(): Flow<List<Activity>> {
        return service.getActivities()
    }
}