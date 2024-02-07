package com.example.activityplannerapp.activities.usecase

import com.example.activityplannerapp.activities.domain.Activity
import com.example.activityplannerapp.activities.service.ActivitiesService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface DeleteActivityUseCase {
    suspend operator fun invoke(activity: Activity)
}

class DeleteActivityUseCaseImpl(private val service: ActivitiesService, private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) : DeleteActivityUseCase {
    override suspend fun invoke(activity: Activity) {
        withContext(ioDispatcher) {
            service.deleteActivity(activity)
        }
    }
}